package com.jcyt.lottery.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jcyt.lottery.listener.OnProgressListener;

/**
 * <p>Title: Red5ClientService.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2016-1-10 上午11:08:51
 * @version 1.0
 *
 */
@Component
public class Red5Client extends Thread{
	
	private OnProgressListener onProgressListener;

	
	public void setOnProgressListener(OnProgressListener listener){
		this.onProgressListener=listener;
	}
	
	
	
	public void processFLV(String red5Host,String filePath){
		 List<String> commend = new java.util.ArrayList<String>();
		 commend.add("E:\\X\\ffmpeg\\ffmpeg\\bin\\ffmpeg.exe");
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

			BufferedReader buf = null; // 保存ffmpeg的输出结果流
			String line = null;

			buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

			while ((line = buf.readLine()) != null) {
				System.out.println(line);
				if(onProgressListener!=null){
					onProgressListener.onProgressListener(line);
				}
				continue;
			}
			int ret = p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
			System.out.println(ret);
			onProgressListener.onProgressStateChangedListener(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
