package org.bc.utils;
import gnu.io.SerialPort;

/**
 * 串口参数封装类
 * @author Administrator
 *
 */
public class SerialPortParameter {
    /**
     * 串口名称(COM1、COM2、COM3等等)
     */
    private String serialPortName;

    /**
     * 波特率
     * 默认：9600
     */
    private int baudRate;

    /**
     * 数据位
     * 可以设置的值：SerialPort.DATABITS_5、SerialPort.DATABITS_6、SerialPort.DATABITS_7、SerialPort.DATABITS_8
     * 默认：SerialPort.DATABITS_8
     */
    private int dataBits;

    /**
     * 停止位
     * 可以设置的值：SerialPort.STOPBITS_1、SerialPort.STOPBITS_2、SerialPort.STOPBITS_1_5
     * 默认：SerialPort.STOPBITS_1
     */
    private int stopBits;

    /**
     * 校验位
     * 可以设置的值：SerialPort.PARITY_NONE、SerialPort.PARITY_ODD、SerialPort.PARITY_EVEN、SerialPort.PARITY_MARK、SerialPort.PARITY_SPACE
     * 默认：SerialPort.PARITY_NONE
     */
    private int parity;

    /**
     * 默认参数：
     *  波特率：9600
     * 	数据位：8位
     * 	停止位：1位
     * 	校    验：无
     * @param serialPortName 串口名称, "COM1"、"COM2"
     */
    public SerialPortParameter(String serialPortName) {
        this.serialPortName = serialPortName;
        this.baudRate = 9600;
        this.dataBits = SerialPort.DATABITS_8;
        this.stopBits = SerialPort.STOPBITS_1;
        this.parity = SerialPort.PARITY_NONE;
    }

    /**
     * 默认参数：
     * 	数据位：8位
     * 	停止位：1位
     * 	校    验：无
     * @param serialPortName 串口名称, "COM1"、"COM2"
     * @param baudRate 波特率
     */
    public SerialPortParameter(String serialPortName, int baudRate) {
        this.serialPortName = serialPortName;
        this.baudRate = baudRate;
        this.dataBits = SerialPort.DATABITS_8;
        this.stopBits = SerialPort.STOPBITS_1;
        this.parity = SerialPort.PARITY_NONE;
    }

    public String getSerialPortName() {
        return serialPortName;
    }

    public void setSerialPortName(String serialPortName) {
        this.serialPortName = serialPortName;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    @Override
    public String toString() {
        return "SerialPortParameter [serialPortName=" + serialPortName
                + ", baudRate=" + baudRate + ", dataBits=" + dataBits
                + ", stopBits=" + stopBits + ", parity=" + parity + "]";
    }
}
