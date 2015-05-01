/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author RivaSyafri
 */
public class Connector {
    
    private SerialPort serialPort;
    
    private byte[] buffer;
    
    private BufferedReader input;
    
    /**
     *
     * @param COM
     */
    public Connector(String COM) {
        serialPort = new SerialPort(COM);
        try {
            // Open Serial Port
            System.out.println("Port opened: " + serialPort.openPort());

            // Define Parameter -- can be found in Device Manager
            // baudRate, iataBits, stopBits, parity
            System.out.println("Params setted: " + serialPort.setParams(9600, 8, 1, 0));
        } catch (SerialPortException ex) {
            System.out.println(ex);

        }
    }
    
    /**
     * Open connection to port
     */
    public void open(){
        try {
            serialPort.openPort();
            
            // Define Parameter -- can be found in Device Manager
            // baudRate, iataBits, stopBits, parity
            serialPort.setParams(9600,8,1,0);
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Close connection to port
     */
    public void close() {
        try {
            serialPort.closePort();
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Set port name to connection
     * @param COM port name
     */
    public void setPortName(String COM) {
        close();
        serialPort = new SerialPort(COM);
    }
    
    /**
     * Get port name
     */
    public void getPortName() {
        serialPort.getPortName();
    }
    
    /**
     * Get data sent by serial communication
     * @return data as byte[]
     */
    public byte[] pullData() {
        byte[] buff = null;
        try {
            //serialPort.writeBytes("4".getBytes());
            //Retrieve data from Arduino -- read 8 bytes
            buff = serialPort.readBytes(5);
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
        return buff;
    }
    
    /**
     * Send data to serial communication
     * @param action action preference
     * @throws jssc.SerialPortException exception that must be handled if failed to send
     */
    public void pushData(String action) throws SerialPortException {
        serialPort.writeBytes(action.getBytes());
    }
    
}
