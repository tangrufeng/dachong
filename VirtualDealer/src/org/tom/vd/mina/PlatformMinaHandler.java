package org.tom.vd.mina;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.tom.vd.config.Config;

import com.effecia.mina.MinaCommandPo;

public class PlatformMinaHandler extends IoHandlerAdapter {
	private final static Logger logger = Logger.getLogger(PlatformMinaHandler.class);
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		//this.sessionClosed(session);
		logger.error("Exception session: " + session.getId());
		logger.error("Exception caught by MINA!" + cause.getMessage());
		cause.printStackTrace();
		session.close(true);
	}
	
//	@Override
//	public void messageSent(IoSession session, Object message) throws Exception {
//		// TODO Auto-generated method stub
//		
//		if (message instanceof MinaCommandPo){
//			MinaCommandPo minaCommandPo = (MinaCommandPo)message;
//			if("ONLINE_INFO".equals(minaCommandPo.getCommandType())||"SEND_RECORD".equals(minaCommandPo.getCommandType())){
////				logger.debug("PlatformMinaHandler messageSent sessionId:"+session.getId()+" " +minaCommandPo.getCommandType());
//			}else{
//				logger.debug("PlatformMinaHandler messageSent sessionId:"+session.getId()+" " +message);
//			}
//		}
//		super.messageSent(session, message);
//	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		MinaCommandPo po = (MinaCommandPo)message;
		//logger.debug("Incoming platform msg: " + po.toString());
		logger.info("message Received" + message.toString());
		
		if(ApCommand.TABLE_INIT.name().equals(po.getCommandType())){

			MinaCommandPo roomStatusPO = new MinaCommandPo();
			roomStatusPO.setRequestType("REQUEST");
			roomStatusPO.setModuleType("AP");
			roomStatusPO.setCommandType(ApCommand.TABLE_INIT.name());
			Map<String,Object> map=roomStatusPO.getParameter();
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			Map<String,String> room=new HashMap<String, String>();
			room.put("tableStatus", TableStatus.BETTING.name());	//1锛嶏紞绛夊緟鎶曟敞銆��2--鍋滄鎶曟敞
			room.put("timeline", "40");
			room.put("tableId","1");
			room.put("currentGameId", "1");
			room.put("currentShoeId", "1");
			list.add(room);
			map.put("roomList", list);
			MinaGameInterface.ioSession.write(roomStatusPO);
			
		}
		
		
	
	}
	

	@Override
	public void sessionClosed(IoSession session){
		logger.warn("Platform server connection lost, try to reconnect...sessionId:"+session.getId());
		IoSession newsession;
		ConnectFuture cf;
		while(true){
			try{
				cf = MinaGameInterface.connector.connect(new InetSocketAddress(Config.getCfg().getString("minahost"),4533));
				cf.awaitUninterruptibly();
				MinaGameInterface.ioSession = cf.getSession();
				break;
			}catch(Exception e){
				logger.error("Can not connect to caopan server, going to give it another try! sessionId:"+session.getId());
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
		}
		
		MinaCommandPo minaCommandPo= new MinaCommandPo();
		minaCommandPo.setRequestType("REQUEST");
		minaCommandPo.setModuleType("AP");
		minaCommandPo.setCommandType("LOGIN_PLATFORM");
		minaCommandPo.getParameter().put("passWord", "ssss");
		minaCommandPo.getParameter().put("hashCode","sss");
		minaCommandPo.getParameter().put("web111SessionId","abc");
		MinaGameInterface.ioSession.write(minaCommandPo);
	}
}