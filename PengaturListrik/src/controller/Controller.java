/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;
import model.Connector;

/**
 *
 * @author RivaSyafri
 */
public class Controller {
    
    private Connector con;
    
    private Boolean currentStatus = true;
    
    private Boolean buzzerStatus = false;
    
    private Time timeLimit;
    
    private Float powerLimit;
    
    /**
     *
     * @param COM
     */
    public Controller(String COM) {
        con = new Connector(COM);
        powerLimit = null;
        timeLimit = null;
    }
    
    /**
     *
     * @param con
     */
    public Controller(Connector con) {
        this.con = con;
        powerLimit = null;
        timeLimit = null;
    }
    
    /**
     *
     * @param COM
     */
    public void changeConnection(String COM) {
        con.setPortName(COM);
        con.openConnection();
    }
    
    /**
     *
     */
    public void switchCurrentStatus() {
        try {
            currentStatus = !currentStatus;
            con.pushData(currentStatus.toString());
        } catch (SerialPortException ex) {
            currentStatus = !currentStatus;
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void switchBuzzerStatus() {
        try {
            buzzerStatus = !buzzerStatus;
            con.pushData(buzzerStatus.toString());
        } catch (SerialPortException ex) {
            buzzerStatus = !buzzerStatus;
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * get the power limit that has been set
     * @return power limit
     */
    public float getPowerConstrain() {
        return powerLimit;
    }
    
    /**
     * set the power limit 
     * @param powerLimit the new power limit
     */
    public void setPowerConstrain(float powerLimit) {
        this.powerLimit = powerLimit;
    }
    
    /**
     * get the time limit
     * @return time limit
     */
    public Time getTimeConstrain() {
        return timeLimit;
    }
    
    /**
     * set the time limit
     * @param timeLimit new time limit
     */
    public void setTimeConstrain(Time timeLimit) {
        this.timeLimit = timeLimit;
    }
}
