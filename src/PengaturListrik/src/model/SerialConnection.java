/*
 * Copyright (C) 2015 Riva Syafri Rachmatullah
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package model;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * SerialConnection is a class to handle serial port used in this program
 * @author Riva Syafri Rachmatullah
 */
public class SerialConnection {
    private final SerialPort serialPort;
    
    /**
     * Create a new instance of serial connection to port
     * @param COM selected port names
     */
    public SerialConnection(String COM) {
        serialPort = new SerialPort(COM);
    }
    
    /**
     * Open the serial connection
     * @throws SerialPortException exception if device selected is busy or not 
     * connected
     */
    public void open() throws SerialPortException {
        serialPort.openPort();
        serialPort.setParams(9600,8,1,0);
    }
    
    /**
     * Close the serial connection
     * @throws SerialPortException exception if device selected is busy or not 
     * connected
     */
    public void close() throws SerialPortException {
        serialPort.closePort();
    }
    
    /**
     * Read data sent from device
     * @return data as String
     * @throws SerialPortException exception if device selected is busy or not 
     * connected
     */
    public String readData() throws SerialPortException {
        String data = new String(serialPort.readBytes(5));
        String trim = data.trim();
        return trim;
    }
    
    /**
     * Write data to device
     * @param action string that want to be written
     * @throws SerialPortException exception if device selected is busy or not 
     * connected
     */
    public void writeData(String action) throws SerialPortException {
        serialPort.writeBytes(action.getBytes());
    }
}
