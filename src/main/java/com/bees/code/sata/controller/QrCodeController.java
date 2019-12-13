package com.bees.code.sata.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Title: QrCodeController.java 
 * Description:   
 * Copyright: Copyright (c) 2019
 * Company: 浙江数蜂科技有限公司
 * @author lds
 * @date 2019年9月25日  
 * @version 1.0
 */
@RestController
@Slf4j
public class QrCodeController {

	 @RequestMapping(value = "/postSerialData")     
	 public String test(@RequestParam Map<String, String> params) {         
	
		 log.info("params begin");
		 params.forEach((key,value) -> {
			 log.info("key:" + key + ",value:" + value);
		 });
		 log.info("params end");
		 
		 return "码数据接收成功！";
	 }
	
	
}
