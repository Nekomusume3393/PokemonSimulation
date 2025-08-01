/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pokemon.sim.util.XDialog;

/**
 *
 * @author May5th
 */
public class TestXDialog extends javax.swing.JFrame {

    private static final Logger logger = LoggerFactory.getLogger(TestXDialog.class);

    public TestXDialog() {
        initComponents();
        initActions();
    }

    private void initActions() {
        btnAlert.addActionListener(e -> XDialog.alert(this, "This is a normal alert.", ""));
        btnSuccess.addActionListener(e -> XDialog.success(this, "Operation succeed!", ""));
        btnError.addActionListener(e -> XDialog.error(this, "An error occurred!", ""));
        btnConfirm.addActionListener(e -> {
            boolean ok = XDialog.confirm(this, "Are you sure?", "");
            logger.info("Confirm: {}", ok);
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnAlert = new javax.swing.JButton();
        btnSuccess = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        btnError = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAlert.setText("Alert");
        btnSuccess.setText("Success");
        btnConfirm.setText("Confirm");
        btnError.setText("Error");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAlert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuccess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnAlert)
                .addGap(18, 18, 18)
                .addComponent(btnSuccess)
                .addGap(18, 18, 18)
                .addComponent(btnError)
                .addGap(18, 18, 18)
                .addComponent(btnConfirm)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.error("This look and feel is unsupported: ", ex);
        }

        java.awt.EventQueue.invokeLater(() -> new TestXDialog().setVisible(true));
    }

    private javax.swing.JButton btnAlert;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnError;
    private javax.swing.JButton btnSuccess;
}
