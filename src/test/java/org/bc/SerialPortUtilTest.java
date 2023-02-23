package org.bc;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.util.List;
import java.util.TooManyListenersException;

import org.bc.utils.SerialPortUtil;
import org.junit.Test;

public class SerialPortUtilTest {

    /**
     * 测试获取串口列表
     */
    @Test
    public void getSystemPortList() {
        List<String> portList = SerialPortUtil.getSerialPortList();
        System.out.println(portList);
    }

    @Test
    public void serialPortAction() {
        try {
            // 打开串口
            final SerialPort serialPort = SerialPortUtil.openSerialPort("COM5");
            // 开启分线程,每个2s钟向串口发送数据
            new Thread(() -> {
                while (true) {
                    String s = "AABBCCDDEEFF";
                    byte[] bytes = s.getBytes();
                    SerialPortUtil.sendData(serialPort, bytes);// 发送数据
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            // 设置串口的listener,用来监听串口是否有数据达到：回调方法中接收串口数据
            SerialPortUtil.setListenerToSerialPort(serialPort, new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {// 数据通知
                        // 接受数据
                        byte[] bytes = SerialPortUtil.readData(serialPort);
                        System.out.println("收到的数据长度：" + bytes.length);
                        System.out.println("收到的数据：" + new String(bytes));
                    }
                }
            });

            for(;;); // 防止程序退出

        } catch (NoSuchPortException | PortInUseException
                | UnsupportedCommOperationException | TooManyListenersException e) {
            e.printStackTrace();
        }
    }
}
