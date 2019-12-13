package com.bees.code.sata.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.bees.code.sata.service.ISerialPortService;
import com.bees.code.sata.util.ArrayUtils;
import com.bees.code.sata.util.Base64;
import com.bees.code.sata.util.HttpUtils;
import com.bees.code.sata.websocket.WebSocketServer;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Title: SerialReader.java 
 * Description:   串口数据读取器
 * Copyright: Copyright (c) 2019
 * Company: 浙江数蜂科技有限公司
 * @author lds
 * @date 2019年9月25日  
 * @version 1.0
 */
@Slf4j
public class SerialReader implements SerialPortEventListener {
    private InputStream in;
    private ISerialPortService serialPortService;
    private String charset;
    private String url;
    private int stopBits;

    public SerialReader ( InputStream in ,ISerialPortService serialPortService, String charset, String url,int stopBits) {
        this.in = in;
        this.serialPortService = serialPortService;
        this.charset = charset;
        this.url = url;
        this.stopBits = stopBits;
    }

    /**
	 * 从串口读取数据
	 * 
	 * @param serialPort
	 *            当前已建立连接的SerialPort对象
	 * @return 读取到的数据
	 */
	public byte[] readFromPort(InputStream in) {
		byte[] bytes = {};
		try {
			// 缓冲区大小为一个字节
			byte[] readBuffer = new byte[1];
			int bytesNum = in.read(readBuffer);
			while (bytesNum > 0) {
				bytes = ArrayUtils.concat(bytes, readBuffer);
				bytesNum = in.read(readBuffer);
			}
			if(bytes.length > stopBits) {
				byte[] bytes1 = new byte[bytes.length - stopBits];
				System.arraycopy(bytes, 0, bytes1, 0, bytes.length - stopBits);
				bytes = bytes1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}
    
    public void serialEvent(SerialPortEvent arg0) {
    	byte[] readBuffer = readFromPort(in);
        String inputStr ;
    	try {
        	if(readBuffer.length < 250) {
        		//长度小于250位，以字符串形式输出
        		//System.out.println("16进制数据：" + ByteUtils.BinaryToHexString(readBuffer));
        		inputStr = new String(readBuffer,charset);
        		serialPortService.serialPortMsgHandler(inputStr);
        	}else {
        		//长度大于250位，以字节数组形式输出
        		serialPortService.serialPortMsgHandlerForBytes(readBuffer);
        		//对字节数组做base64编码
        		inputStr = Base64.encode(readBuffer);
        		log.info("二维码base64编码数据：" + inputStr);
        	}
            //把读取到的串口数据发送到指定url地址
        	Map<String,String> requestMap = new HashMap<>();
        	requestMap.put("qrCodeData", inputStr);
        	String result = HttpUtils.getInstance().sendHttpPost(url, requestMap);
        	
        	// TODO 从result 拿到用户的姓名、身份证信息
        	String[] names = {"小明","小王","小张"};
        	//通过websocket把用户姓名、身份证号推送到前端
        	WebSocketServer.sendInfo("name:" + names[new Random().nextInt(3)] +",cardNumber:"+new Random().nextInt(20));
        	
        	log.info("http请求结果：" + result);
        } catch ( IOException e ) {
            log.error("串口监听事件处理异常！",e);
            System.exit(-1);
        }
    }
    
}