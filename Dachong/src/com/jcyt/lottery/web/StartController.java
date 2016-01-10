package com.jcyt.lottery.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcyt.lottery.commons.BaseController;
import com.jcyt.lottery.dto.JsonResult;
import com.jcyt.lottery.service.GameRoundInfoService;

/**
 * <p>
 * Title: OrderController.java
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
 * @date 2014-10-29 下午5:55:22
 * @version 1.0
 * 
 */

@Controller
public class StartController extends BaseController {

	@Autowired
	private GameRoundInfoService oService = null;


	@RequestMapping("begin")
	@ResponseBody
	public void numberOrder() {
		oService.getGameRoundInfo();
	}
}
