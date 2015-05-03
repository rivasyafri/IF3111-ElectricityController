/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static java.lang.Thread.sleep;
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
    
    private Integer timeLimit;
    
    private Double energyLimit;
    
    private double totalPower;
    
    private int timer;
    
    private final int sizeNumber = 60;
    
    private ArrayBlockingQueue<Double> listPowerData;
    
    private ArrayBlockingQueue<Double> listEnergyData;
    
    private XYSeriesCollection dataset;
    
    /**
     * Create new instance of Controller
     */
    public Controller() {
        System.out.println("Controller is instantiated");
        energyLimit = Double.POSITIVE_INFINITY;
        timeLimit = null;
        totalPower = 0;
        listPowerData = new ArrayBlockingQueue(sizeNumber);
        listEnergyData = new ArrayBlockingQueue(sizeNumber);
    }
    
    /**
     * Create new instance of Controller
     * @param COM port name
     */
    public Controller(String COM) {
        System.out.println("Controller is instantiated");
        energyLimit = Double.POSITIVE_INFINITY;
        timeLimit = null;
        totalPower = 0;
        listPowerData = new ArrayBlockingQueue(sizeNumber);
        listEnergyData = new ArrayBlockingQueue(sizeNumber);
        con = new Connector(COM);
        currentStatus = con.getStatus();
    }
    
    /**
     * Create new instance of Controller
     * @param con connector
     */
    public Controller(Connector con) {
        this.con = con;
        energyLimit = null;
        timeLimit = null;
        totalPower = (float) 0;
        listPowerData = new ArrayBlockingQueue(sizeNumber);
        listEnergyData = new ArrayBlockingQueue(sizeNumber);
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
        Integer current;
        if (currentStatus) {
            current = 0;
        } else {
            current = 1;
        }
        boolean success = true;
        try {
            con.pushData(current.toString());
            currentStatus = !currentStatus;
        } catch (SerialPortException ex) {
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
        Integer buzzer;
        if (buzzerStatus) {
            buzzer = 1;
        } else {
            buzzer = 0;
        }
        buzzer += 2;
        boolean success = true;
        try {
            con.pushData(buzzer.toString());
            buzzerStatus = !buzzerStatus;
        } catch (SerialPortException ex) {
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
    public int getTimeLimit() {
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
    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
        timeLimitter = true;
        timer = 0;
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
     * Create 3D Line Chart of Power
     * @return return chart
     */
    public JFreeChart generatePowerLineChart() {
        JFreeChart lineChartObject = ChartFactory.createXYLineChart(
         null,"TIME (s)",
         "POWER (W)",
         generatePowerDataset(),PlotOrientation.VERTICAL,
         true,true,false);
        return lineChartObject;
    }
    
    /**
     * Generate dataset of power for Line Chart
     * @return dataset
     */
    private XYSeriesCollection generatePowerDataset() {
        readData();
        //generateDummyData();
        XYSeries series = new XYSeries("power");
        int time = 0;
        /*for (int i = 0; i < 60; i++)
            series.add(i, Math.random());*/
        for (Double datum : listPowerData) {
            series.add(time, datum);
            time++;
        }
        dataset = new XYSeriesCollection(series);
        return dataset;
    }
    
    /**
     * Create 3D Line Chart of Power
     * @return return chart
     */
    public JFreeChart generateEnergyLineChart() {
        JFreeChart lineChartObject = ChartFactory.createXYLineChart(
         null,"TIME (s)",
         "ENERGY (Wh)",
         generateEnergyDataset(),PlotOrientation.VERTICAL,
         true,true,false);
        return lineChartObject;
    }
    
    /**
     * Generate dataset of power for Line Chart
     * @return dataset
     */
    private XYSeriesCollection generateEnergyDataset() {
        readData();
        //generateDummyData();
        XYSeries series = new XYSeries("power");
        int time = 0;
        /*for (int i = 0; i < 60; i++)
            series.add(i, Math.random());*/
        for (Double datum : listPowerData) {
            series.add(time, datum);
            time++;
        }
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
        totalPower += data;
        if (listEnergyData.size() == sizeNumber) {
            listEnergyData.remove();
        }
        listEnergyData.add(totalPower/3600);
        if (timeLimitter) {
            timer++;
        }
        try {
            checkEnergyLimit();
            checkTimeLimit();
        } catch (SerialPortException ex) {
            errorMessage = ex.getMessage();
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void checkEnergyLimit() throws SerialPortException {
        if (energyLimitter) {
            System.out.println(totalPower/3600 + " " + energyLimit);
            if (totalPower/3600 >= energyLimit) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(new JFrame(), "Limit has been reached", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                };
                t.start();
                energyLimitter = false;
                energyLimit = Double.POSITIVE_INFINITY;
                con.pushData("0");
            } else if ((totalPower/3600) >= (energyLimit*0.9)) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(new JFrame(), "90% to limit", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                };
                t.start();
                con.pushData("2");
            }
        }
    }
    
    private void checkTimeLimit() throws SerialPortException {
        if (timeLimitter) {
            System.out.println(timeLimit - timer);
            if (timeLimit - timer == 0) {
                JOptionPane.showMessageDialog(new JFrame(), "Limit has been reached", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                timeLimitter = false;
                timeLimit = null;
                con.pushData("0");
            } else if (timeLimit - timer == 10) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(new JFrame(), "10 seconds to limit", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                };
                t.start();
                con.pushData("2");
            }
        }
    }
    
    /**
     * Generate dummy data
     */
    private void generateDummyData() {
        double data = Math.random();
        if (listPowerData.size() == sizeNumber) {
            listPowerData.remove();
        }
        listPowerData.add(data);
    }
}
