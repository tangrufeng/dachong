package org.tom.vd.rmtp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.tom.vd.config.Config;

/**
 * <p>Title: FlowPusher.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2016-1-10 ����4:43:09
 * @version 1.0
 *
 */
public class FlowPusher implements Runnable{

	private OnProgressListener onProgressListener;

	private String red5Host;
	
	private String filePath;
	
	private String id;
	
	public FlowPusher(String red5Host,String filePath,String id){
		this.id=id;
		this.red5Host=red5Host;
		this.filePath=filePath;
	}
	
	
	public void setOnProgressListener(OnProgressListener listener){
		this.onProgressListener=listener;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		 List<String> commend = new java.util.ArrayList<String>();
		 commend.add(Config.getCfg().getString("ffmpeg.path"));
		 commend.add("-re");
		 commend.add("-i");
		 commend.add(filePath);
		 commend.add("-vcodec");
		 commend.add("copy");
		 commend.add("-f");
		 commend.add("flv");
		 commend.add(red5Host);

		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.redirectErrorStream(true);
			Process p = builder.command(commend).start();
			onProgressListener.onProgressStateChangedListener(true);

			BufferedReader buf = null; // 
			String line = null;

			buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

			while ((line = buf.readLine()) != null) {
				System.out.println(line);
				if(onProgressListener!=null){
					onProgressListener.onProgressListener(id,line);
				}
				continue;
			}
			int ret = p.waitFor();// 
			System.out.println(ret);
			onProgressListener.onProgressStateChangedListener(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {

	}




}
