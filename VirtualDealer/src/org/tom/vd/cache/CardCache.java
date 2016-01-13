package org.tom.vd.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import org.tom.vd.bean.GameRoundInfo;
import org.tom.vd.db.DBHelper;

public class CardCache {

	private LinkedBlockingDeque<GameRoundInfo> queue = new LinkedBlockingDeque<GameRoundInfo>();

	private Boolean isFill = false;

	private static CardCache instance = new CardCache();

	private CardCache() {
	}

	public static CardCache getCache() {
		return instance;
	}

	/**
	 * 获取局信息
	 * @return
	 */
	public GameRoundInfo poll() {
		if(queue.size()<=5 && !isFill){
			new FillQueueThread().start();
		}
		GameRoundInfo g=null;
		try {
			System.out.println("begin 1");
			g = queue.take();
			System.out.println(g);
		} catch (InterruptedException e) {
		}
		return g;
	}

	class FillQueueThread extends Thread {

		@Override
		public void run() {
			System.out.println("Begin fill queue from db");
			synchronized (isFill) {
				isFill = true;
			}
			DBHelper helper = DBHelper.getHelper();
			List<Map<String, String>> list = helper.getGameRoundInfo();
			for (Map<String, String> map : list) {
				GameRoundInfo g = new GameRoundInfo();
				g.setId(Integer.parseInt(map.get("id")));
				g.setResult(Integer.parseInt(map.get("result")));
				g.setCreateTime(map.get("createTime"));
				g.setGameType(map.get("gameType"));
				g.setVideoPath(map.get("videoPath"));
				g.setCardInfo(map.get("cardInfo"));
				g.setDealer(Integer.parseInt(map.get("dealer")));
				g.setGroupId(Integer.parseInt(map.get("groupId")));
				g.setShoeid(Integer.parseInt(map.get("shoeId")));
				g.setRoomId(Integer.parseInt(map.get("roomId")));
				queue.addLast(g);
			}
//			try {
//				Thread.sleep(5000l);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			GameRoundInfo g = new GameRoundInfo();
//			queue.add(g);
//			queue.add(g);
//			queue.add(g);
//			queue.add(g);
//			queue.add(g);
//			queue.add(g);
			synchronized (isFill) {
				isFill=false;
			}
			System.out.println("end fill queue from db");
		}

	}

	public static void main(String[] args) throws InterruptedException {
		CardCache.getCache().poll();
	}
}
