package com.jcyt.lottery.dto;

import java.io.Serializable;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class JsonResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final JsonResult jr=new JsonResult();
	/**
	 * 系统错误异常码
	 */
	public final static String SYS_EXCEPTION = "-99";
	/**
	 * 登陆超时或者非法访问请求
	 */
	public final static String DENIED_CODE="-1";
	
	
	private String respCode="0";
	private boolean isError=false;
	private String respMsg="";
	private Object data="";
	
	public JsonResult(){
		
	}
	public JsonResult(Object data){
		this.data=data;
	}

	
	public static JsonResult failure(String code,String msg){
		JsonResult  result=new JsonResult();
		result.respCode=code;
		result.respMsg=msg;
		result.isError=true;
		return result;
	}
	
	public static JsonResult success(){
		return jr;
	}
	
	
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}



	public boolean isError() {
		return isError;
	}



	public void setError(boolean isError) {
		this.isError = isError;
	}
	
	
	public String toString(){
		return JSONObject.fromObject(this).toString();
	}

}
