package com.jcyt.lottery.listener;

/**
 * <p>Title: OnProgressListener.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2016-1-10 上午11:06:51
 * @version 1.0
 *
 */
public interface OnProgressListener {

	public void onProgressListener(String commondOutput);
	
	public void onProgressStateChangedListener(boolean isInProgress);

}
