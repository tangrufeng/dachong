package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title: TestClass.java
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
 * @date 2016-1-10 上午10:16:50
 * @version 1.0
 * 
 */
public class TestClass {

	private static String regexStr="\\d+:.*?-->.*?.\\d+";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Pattern p = Pattern.compile("\\d+:.*?-->.*?.\\d+");
//		Matcher m = p.matcher(s);
//		PatternCompiler compiler =new Perl5Compiler();
//		Pattern patternDuration = compiler.compile(regexDuration,Perl5Compiler.CASE_INSENSITIVE_MASK);
		processFLV("");
		

//		Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}.\\d{2}");
//		Matcher m = pattern.matcher("5216kB time=00:00:22.20 bitrate=1924");
//		if(m.find()){
//			System.out.println(m.group());
//		}
	}

	private static String processFLV(String inputPath) {
		 List<String> commend = new java.util.ArrayList<String>();
		 commend.add("E:\\X\\ffmpeg\\ffmpeg\\bin\\ffmpeg.exe");
		 commend.add("-re");
		 commend.add("-i");
		 commend.add("E:\\X\\screen.mp4");
		 commend.add("-vcodec");
		 commend.add("copy");
		 commend.add("-f");
		 commend.add("mp4");
		 commend.add("rtmp://localhost/tom/mylive");

		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.redirectErrorStream(true);
			Process p = builder.command(commend).start();

			// 1. start
			BufferedReader buf = null; // 保存ffmpeg的输出结果流
			String line = null;

			buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

			StringBuffer sb = new StringBuffer();
			while ((line = buf.readLine()) != null) {
				System.out.println(line);
				if(line.startsWith("frame=")){
					System.out.println(line);
					Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}.\\d{2}");
					Matcher m = pattern.matcher(line);
					if(m.find()){
						System.out.println(m.group());
					}
				}
				continue;
			}
			int ret = p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
			// 1. end
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
