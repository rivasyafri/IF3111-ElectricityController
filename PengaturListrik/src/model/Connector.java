/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author RivaSyafri
 */
public class Connector {
    
    private boolean status = true;
    
    private SerialPort serialPort;
    
    private byte[] buffer;
    
    private BufferedReader input;
    
    /**
     *
     * @param COM
     * @throws jssc.SerialPortException
     */
    public Connector(String COM) {
        try {
            serialPort = new SerialPort(COM);
            serialPort.openPort();
            serialPort.setParams(9600,8,1,0);
        } catch (SerialPortException ex) {
            status = false;
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Open connection to port
     * @throws jssc.SerialPortException
     */
    public void open() {
        try {
            serialPort.openPort();
            serialPort.setParams(9600,8,1,0);
            status = true;
        } catch (SerialPortException ex) {
            status = false;
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Close connection to port
     */
    public void close() {
        try {
            serialPort.closePort();
        } catch (SerialPortException ex) {
            status = true;
            System.out.println(ex);
        }
        status = false;
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
     * @throws jssc.SerialPortException exception if port is not connected
     */
    public void pushData(String action) throws SerialPortException {
        serialPort.writeBytes(action.getBytes());
    }   
    
    /**
     * Get connection status
     * @return true if connected
     */
    public boolean getStatus() {
        return status;
    }
}
