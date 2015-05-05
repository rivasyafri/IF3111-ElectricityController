/*
 * The MIT License
 *
 * Copyright 2015 RivaSyafri.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/*package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import view.Dashboard;
import view.ConnectionSetting;


public class FrameController {
    private Dashboard dashboard;
    private ConnectionSetting connectionSetting;
    
    public FrameController() {
        dashboard = new Dashboard(this);
    }
    
    public void showConnectionSetting() {
        connectionSetting.setVisible(true);
    }
    
    public void showDashboard() {
        connectionSetting.setVisible(false);
        dashboard.setVisible(true);
        Timer timer = new Timer(1000, new ActionListener() {
            private boolean b;

            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard.updateChart();
                b = !b;
            }
        });
        timer.start();
    }
    
    public Dashboard getDashboard() {
        return dashboard;
    }
    
    public ConnectionSetting getConnectionSetting() {
        return connectionSetting;
    }
    
    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
    
    public void setConnectionSetting(ConnectionSetting connectionSetting) {
        this.connectionSetting = connectionSetting;
    }
    
    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
        /* Set the Windows look and feel */
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
            java.util.logging.Logger.getLogger(ConnectionSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        FrameController fc = new FrameController();
    }*/
/*}*/
