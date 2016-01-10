package com.jcyt.lottery.commons;


import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcyt.lottery.dto.JsonResult;

/**
 * <p>Title: BaseController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2014-11-22 下午4:02:05
 * @version 1.0
 *
 */
public class BaseController {
	
	private static Logger log=Logger.getLogger(BaseController.class);
	
	/**
	 * 异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)	
	public @ResponseBody String handleException(RuntimeException ex){
		log.error(ex);
		ex.printStackTrace();
		JsonResult jr=new JsonResult();
		jr.setError(true);
		jr.setRespCode(JsonResult.SYS_EXCEPTION);
		jr.setRespMsg("Take it easy,as you see,some strange things happened!(o^_^o)");
		return jr.toString();
	}
}
