package com.bees.code.sata.service.impl;

import org.springframework.stereotype.Service;

import com.bees.code.sata.service.ISerialPortService;
import com.bees.code.sata.util.ByteUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Title: SerialPortServiceImpl.java 
 * Description:  串口服务接口实现类 
 * Copyright: Copyright (c) 2019
 * Company: 浙江数蜂科技有限公司
 * @author lds
 * @date 2019年9月25日  
 * @version 1.0
 */
@Service
@Slf4j
public class SerialPortServiceImpl implements ISerialPortService {

    @Override
    public void serialPortMsgHandler(String msg) {
        log.info("收到端口消息： " + msg);
    }

	@Override
	public void serialPortMsgHandlerForBytes(byte[] msgBytes) {
		// TODO Auto-generated method stub
		log.info("扫码数据： " + ByteUtils.BinaryToHexString(msgBytes));
	}
}
