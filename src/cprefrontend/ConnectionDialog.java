/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mike
 */
public class ConnectionDialog extends JDialog {

    private JPanel fieldsPanel;
    private JPanel buttonsPanel;
    private JButton okBtn;
    private JButton cancelBtn;
    private JTextField ipTextField;
    private JTextField portTextField;
    private JLabel ipLabel;
    private JLabel portLabel;

    private String ip;
    private int port;

    public ConnectionDialog(JFrame parent) {
        super(parent, "Edit Connection Information", true);
        fieldsPanel = new JPanel();
        buttonsPanel = new JPanel();
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        ipTextField = new JTextField(10);
        ipTextField.setText("192.168.1.1");
        portTextField = new JTextField(5);
        portTextField.setText("288");
        ipLabel = new JLabel("IP:Port");
        portLabel = new JLabel(":");

        ip = "";
        port = 0;

        if (parent != null) {
            Dimension parentSize = parent.getSize();
            Point p = parent.getLocation();
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
        }
    }

    public void createDialog() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        fieldsPanel.setLayout(new FlowLayout());

        fieldsPanel.add(ipLabel);
        fieldsPanel.add(ipTextField);
        fieldsPanel.add(portLabel);
        fieldsPanel.add(portTextField);
        getContentPane().add(fieldsPanel);

        okBtn.addActionListener(this::okBtnActionPerformed);
        buttonsPanel.add(okBtn);

        cancelBtn.addActionListener(this::cancelBtnActionPerformed);
        buttonsPanel.add(cancelBtn);

        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        setSize(350, 150);
        setVisible(true);
    }

    private void okBtnActionPerformed(ActionEvent evt) {
        try {
            ip = ipTextField.getText();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Fields must be entered correctly", "IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tmp = portTextField.getText();
        try {
            port = Integer.parseInt(portTextField.getText());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Fields must be entered correctly", "IllegalArgumentException", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {
        dispose();
    }

    protected String getIP() {
        return ip;
    }

    protected int getPort() {
        return port;
    }

}
