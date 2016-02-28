package org.tom.vd;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.tom.vd.bean.GameRoundInfo;
import org.tom.vd.bean.ProgessBean;
import org.tom.vd.cache.CardCache;
import org.tom.vd.config.Config;
import org.tom.vd.mina.ApCommand;
import org.tom.vd.mina.MinaGameInterface;
import org.tom.vd.rmtp.FlowPusher;
import org.tom.vd.rmtp.OnProgressListener;

import com.effecia.mina.MinaCommandPo;

/**
 * <p>
 * Title: VirtualDealer.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: jc-yt.com
 * </p>
 * 
 * @author tang
 * @date 2016-1-10 7:05:17
 * @version 1.0
 * 
 */
public class VirtualDealer implements OnProgressListener {

	private static final Logger logger = Logger.getLogger(VirtualDealer.class);

	private static Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
	private String roomId;
	private String currentGameId;
	private String currentShoeId;
	private String tableStatus;
	private String dealer;
	private String now;
	private int index = 0;

	private Boolean isPlay = false;

	Map<String, ProgessBean> progressCache = new HashMap<String, ProgessBean>();

	public void start() {

		GameRoundInfo g = CardCache.getCache().poll();
		logger.debug("begin play:" + g);
		if (g != null) {
			pushStreamToRed5(g, g.getId() + "");
			parseBJLProgessToCache(g);
		}

	}

	public void pushStreamToRed5(GameRoundInfo game, String id) {
		FlowPusher pusher = new FlowPusher(game, Config.getCfg().getString(
				"red5host"));
		pusher.setOnProgressListener(this);
		new Thread(pusher).start();
	}

	public void pushToMina(GameRoundInfo game, String timeline) {
		ProgessBean pb = progressCache.remove(timeline);
		if (pb == null) {
			return;
		}
		logger.debug(pb);
		if ("begin".equals(pb.getStatus())) {
			sendStartCommond(game);
		} else if ("dealing_wait".equals(pb.getStatus())) {
			sendDealingWaitCommond(game);
		} else if ("deal".equals(pb.getStatus())) {
			sendDealCommond(game, pb);
		} else if ("end".equals(pb.getStatus())) {
			sendEndCommand(game);
		}
	}

	/**
	 * @param game
	 */
	private void sendEndCommand(GameRoundInfo game) {
		MinaCommandPo mcp = new MinaCommandPo();
		mcp.setRequestType("REQUEST");
		mcp.setModuleType("AP");
		mcp.setCommandType(ApCommand.END.toString());
		Map map = mcp.getParameter();
		map.put("tableId", game.getRoomId());// 
		map.put("shoeId", game.getShoeid() + "");
		map.put("gameId", game.getGameId());
		map.put("cardInfo", game.getCardInfo());
		map.put("gameRoundInfoId",game.getId());
		MinaGameInterface.ioSession.write(mcp);
	}

	/**
	 * @param game
	 * @param pb
	 */
	private void sendDealCommond(GameRoundInfo game, ProgessBean pb) {
		MinaCommandPo mcp = new MinaCommandPo();
		mcp.setRequestType("REQUEST");
		mcp.setModuleType("AP");
		mcp.setCommandType(ApCommand.DEAL.toString());
		Map map = mcp.getParameter();
		map.put("tableId", game.getRoomId());//
		map.put("shoeId", game.getShoeid() + "");
		map.put("gameId", game.getGameId());
		map.put("bnum", pb.getBnum());
		map.put("fnum", pb.getFnum());
		map.put("bcolor", pb.getBcolor());
		map.put("fcolor", pb.getFcolor());
		map.put("gameRoundInfoId",game.getId());
		MinaGameInterface.ioSession.write(mcp);
	}

	/**
	 * @param game
	 */
	private void sendDealingWaitCommond(GameRoundInfo game) {
		MinaCommandPo mcp = new MinaCommandPo();
		mcp.setRequestType("REQUEST");
		mcp.setModuleType("AP");
		mcp.setCommandType(ApCommand.DEALING_WAIT.toString());
		Map map = mcp.getParameter();
		map.put("tableId", game.getRoomId());//
		map.put("shoeId", game.getShoeid() + "");
		map.put("gameId", game.getGameId());
		map.put("gameRoundInfoId",game.getId());
		MinaGameInterface.ioSession.write(mcp);
	}

	/**
	 * @param game
	 */
	private void sendStartCommond(GameRoundInfo game) {
		MinaCommandPo mcp = new MinaCommandPo();
		mcp.setRequestType("REQUEST");
		mcp.setModuleType("AP");
		mcp.setCommandType(ApCommand.START.toString());
		Map map = mcp.getParameter();
		map.put("tableId", game.getRoomId());//
		map.put("shoeId", game.getShoeid() + "");
		map.put("gameId", game.getGameId());
		map.put("countDown", 20);// TODO
		map.put("gameRoundInfoId",game.getId());
		MinaGameInterface.ioSession.write(mcp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tom.vd.rmtp.OnProgressListener#onProgressListener(java.lang.String)
	 */
	@Override
	public void onProgressListener(GameRoundInfo game, String commondOutput) {
		logger.debug(commondOutput);
		if (StringUtils.isNotEmpty(commondOutput)) {
			if (commondOutput.startsWith("frame=")) {
				Matcher m = pattern.matcher(commondOutput);
				if (m.find()) {
					String timeline = m.group();
					pushToMina(game, timeline);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tom.vd.rmtp.OnProgressListener#onProgressStateChangedListener(boolean
	 * )
	 */
	@Override
	public void onProgressStateChangedListener(boolean isInProgress) {
		logger.debug("onProgressStateChangedListener===>" + isInProgress);
		if (!isInProgress) {
			start();
		}
	}
	
	private void parseBJLProgessToCache(GameRoundInfo g) {
		progressCache.clear();
		JSONArray jr = JSONArray.fromObject(g.getCardInfo());
		for (int i = 0; i < jr.size(); i++) {
			JSONObject jo = jr.getJSONObject(i);
			ProgessBean bean = new ProgessBean();
			bean.setTimeline(jo.getString("timeline"));
			bean.setStatus(jo.getString("event"));
			if (jo.containsKey("bcolor"))
				bean.setBcolor(jo.getInt("bcolor"));
			if (jo.containsKey("bnum"))
				bean.setBnum(jo.getInt("bnum"));
			if (jo.containsKey("fcolor"))
				bean.setFcolor(jo.getInt("fcolor"));
			if (jo.containsKey("fnum"))
				bean.setFnum(jo.getInt("fnum"));
			progressCache.put(bean.getTimeline(), bean);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
