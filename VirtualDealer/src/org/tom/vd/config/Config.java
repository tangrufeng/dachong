package org.tom.vd.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>
 * Title: Config.java
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
 * @date 2016-1-10 ÏÂÎç2:46:53
 * @version 1.0
 * 
 */
public class Config {

	Properties prop = new Properties();
	private static Config cfg;

	private Config() {
		init();
	}

	public static Config getCfg() {
		if (cfg == null) {
			cfg = new Config();

		}
		return cfg;
	}

	private void init() {
		System.out.println("Current context path==>"+Config.class.getResource("/").getPath());
		InputStream in = Config.class.getResourceAsStream("/config.properties");
		try {
			if(in==null){
				System.out.println("Can not found the config.properties!!!!!!");
			}else{
				prop.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getString(String name){
		return prop.getProperty(name);
	}
}
