package com.jcyt.lottery.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * <p>
 * Title: Test.java
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
 * @date 2015-12-27 下午5:13:53
 * @version 1.0
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {

			ServerSocket ss = new ServerSocket(18188);
			while (true) {
				Socket s = ss.accept();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(s.getInputStream()));
				System.out.println("====>" + reader.readLine());
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
