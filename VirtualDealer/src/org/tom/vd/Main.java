package org.tom.vd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.tom.vd.mina.ApCommand;
import org.tom.vd.mina.MinaGameInterface;

import com.effecia.mina.MinaCommandPo;

/**
 * <p>Title: VirtualDealer.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2016-1-10 下午3:42:44
 * @version 1.0
 *
 */
public class Main {
	private void initLog4j(){
		System.out.println("初始化log4j........");
		Properties prop = new Properties();
		InputStream in = Main.class.getResourceAsStream("/log4j.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(prop);
	}
	
	private void initMina(){
		System.out.println("初始话Mina，并注册..........");
		new MinaGameInterface();
		MinaCommandPo regiestPo  = new MinaCommandPo();
		regiestPo.setModuleType("PLATFORM");
		regiestPo.setRequestType("REQUEST");
		regiestPo.setCommandType(ApCommand.TABLE_INIT.toString());
		MinaGameInterface.ioSession.write(regiestPo);
		System.out.println("初始话Mina，并注册结束..........");
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
