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
    
    private Time timeLimit;
    
    private Double energyLimit;
    
    private double totalPower;
    
    private int sizeNumber;
    
    private ArrayBlockingQueue<Double> listPowerData;
    
    private XYSeriesCollection dataset;
    
    /**
     * Create new instance of Controller
     * @param COM port name
     */
    public Controller(String COM) {
        System.out.println("Controller is instantiated");
        this.sizeNumber = 60;
        con = new Connector(COM);
        energyLimit = Double.POSITIVE_INFINITY;
        timeLimit = null;
        totalPower = 0;
        listPowerData = new ArrayBlockingQueue(sizeNumber);
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
    }
    
    /**
     * Create 3D Line Chart
     * @return return chart
     */
    public JFreeChart generateChart() {
        JFreeChart lineChartObject = ChartFactory.createXYLineChart(
         null,"Time",
         "Power",
         generateDataset(),PlotOrientation.HORIZONTAL,
         true,true,false);
        return lineChartObject;
    }
    
    /**
     * Change the connection
     * @param COM port name
     */
    public void changeConnection(String COM) {
        con.setPortName(COM);
        con.open();
    }
    
    /**
     * Switch the status of current
     */
    public void switchCurrentStatus() {
        try {
            currentStatus = !currentStatus;
            Integer current = 1;
            if (currentStatus) {
                current = 1;
            } else {
                current = 0;
            }
            con.pushData(current.toString());
        } catch (SerialPortException ex) {
            currentStatus = !currentStatus;
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Switch the status of buzzer
     */
    public void switchBuzzerStatus() {
        try {
            buzzerStatus = !buzzerStatus;
            Integer buzzer = 0;
            if (buzzerStatus) {
                buzzer = 0;
            } else {
                buzzer = 1;
            }
            buzzer += 2;
            con.pushData(buzzer.toString());
        } catch (SerialPortException ex) {
            buzzerStatus = !buzzerStatus;
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Get the power limit that has been set
     * @return power limit
     */
    public double getEnergyLimit() {
        return energyLimit;
    }
    
    /**
     * Set the power limit 
     * @param energyLimit the new power limit
     */
    public void setPowerConstrain(double energyLimit) {
        this.energyLimit = energyLimit;
    }
    
    /**
     * Get the time limit
     * @return time limit
     */
    public Time getTimeConstrain() {
        return timeLimit;
    }
    
    /**
     * Set the time limit
     * @param timeLimit new time limit
     */
    public void setTimeConstrain(Time timeLimit) {
        this.timeLimit = timeLimit;
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
     * Generate dataset for Line Chart
     * @return dataset
     */
    private XYSeriesCollection generateDataset() {
        readData();
        XYSeries series = new XYSeries("power");
        int time = 0;
        for (Double datum : listPowerData) {
            series.add(time, datum);
            time++;
        }
        System.out.println(Arrays.deepToString(listPowerData.toArray()));
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
        try {
            if (totalPower/3600 >= energyLimit) {
                con.pushData("0");
            } else if ((totalPower/3600) == (energyLimit*0.9)) {
                con.pushData("2");
            }
        } catch (SerialPortException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
