package org.tom.vd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.tom.vd.config.Config;

/**
 * <p>Title: VirtualDealer.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2016-1-10 ÏÂÎç3:42:44
 * @version 1.0
 *
 */
public class Main {
	private void initLog4j(){
		System.out.println("³õÊ¼»¯log4j........");
		Properties prop = new Properties();
		InputStream in = Main.class.getResourceAsStream("/log4j.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(prop);
	}
	
	private void init(){
		initLog4j();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
