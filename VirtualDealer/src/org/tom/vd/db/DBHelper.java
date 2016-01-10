package org.tom.vd.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.tom.vd.config.Config;

/**
 * <p>Title: DBOperator.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2016-1-10 下午2:43:33
 * @version 1.0
 *
 */
public class DBHelper {
	
	private static Logger log=Logger.getLogger(DBHelper.class);
	
	private static DBHelper helper;
	public Connection conn = null;  
    private Config cfg=Config.getCfg();
    private DBHelper() {  
        try {  
            Class.forName(cfg.getString("jdbc.driverClassName"));//指定连接类型  
            connDB();
        } catch (Exception e) {  
        	e.printStackTrace();
        	log.error("连接数据库失败", e);
        }  
    }  
    
    private void connDB(){
        try {  
            conn = DriverManager.getConnection(cfg.getString("jdbc.url"), cfg.getString("jdbc.username"), cfg.getString("jdbc.userpassword"));//获取连接
            PreparedStatement ps=conn.prepareStatement("select 1 from dual");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	if(1==rs.getInt(1)){
            		log.error("数据库连接成功...");
            	}
            }
        } catch (Exception e) {  
        	e.printStackTrace();
        	log.error("连接数据库失败", e);
        }  
    }
    
    public List<Map<String,String>> getGameRoundInfo(){
    	List<Map<String,String>> rst=new ArrayList<Map<String,String>>();
    	PreparedStatement ps=null;
    	try {
			if(conn==null || conn.isClosed()){
				connDB();
			}
			ps = conn.prepareStatement(cfg.getString("sql.getGameRoundInfo"));
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Map<String,String> map=new HashMap<String, String>();
				map.put("id", rs.getString(1));
				map.put("result", rs.getString(2));
				map.put("createTime", rs.getString(3));
				map.put("gameType", rs.getString(4));
				map.put("videoPath", rs.getString(5));
				map.put("cardInfo", rs.getString(6));
				map.put("dealer", rs.getString(7));
				map.put("groupId", rs.getString(8));
				map.put("lastPlayTime", rs.getString(9));
				rst.add(map);
			}
		} catch (SQLException e) {
        	e.printStackTrace();
        	log.error("连接数据库失败", e);
		}finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
		}
    	return rst;
    }
    
    public static DBHelper getHelper(){
    	if(helper==null){
    		helper=new DBHelper();
    	}
    	return helper;
    }
  
    public void close() {  
        try {  
            this.conn.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DBHelper.getHelper().getGameRoundInfo());
	}

}
