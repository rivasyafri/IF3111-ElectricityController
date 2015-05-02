/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Time;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jssc.SerialPortException;
import model.Connector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Class to control connector and to process data
 * @author RivaSyafri
 */
public class Controller {
    
    private Connector con;
    
    private Boolean currentStatus = true;
    
    private Boolean buzzerStatus = false;
    
    private boolean energyLimitter = false;
    
    private boolean timeLimitter = false;
    
    private String errorMessage = "";
    
    private Time timeLimit;
    
    private Double energyLimit;
    
    private double totalPower;
    
    private int sizeNumber;
    
    private ArrayBlockingQueue<Double> listPowerData;
    
    private XYSeriesCollection dataset;
    
    /**
     * Create new instance of Controller
     */
    public Controller() {
        System.out.println("Controller is instantiated");
        this.sizeNumber = 60;
        energyLimit = Double.POSITIVE_INFINITY;
        timeLimit = null;
        totalPower = 0;
        listPowerData = new ArrayBlockingQueue(sizeNumber);
    }
    
    /**
     * Create new instance of Controller
     * @param COM port name
     */
    public Controller(String COM) {
        System.out.println("Controller is instantiated");
        this.sizeNumber = 60;
        energyLimit = Double.POSITIVE_INFINITY;
        timeLimit = null;
        totalPower = 0;
        listPowerData = new ArrayBlockingQueue(sizeNumber);
        con = new Connector(COM);
        currentStatus = con.getStatus();
    }
    
    /**
     * Create new instance of Controller
     * @param con connector
     */
    public Controller(Connector con) {
        this.sizeNumber = 60;
        this.con = con;
        energyLimit = null;
        timeLimit = null;
        totalPower = (float) 0;
        listPowerData = new ArrayBlockingQueue(sizeNumber);
        currentStatus = con.getStatus();
    }
    
    /**
     * Set the connector
     * @param COM port name
     * @return true if success
     */
    public void setConnection(String COM) {
        con = new Connector(COM);
        currentStatus = con.getStatus();
    }
    
    /**
     * Set the connector
     * @param con
     */
    public void setConnection(Connector con) {
        this.con = con;
        currentStatus = con.getStatus();
    }
    
    /**
     * Change the connection
     * @param COM port name
     * @return true if success
     */
    public void changeConnection(String COM) {
        con = new Connector(COM);
        currentStatus = con.getStatus();
    }
    
    /**
     * Change the connection
     * @param con New connector
     */
    public void changeConnection(Connector con) {
        this.con = con;
        currentStatus = con.getStatus();
    }
    
    /**
     * Switch the status of current
     * @return return value if success or not
     */
    public boolean switchCurrentStatus() {
        currentStatus = !currentStatus;
        Integer current = 1;
        if (currentStatus) {
            current = 1;
        } else {
            current = 0;
        }
        boolean success = true;
        try {
            con.pushData(current.toString());
        } catch (SerialPortException ex) {
            currentStatus = !currentStatus;
            errorMessage = ex.getMessage();
            success = false;
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    /**
     * Switch the status of buzzer
     * @return return value if success or not
     */
    public boolean switchBuzzerStatus() {
        buzzerStatus = !buzzerStatus;
        Integer buzzer;
        if (buzzerStatus) {
            buzzer = 0;
        } else {
            buzzer = 1;
        }
        buzzer += 2;
        boolean success = true;
        try {
            con.pushData(buzzer.toString());
        } catch (SerialPortException ex) {
            buzzerStatus = !buzzerStatus;
            errorMessage = ex.getMessage();
            success = false;
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    /**
     * Get connector in controller
     * @return connector
     */
    public Connector getConnector() {
        return con;
    }
    
    /**
     * Get the power limit that has been set
     * @return power limit
     */
    public double getEnergyLimit() {
        return energyLimit;
    }
    
    /**
     * Get the time limit
     * @return time limit
     */
    public Time getTimeConstrain() {
        return timeLimit;
    }
    
    /**
     * Get current status
     * @return current status
     */
    public boolean getCurrentStatus() {
        return currentStatus;
    }
    
    /**
     * Get buzzer status
     * @return buzzer status
     */
    public boolean getBuzzerStatus() {
        return buzzerStatus;
    }
    
    /**
     * Get error message from process
     * @return error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * Set the time limit
     * @param timeLimit new time limit
     */
    public void setTimeLimit(Time timeLimit) {
        this.timeLimit = timeLimit;
        timeLimitter = true;
    }
    
    /**
     * Set the power limit 
     * @param energyLimit the new power limit
     */
    public void setEnergyLimit(double energyLimit) {
        this.energyLimit = energyLimit;
        energyLimitter = true;
        System.out.println(energyLimit);
    }
    
    /**
     * Create 3D Line Chart
     * @return return chart
     */
    public JFreeChart generateChart() {
        JFreeChart lineChartObject = ChartFactory.createXYLineChart(
         null,"TIME",
         "POWER",
         generateDataset(),PlotOrientation.VERTICAL,
         true,true,false);
        return lineChartObject;
    }
    
    /**
     * Generate dataset for Line Chart
     * @return dataset
     */
    private XYSeriesCollection generateDataset() {
        readData();
        XYSeries series = new XYSeries("power");
        int time = 0;
        for (int i = 0; i < 60; i++)
            series.add(i, Math.random());
        /*for (Double datum : listPowerData) {
            series.add(time, datum);
            time++;
        }*/
        dataset = new XYSeriesCollection(series);
        return dataset;
    }
    
    /**
     * Read data from connector
     */
    private void readData() {
        byte[] rawdata = con.pullData();
        Double data;
        if (rawdata == null) {
            data = (double) 0;
        } else {
            data = Double.valueOf(new String(rawdata));
        }
        if (listPowerData.size() == sizeNumber) {
            listPowerData.remove();
        }
        listPowerData.add(data);
        if (energyLimitter) {
            totalPower += data;
        }    
        try {
            if (totalPower/3600 >= energyLimit) {
                con.pushData("0");
                JOptionPane.showMessageDialog(new JFrame(), "Limit has been reached", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                energyLimitter = false;
                totalPower = 0;
            } else if ((totalPower/3600) == (energyLimit*0.9)) {
                con.pushData("2");
                JOptionPane.showMessageDialog(new JFrame(), "90% to limit", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SerialPortException ex) {
            errorMessage = ex.getMessage();
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
