package org.tom.vd.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;
import org.tom.vd.VirtualDealer;
import org.tom.vd.bean.GameRoundInfo;
import org.tom.vd.db.DBHelper;

public class CardCache {

	private static final Logger logger = Logger.getLogger(CardCache.class);

	private LinkedBlockingDeque<GameRoundInfo> queue = new LinkedBlockingDeque<GameRoundInfo>();

	private Boolean isFill = false;

	private static CardCache instance = new CardCache();
	
	int shoeId=1;

	private CardCache() {
	}

	public static CardCache getCache() {
		return instance;
	}

	/**
	 * @return
	 */
	public GameRoundInfo poll() {
		if(queue.isEmpty() && !isFill){
			new FillQueueThread().start();
		}
		GameRoundInfo g=null;
		try {
			logger.debug("begin 1");
			g = queue.take();
			logger.debug(g);
		} catch (InterruptedException e) {
		}
		return g;
	}

	class FillQueueThread extends Thread {

		@Override
		public void run() {
			logger.debug("Begin fill queue from db  cache size ==>"+queue.size());
			synchronized (isFill) {
				isFill = true;
			}
			DBHelper helper = DBHelper.getHelper();
			int vShoeId=helper.getShoeid("BJL_01_SHOEID");
			List<Map<String, String>> list = helper.getGameRoundInfo(2);
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
				g.setShoeid(vShoeId);
				g.setRoomId(Integer.parseInt(map.get("roomId")));
				g.setGameId(Integer.parseInt(map.get("gameId")));
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
			logger.debug("end fill queue from db  cache size ==>"+queue.size());
		}

	}

	public static void main(String[] args) throws InterruptedException {
		CardCache.getCache().poll();
	}
}
