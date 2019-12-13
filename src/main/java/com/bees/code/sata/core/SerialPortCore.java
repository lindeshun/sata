package com.bees.code.sata.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bees.code.sata.service.ISerialPortService;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Title: SerialPortCore.java 
 * Description:   串口配置类
 * Copyright: Copyright (c) 2019
 * Company: 浙江数蜂科技有限公司
 * @author lds
 * @date 2019年9月25日  
 * @version 1.0
 */
@Component
@Slf4j
public class SerialPortCore {
    private int timeout = 2000;//open 端口时的等待时间

    @Value("${baudRate}")
    private int baudRate;
    @Value("${dataBits}")
    private int dataBits;
    @Value("${stopBits}")
    private int stopBits;
    @Value("${parity}")
    private int parity; // 0 表示none
    @Value("${serialData.charset:}")
    private String charset;
    @Value("${serialData.url:}")
    private String url;

    private SerialPort serialPort ;

    
    
    @Autowired
    public ISerialPortService serialPortService;


    /**
     * @方法名称 :listPort
     * @功能描述 :列出所有可用的串口
     * @返回值类型 :void
     */
    @SuppressWarnings("rawtypes")
    public List<String> listPort(){
        List<CommPortIdentifier> cpidList = new ArrayList<>();
        List<String> portNameList = new ArrayList<>();
        Enumeration en = CommPortIdentifier.getPortIdentifiers();

        //System.out.println("now to list all Port of this PC：" +en);
        
        while(en.hasMoreElements()){
            CommPortIdentifier cpid = (CommPortIdentifier)en.nextElement();
            portNameList.add(cpid.getName());
            if(cpid.getPortType() == CommPortIdentifier.PORT_SERIAL){
                cpidList.add(cpid);
            }
        }
        if(cpidList.size() == 0) {
        	log.error("当前没有可用串口！");
        }else {
        	log.info("总共有" + cpidList.size() + "个可用串口：" + portNameList.toString());
        }
        return portNameList;
    }

    public void connect (String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() ) {
            //System.out.println("Error: Port is currently in use");
        	log.warn(portName + "串口已被占用！");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),timeout);
            if ( commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                // 参数
                serialPort.setSerialPortParams(baudRate,dataBits,stopBits,parity);

                InputStream in = serialPort.getInputStream();
                if(StringUtils.isEmpty(charset) || !"utf-8".equalsIgnoreCase(charset)) {
                	charset = "gbk";
                }
                
                serialPort.addEventListener(new SerialReader(in,serialPortService,charset,url,stopBits));
                serialPort.notifyOnDataAvailable(true);

                log.info("连接串口： " + portName + " 成功");
            } else {
                log.error("Error: Only serial ports are handled by this example.");
            }
        }
    }

    
    

    /** */
    public class SerialWriter implements Runnable {
        OutputStream out;
        public SerialWriter (OutputStream out) {
            this.out = out;
        }

        public void run () {
            try {
                int c = 0;
                while ( ( c = System.in.read()) > -1 ) {
                    this.out.write(c);
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
    public void sendMsg(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            try {
                OutputStream out = serialPort.getOutputStream();
                out.write(msg.getBytes());
                //(new Thread(new SerialPortCore.SerialWriter(out))).start();
            }catch (IOException e) {
                log.error("消息发送失败",e);
            }
        }
    }
}
