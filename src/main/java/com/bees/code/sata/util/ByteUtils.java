package com.bees.code.sata.util;

/**
 * 
 * Title: ByteUtils.java 
 * Description: 字节流工具类  
 * Copyright: Copyright (c) 2019
 * Company: 浙江数蜂科技有限公司
 * @author lds
 * @date 2019年9月25日  
 * @version 1.0
 */
public class ByteUtils {

	private static String hexStr =  "0123456789ABCDEF";  
    private static String[] binaryArray =   
        {"0000","0001","0010","0011",  
        "0100","0101","0110","0111",  
        "1000","1001","1010","1011",  
        "1100","1101","1110","1111"};  
          
    public static void main(String[] args)   
    {  
        String str = "二进制与十六进制互转测试";  
        System.out.println("源字符串：\n"+str);  
          
        String hexString = BinaryToHexString(str.getBytes());  
        System.out.println("转换为十六进制：\n"+hexString);  
        System.out.println("转换为二进制：\n"+bytes2BinaryStr(str.getBytes()));  
          
        byte [] bArray = fromHexString(hexString);  
        System.out.println("将str的十六进制文件转换为二进制再转为String：\n"+new String(bArray));  
      
    }  
    
    /**
     * 字节数组转为普通字符串（ASCII对应的字符）
     * 
     * @param bytearray
     *            byte[]
     * @return String
     */
    public static String bytetoString(byte[] bytearray) {
        String result = "";
        char temp;

        int length = bytearray.length;
        for (int i = 0; i < length; i++) {
            temp = (char) bytearray[i];
            System.out.println(bytearray[i]);
            System.out.println(temp);
            result += temp;
        }
        return result;
    }
    
    
    /** 
     *  
     * @param str 
     * @return 转换为二进制字符串 
     */  
    public static String bytes2BinaryStr(byte[] bArray){  
          
        String outStr = "";  
        int pos = 0;  
        for(byte b:bArray){  
            //高四位  
            pos = (b&0xF0)>>4;  
            outStr+=binaryArray[pos];  
            //低四位  
            pos=b&0x0F;  
            outStr+=binaryArray[pos];  
        }  
        return outStr;  
          
    }  
    /** 
     *  
     * @param bytes 
     * @return 将二进制转换为十六进制字符输出 
     */  
    public static String BinaryToHexString(byte[] bytes){  
          
        String result = "";  
        String hex = "";  
        for(int i=0;i<bytes.length;i++){  
            //字节高4位  
            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));  
            //字节低4位  
            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));  
            result +=hex;  
        }  
        return result;  
    }  
    /**
     * 将16进制字符串转换为byte[]
     * @explain 16进制字符串不区分大小写，返回的数组相同
     * @param hexString
     *            16进制字符串
     * @return byte[]
     */
    public static byte[] fromHexString(String hexString) {
        if (null == hexString || "".equals(hexString.trim())) {
            return new byte[0];
        }

        byte[] bytes = new byte[hexString.length() / 2];
        // 16进制字符串
        String hex;
        for (int i = 0; i < hexString.length() / 2; i++) {
            // 每次截取2位
            hex = hexString.substring(i * 2, i * 2 + 2);
            // 16进制-->十进制
            bytes[i] = (byte) Integer.parseInt(hex, 16);
        }

        return bytes;
    }
	
	
}
