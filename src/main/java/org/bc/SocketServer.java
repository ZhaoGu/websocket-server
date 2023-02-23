package org.bc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.TooManyListenersException;

import gnu.io.*;
import org.bc.utils.SerialPortUtil;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class SocketServer extends WebSocketServer {
    // 打开串口
    SerialPort serialPort=null;
    public SocketServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        try {
            serialPort=SerialPortUtil.openSerialPort("COM5");
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
    }

    public SocketServer(InetSocketAddress address) {
        super(address);
        try {
            serialPort=SerialPortUtil.openSerialPort("COM5");
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // This method sends a message to the new client
        conn.send("Welcome to the server!");
        broadcast("new connection: " + handshake
                .getResourceDescriptor()); // This method sends a message to all clients connected
        System.out.println(
                conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");

    }

    @Override
    public void onMessage(WebSocket conn, String message) {


        System.out.println("接收到的消息："+message);
        broadcast("转发的消息"+message);
        System.out.println(message.equals("010203"));
        if(message.equals("010203")){


            try {

                // 开启分线程,每个2s钟向串口发送数据
                new Thread(() -> {
                    //while (true) {
                        String s = "AABBCCDDEEFF";
                        byte[] bytes = s.getBytes();
                        SerialPortUtil.sendData(serialPort, bytes);// 发送数据
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                   // }
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
                            broadcast("读取串口信息成功:"+new String(bytes));
                        }
                    }
                });

                //for(;;); // 防止程序退出

            } catch ( TooManyListenersException e) {
                e.printStackTrace();
            }
        }







        System.out.println("转发的消息："+message);
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific
            // websocket
        }

    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);

    }

}

