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
package controller;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import jssc.SerialPortException;
import model.SerialConnection;

/**
 * Controller for Serial Connection
 * @author Riva Syafri Rachmatullah
 */
public class ConnectionController {
    private boolean connectionStatus = false;
    private boolean buzzerStatus = false;
    private boolean muteStatus = false;
    private boolean readStatus = false;
    private boolean limitStatus = false;
    private boolean timerStatus = false;
    private double totalPower;
    private double energyLimit;
    private int timetoStop;
    private final int sizeofQueue = 1000;
    private SerialConnection con;
    private Timer timer;
    
    public ConnectionController() {}
    
    public void connect(String COM) throws SerialPortException {
        con = new SerialConnection(COM);
        con.open();
        connectionStatus = true;
    }
    
    public void disconnect() throws SerialPortException {
        con.close();
        connectionStatus = false;
    }
    
    public double readData() {
        try {
            String rawdata = con.readData();
            Double data;
            if (rawdata == null || rawdata.isEmpty()) {
                data = (double) 0;
            } else {
                data = Double.valueOf(rawdata);
            }
            totalPower += data;
            checkEnergyLimit();
            return data;
        } catch (SerialPortException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public void switchBuzzerStatus() throws SerialPortException {
        Integer buzzer;
        if (buzzerStatus) {
            buzzer = 1;
        } else {
            buzzer = 0;
        }
        buzzer += 2;
        con.writeData(buzzer.toString());
        buzzerStatus = !buzzerStatus;
    }
    
    public void switchMuteStatus() {
        muteStatus = !muteStatus;
    }
    
    public void switchReadStatus() throws SerialPortException {
        Integer passing;
        if (readStatus) {
            passing = 0;
        } else {
            passing = 1;
        }
        con.writeData(passing.toString());
        readStatus = !readStatus;
    }
    
    public void setEnergyLimit(double energyLimit) {
        this.energyLimit = energyLimit;
        limitStatus = true;
    }
    
    public void setTimerStatus(boolean timerStatus) {
        this.timerStatus = timerStatus;
    }
    
    public void setTime(int timetoStop) {
        this.timetoStop = timetoStop;
    }
    
    public void timerON() {
        timer = new Timer(1000, (ActionEvent e) -> {
            checkTimeLimit();
        });
        timer.start();
    }
    
    public boolean getConnectionStatus() {
        return connectionStatus;
    }
    
    public boolean getBuzzerStatus() {
        return buzzerStatus;
    }
    
    public boolean getMuteStatus() {
        return muteStatus;
    }
    
    public boolean getReadStatus() {
        return readStatus;
    }
    
    public boolean getLimitStatus() {
        return limitStatus;
    }
    
    public double getTotalPower() {
        return totalPower;
    }
    
    private void checkEnergyLimit() throws SerialPortException {
        if (limitStatus) {
            if (totalPower/3600 >= energyLimit ){
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(new  JFrame(), "Limit has been reached", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                };
                t.start();
                limitStatus = false;
                con.writeData("0");
                readStatus = false;
                if (!muteStatus) {
                    con.writeData("2");
                    buzzerStatus = true;
                }
            } else if ((totalPower/3600) >= (energyLimit*0.9)) {
                if (!muteStatus) {
                    switchBuzzerStatus();
                }
            }
        }
    }

    private void checkTimeLimit() {
        if (timerStatus) {
            System.out.println(timetoStop);
            try {
                if (timetoStop == 0) {
                    timer.stop();
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(new JFrame(), "Limit has been reached", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                        }
                    };
                    t.start();
                    timerStatus = false;
                    con.writeData("0");
                    readStatus = false;
                    if (!muteStatus) {
                        con.writeData("2");
                        buzzerStatus = true;
                    }
                } else if (timetoStop <= 10) {
                    if (!muteStatus) {
                        switchBuzzerStatus();
                    }
                    timetoStop--;
                } else {
                    timetoStop--;
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
