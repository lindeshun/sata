package com.bees.code.sata.service;

/**
 * 
 * Title: ISerialPortService.java 
 * Description: 串口服务接口类  
 * Copyright: Copyright (c) 2019
 * Company: 浙江数蜂科技有限公司
 * @author lds
 * @date 2019年9月25日  
 * @version 1.0
 */
public interface ISerialPortService {
    void serialPortMsgHandler(String msg);
    
    void serialPortMsgHandlerForBytes(byte[] msgBytes);
}
