# java 串口通信

#### 项目介绍
java 串口通信

#### 软件架构
spring boot + rxtx + jdk1.8

#### 安装教程

1. 下载对应版本的rxtx 解压后得到三个文件 rxtxParallel.dll、rxtxSerial.dll、RXTXcomm.jar/
2. 将rxtxParallel.dll、rxtxSerial.dll 放进当前使用的jdk的bin目录（ **jdk版本必须与下载的rxtx一致** ，区分32位/64位 AMD/IA）
3. (windows x64系统可以将当前项目libs下的 rxtxParallel.dll、rxtxSerial.dll 直接放入jdk下的bin目录)
4.  **需要将libs下的RXTXcomm.jar加入项目依赖** 

#### 使用说明

1. 当前项目的是windows x64
2. 当前使用jdk1.8
