/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author RivaSyafri
 */
public class Connector {
    private SerialPort serialPort;
    
    private byte[] buffer;
    
    /**
     *
     * @param COM
     */
    public Connector(String COM) {
        serialPort = new SerialPort(COM);
        try {
            // Open Serial Port
            serialPort.openPort();

            // Define Parameter -- can be found in Device Manager
            // baudRate, iataBits, stopBits, parity
            serialPort.setParams(9600,8,1,0);
        } catch (SerialPortException ex) {
            System.out.println(ex);

        }
    }
    
    /**
     * Open connection to port
     */
    public void openConnection(){
        try {
            serialPort.openPort();
            
            // Define Parameter -- can be found in Device Manager
            // baudRate, iataBits, stopBits, parity
            serialPort.setParams(9600,8,1,0);
        } catch (SerialPortException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Close connection to port
     */
    public void closeConnection() {
        try {
            serialPort.closePort();
        } catch (SerialPortException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Set port name to connection
     * @param COM port name
     */
    public void setPortName(String COM) {
        closeConnection();
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
            serialPort.writeBytes("4".getBytes());
            //Retrieve data from Arduino -- read 10 bytes
            buff = serialPort.readBytes(10);
        } catch (SerialPortException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
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
