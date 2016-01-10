package org.tom.vd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tom.vd.db.DBHelper;
import org.tom.vd.rmtp.OnProgressListener;

/**
 * <p>Title: VirtualDealer.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2016-1-10 ÏÂÎç7:05:17
 * @version 1.0
 *
 */
public class VirtualDealer implements OnProgressListener{

	private String roomId;
	private String currentGameId;
	private String currentShoeId;
	private String tableStatus;
	private String dealer;
	private Map<String,String> cardInfo=new HashMap<String, String>();
	
	public void start(){
		List<Map<String,String>> list=DBHelper.getHelper().getGameRoundInfo();
		for(Map<String,String> map:list){
			currentGameId=map.get("id");
			currentShoeId=map.get("shoeId");
			roomId=map.get("roomId");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tom.vd.rmtp.OnProgressListener#onProgressListener(java.lang.String)
	 */
	@Override
	public void onProgressListener(String commondOutput) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.tom.vd.rmtp.OnProgressListener#onProgressStateChangedListener(boolean)
	 */
	@Override
	public void onProgressStateChangedListener(boolean isInProgress) {
		// TODO Auto-generated method stub
		
	}

}
