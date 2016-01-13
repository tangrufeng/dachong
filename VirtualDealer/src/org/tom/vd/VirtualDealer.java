package org.tom.vd;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.tom.vd.bean.GameRoundInfo;
import org.tom.vd.bean.ProgessBean;
import org.tom.vd.cache.CardCache;
import org.tom.vd.config.Config;
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

	private String reg = "";
	private String roomId;
	private String currentGameId;
	private String currentShoeId;
	private String tableStatus;
	private String dealer;
	private String now;

	private Boolean isPlay = false;
	
	Map<String,ProgessBean> progressCache=new HashMap<String, ProgessBean>();
	
	private Map<String, String> cardInfo = new HashMap<String, String>();

	public void start() {
		do {
			if (!isPlay) {
				GameRoundInfo g = CardCache.getCache().poll();
				if (g != null) {
					pushStreamToRed5(g.getVideoPath(),g.getId()+"");
					parseBJLProgessToCache(g);
				}
			}
		} while (true);
	}

	public void pushStreamToRed5(String videoPath,String id) {
		FlowPusher pusher = new FlowPusher(Config.getCfg()
				.getString("red5host"), videoPath,id);
		new Thread(pusher).start();
	}

	public void pushToMina(String id,String timeline) {
		ProgessBean pb=progressCache.get(timeline);
		MinaCommandPo minaCommandPo = new MinaCommandPo();
		minaCommandPo.setRequestType("REQUEST");
		minaCommandPo.setModuleType("AP");
		String body = getProgressJSON(id, pb);
		System.out.println("=====body.getBytes().length=======>"
				+ body);
		minaCommandPo.getParameter().put("data", body);
		MinaGameInterface.ioSession.write(minaCommandPo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tom.vd.rmtp.OnProgressListener#onProgressListener(java.lang.String)
	 */
	@Override
	public void onProgressListener(String id,String commondOutput) {
		if (StringUtils.isNotEmpty(commondOutput)) {
			logger.debug("FFMPEG::" + commondOutput);
			if (commondOutput.startsWith("Frame:")) {
				String timeline="";
				pushToMina(id,timeline);
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
		synchronized (isPlay) {
			isPlay=isInProgress;
		}
	}
	
	private String getProgressJSON(String id, ProgessBean bean) {
		JSONObject json = new JSONObject();
		
		json.put("id", id);
		json.put("roomNumber", "BJL_01");
		json.put("cardInfo", bean.getBcolor() + "_" + bean.getBnum());
		json.put("event", bean.getStatus());
		json.put("timeline", bean.getTimeline());

		
		logger.info(json.toString());
		return json.toString();
	}

	// private String

	private void parseBJLProgessToCache(GameRoundInfo g) {
		progressCache.clear();
		JSONArray jr = JSONArray.fromObject(g.getCardInfo());
		for (int i = 0; i < jr.size(); i++) {
			JSONObject jo = jr.getJSONObject(i);
			ProgessBean bean = new ProgessBean();
			bean.setTimeline(jo.getString("timeline"));
			bean.setStatus(jo.getString("event"));
			bean.setBcolor(jo.getInt("bcolor"));
			bean.setBnum(jo.getInt("bnum"));
			bean.setFcolor(jo.getInt("fcolor"));
			bean.setFnum(jo.getInt("fnum"));
			logger.info(bean.toString());
			progressCache.put(bean.getTimeline(), bean);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
