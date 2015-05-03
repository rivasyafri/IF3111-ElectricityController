/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import controller.FrameController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author Riva Syafri Rachmatullah
 */
public class Dashboard extends javax.swing.JFrame {

    private final Controller controller;
    
    private final FrameController fc;
    
    private boolean energyChartSelected = true;
    
    /**
     * Creates new form Dashboard
     * @param portName
     * @param fc
     */
    public Dashboard(String portName, FrameController fc) {
        this.fc = fc;
        controller = new Controller(portName, this);
        initComponents();
        if (!controller.getConnector().getStatus()) {
            switchButton.setEnabled(false);
            buzzerButton.setEnabled(false);
        }
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        changeSwitchTextButton();
        changeBuzzerImage();
    }

    public Controller getController() {
        return controller;
    }
    
    public javax.swing.JPanel getChartPanel() {
        return chartPanel;
    }
    
    public void updateChart() {
        chartPanel.removeAll();
        ChartPanel chart;
        if (energyChartSelected) {
            chart = new ChartPanel(controller.generateEnergyLineChart());
        } else {
            chart = new ChartPanel(controller.generatePowerLineChart());
        }
        chart.setDomainZoomable(true);
        chartPanel.add(chart, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ChartPanel chart = new ChartPanel(controller.generatePowerLineChart());
        chart.setDomainZoomable(true);
        chartPanel = new javax.swing.JPanel();
        controlPanel = new javax.swing.JPanel();
        switchButton = new javax.swing.JButton();
        timeLimitButton = new javax.swing.JButton();
        energyLimitButton = new javax.swing.JButton();
        buzzerButton = new javax.swing.JButton();
        timeLimit = new javax.swing.JSpinner();
        energyLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        secondsLabel = new javax.swing.JLabel();
        WhLabel = new javax.swing.JLabel();
        energyLimit = new javax.swing.JFormattedTextField();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        setPortButton = new javax.swing.JMenuItem();
        exitButton = new javax.swing.JMenuItem();
        actionMenu = new javax.swing.JMenu();
        switchChart = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard");
        setMinimumSize(new java.awt.Dimension(502, 400));
        setPreferredSize(new java.awt.Dimension(530, 400));
        setResizable(false);

        chartPanel.setPreferredSize(new java.awt.Dimension(380, 380));
        chartPanel.setRequestFocusEnabled(false);
        chartPanel.setLayout(new java.awt.BorderLayout());

        chartPanel.add(chart, BorderLayout.CENTER);

        controlPanel.setBackground(new java.awt.Color(0, 0, 0, 200));
        controlPanel.setPreferredSize(new java.awt.Dimension(140, 380));
        controlPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        switchButton.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        switchButton.setText("OFF");
        switchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchButtonActionPerformed(evt);
            }
        });
        controlPanel.add(switchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 50));

        timeLimitButton.setText("Set Timer");
        timeLimitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeLimitButtonActionPerformed(evt);
            }
        });
        controlPanel.add(timeLimitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 80, -1));

        energyLimitButton.setText("Set Limit");
        energyLimitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                energyLimitButtonActionPerformed(evt);
            }
        });
        controlPanel.add(energyLimitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 80, -1));

        buzzerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/off (60x38).jpg"))); // NOI18N
        buzzerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buzzerButtonActionPerformed(evt);
            }
        });
        controlPanel.add(buzzerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 60, 40));
        controlPanel.add(timeLimit, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 60, -1));

        energyLabel.setForeground(new java.awt.Color(255, 255, 255));
        energyLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        energyLabel.setText("Energy");
        controlPanel.add(energyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 40, 20));

        timeLabel.setForeground(new java.awt.Color(255, 255, 255));
        timeLabel.setText("Time");
        controlPanel.add(timeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        secondsLabel.setForeground(new java.awt.Color(255, 255, 255));
        secondsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        secondsLabel.setText("s");
        controlPanel.add(secondsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 20, 20));

        WhLabel.setForeground(new java.awt.Color(255, 255, 255));
        WhLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WhLabel.setText("Wh");
        controlPanel.add(WhLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 20, 20));

        energyLimit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        energyLimit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        energyLimit.setText("0");
        controlPanel.add(energyLimit, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 60, -1));

        fileMenu.setText("File");

        setPortButton.setText("Chenge Port Name");
        setPortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setPortButtonActionPerformed(evt);
            }
        });
        fileMenu.add(setPortButton);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        fileMenu.add(exitButton);

        menuBar.add(fileMenu);

        actionMenu.setText("Action");

        switchChart.setText("Change to Power Chart");
        switchChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchChartActionPerformed(evt);
            }
        });
        actionMenu.add(switchChart);

        menuBar.add(actionMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void switchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchButtonActionPerformed
        boolean success = controller.switchCurrentStatus();
        if (success) {
            changeSwitchTextButton();
        } else {
            JOptionPane.showMessageDialog(rootPane, controller.getErrorMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        changeSwitchTextButton();
    }//GEN-LAST:event_switchButtonActionPerformed

    private void timeLimitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeLimitButtonActionPerformed
        controller.setTimeLimit((Integer) timeLimit.getValue());
    }//GEN-LAST:event_timeLimitButtonActionPerformed

    private void energyLimitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_energyLimitButtonActionPerformed
        controller.setEnergyLimit(Double.parseDouble(energyLimit.getText()));
    }//GEN-LAST:event_energyLimitButtonActionPerformed

    private void buzzerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buzzerButtonActionPerformed
        boolean success = controller.switchBuzzerStatus();
        if (success) {
            changeBuzzerImage();
        } else {
            JOptionPane.showMessageDialog(rootPane, controller.getErrorMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buzzerButtonActionPerformed

    private void setPortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setPortButtonActionPerformed
        fc.showPortSetter();
    }//GEN-LAST:event_setPortButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void switchChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchChartActionPerformed
        if (energyChartSelected) {
            switchChart.setText("Change to Power Chart");
        } else {
            switchChart.setText("Change to Energy Chart");
        }
        energyChartSelected = !energyChartSelected;
    }//GEN-LAST:event_switchChartActionPerformed

    public void changeSwitchTextButton() {
        if (controller.getCurrentStatus()) {
            switchButton.setText("ON");
            switchButton.setForeground(Color.RED);
        } else {
            switchButton.setText("OFF");
            switchButton.setForeground(Color.BLACK);
        }
    }
    
    public void changeBuzzerImage() {
        if (controller.getBuzzerStatus()) {
           buzzerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/on (60x60).jpg"))); // NOI18N 
        } else {
            buzzerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/off (60x38).jpg"))); // NOI18N
        }
    }

    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        Dashboard main = new Dashboard();
        java.awt.EventQueue.invokeLater(() -> {
            main.setVisible(true);
        });
        /*
        Thread t = new Thread() {
            ChartPanel panel;
            
            @Override
            public void run() {
                while (true) {
                    main.getChartPanel().removeAll();
            
                    // Creating the Swing ChartPanel instead of DefaultChart
                    panel = new ChartPanel(data, title, DefaultChart.LINEAR_X_LINEAR_Y);
                    // Adding ChartRenderer as usual
                    panel.addChartRenderer(new LineChartRenderer(panel.getCoordSystem(), data), 1);
                    panel.setSize(400, 300);
                    main.getChartPanel().add(panel, BorderLayout.CENTER);

                    double datum = main.controller.readData();
                    System.out.println(datum);
                    main.data.insertValue(0, datum, time);
                    main.time++;
                    panel.addChartPanel.revalidate();
                    panel.addChartPanel.repaint();
                }
            }
        };
        t.start();*/
    /*}*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel WhLabel;
    private javax.swing.JMenu actionMenu;
    private javax.swing.JButton buzzerButton;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JLabel energyLabel;
    private javax.swing.JFormattedTextField energyLimit;
    private javax.swing.JButton energyLimitButton;
    private javax.swing.JMenuItem exitButton;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel secondsLabel;
    private javax.swing.JMenuItem setPortButton;
    private javax.swing.JButton switchButton;
    private javax.swing.JMenuItem switchChart;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JSpinner timeLimit;
    private javax.swing.JButton timeLimitButton;
    // End of variables declaration//GEN-END:variables


}