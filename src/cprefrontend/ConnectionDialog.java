/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
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

    private final JPanel fieldsPanel;
    private final JPanel buttonsPanel;
    private final JButton okBtn;
    private final JButton cancelBtn;
    private final JTextField ipTextField;
    private final JTextField portTextField;
    private final JLabel ipLabel;
    private final JLabel portLabel;

    private String ip;
    private int port;

    /**
     * Constructor which calls super constructor and initializes fields
     * ipTextField is set to 192.168.1.1
     * portTextField is set to 288
     * 
     * ipTextField is the JTextField which accepts user input for IP address
     * portTextField is the JTextField which accepts user input for the port number
     * 
     * @param parent parent JFrame
     */
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

    /**
     * Creates new JDialog for entering connection information
     */
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
        ipTextField.setText(null);
        portTextField.setText("0");
        dispose();
    }

    /**
     * Returns IP address
     * @return ip
     */
    protected String getIP() {
        return ip;
    }

    /**
     * Returns port number
     * @return port
     */
    protected int getPort() {
        return port;
    }

}
