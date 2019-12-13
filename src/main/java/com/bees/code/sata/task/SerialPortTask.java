package com.bees.code.sata.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bees.code.sata.core.SerialPortCore;
import com.bees.code.sata.util.ByteUtils;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Title: SerialPortTask.java 
 * Description:  串口任务 
 * Copyright: Copyright (c) 2019
 * Company: 浙江数蜂科技有限公司
 * @author lds
 * @date 2019年9月25日  
 * @version 1.0
 */
@Component
@Slf4j
public class SerialPortTask implements CommandLineRunner {

    @Value("${serialPortName}")
    private String serialPortName;

    @Autowired
    private SerialPortCore serialPortCore;

    @Override
    public void run(String... args) throws Exception {
    	log.info("开启串口任务。");
    	serialPortCore.listPort();
    	// 连接
        try {
			serialPortCore.connect(serialPortName);
			log.info("向串口发送数据：");
			serialPortCore.sendMsg("hello world!");
			
		} catch (NoSuchPortException e) {
			log.error(serialPortName + "串口不存在，请确认本机串口，并修改串口配置后重试!");
			Thread.sleep(5000);
			System.exit(1);
		} catch (PortInUseException e) {
			log.error(serialPortName + "串口被占用，请确认本机是否已开启串口程序!");
			Thread.sleep(5000);
			System.exit(1);
		}
        //Thread.sleep(2000);
    }
}
