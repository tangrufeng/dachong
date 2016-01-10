package com.jcyt.lottery.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.effecia.mina.MinaCommandPo;
import com.jcyt.lottery.dao.GameRoundInfoDAO;
import com.jcyt.lottery.entity.GameRoundInfo;
import com.jcyt.lottery.entity.ProgessBean;
import com.jcyt.lottery.listener.OnProgressListener;

/**
 * <p>
 * Title: GameRoundInfoService.java
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
 * @date 2015-12-20 下午4:52:26
 * @version 1.0
 * 
 */
@Service
public class GameRoundInfoService implements OnProgressListener{
	private final static Logger logger = Logger
			.getLogger(GameRoundInfoService.class);
	@Autowired
	private GameRoundInfoDAO griDAO;
	
	@Autowired
	private Red5Client red5Client;

	private boolean isInProgress=false;
	public void getGameRoundInfo() {
		
		//初始话Mina，并注册
		System.out.println("初始话Mina，并注册..........");
		new MinaGameInterface();
		MinaCommandPo regiestPo  = new MinaCommandPo();
		regiestPo.setModuleType("PLATFORM");
		regiestPo.setRequestType("REQUEST");
		regiestPo.setCommandType(ApCommand.TABLE_INIT.toString());
		MinaGameInterface.ioSession.write(regiestPo);
		System.out.println("初始话Mina，并注册结束..........");
		//end~~~
		
		
//		List<GameRoundInfo> list = griDAO.getGameRoundInfo(1);
//		
//		try {
//			System.out.println("start w");
//			System.out.println("the client is connected..........");
//			for (GameRoundInfo g : list) {
//				List<ProgessBean> pgList = parseBJLProgess(g);
//				for (ProgessBean pb : pgList) {
//
//					MinaCommandPo minaCommandPo = new MinaCommandPo();
//					minaCommandPo.setRequestType("REQUEST");
//					minaCommandPo.setModuleType("AP");
//					String body = getProgressJSON(g, pb);
//					System.out.println("=====body.getBytes().length=======>"
//							+ body);
//					minaCommandPo.getParameter().put("data", body);
//					MinaGameInterface.ioSession.write(minaCommandPo);
//					// out.write(bytes.length);
//					// out.write(bytes);
//					// out.flush();
//					// out.close();
//					Thread.sleep(10000);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("", e);
//		} finally {
//			// if(out!=null){
//			// try {
//			// out.close();
//			// socket.close();
//			//
//			// } catch (IOException e) {
//			// e.printStackTrace();
//			// logger.error("", e);
//			// }
//			// }
//		}
	}

	private String getProgressJSON(GameRoundInfo g, ProgessBean bean) {
		JSONObject json = new JSONObject();
		
		json.put("id", g.getId());
		json.put("roomNumber", "BJL_01");
		json.put("cardResult", g.getResult());
		json.put("cardInfo", bean.getBcolor() + "_" + bean.getBnum());
		json.put("event", bean.getStatus());
		json.put("timeline", bean.getTimeline());

		
		logger.info(json.toString());
		return json.toString();
	}

	// private String

	private List<ProgessBean> parseBJLProgess(GameRoundInfo g) {
		List<ProgessBean> rst = new ArrayList<ProgessBean>();
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
			rst.add(bean);
		}
		return rst;
	}

	/* (non-Javadoc)
	 * @see com.jcyt.lottery.listener.OnProgressListener#onProgressListener(java.lang.String)
	 */
	@Override
	public void onProgressListener(String commondOutput) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.jcyt.lottery.listener.OnProgressListener#onProgressStateChangedListener(boolean)
	 */
	@Override
	public void onProgressStateChangedListener(boolean isInProgress) {
		this.isInProgress=isInProgress;
	}

}
