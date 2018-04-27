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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
public class CalibrationDialog extends JDialog {

    private Window window;
    
    private CalibrationProfile calibrationProfile;
    private JTextField centerOffsetTextField;
    private JTextField profileNameTextField;
    private JTextField multiplierTextField;

    private JButton tryOffsetBtn;
    private JButton tryMultBtn;

    private String profileName;

    /**
     * Constructor which calls the super constructor and initialized fields
     * @param parent parent JFrame
     */
    public CalibrationDialog(JFrame parent) {
        super(parent, "Create new calibration profile", true);
        window = (Window) parent;
        calibrationProfile = new CalibrationProfile();
        calibrationProfile.setMultiplier(1);
        calibrationProfile.setOffset(0);

        if (parent != null) {
            Dimension parentSize = parent.getSize();
            Point p = parent.getLocation();
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
        }
    }

    /**
     * Constructor which sets profile name
     * @param parent parent JFrame
     * @param profileName name of profile
     */
    public CalibrationDialog(JFrame parent, String profileName) {
        this(parent);
        this.profileName = profileName;
    }

    /**
     * Creates JDialog for entering calibration information
     */
    public void createDialog() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel profileNameLabel = new JLabel("Profile Name:");
//        profileNameLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        JLabel centerOffsetLabel = new JLabel("Center offset:");
//        centerOffsetLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        JLabel multiplierLabel = new JLabel("Multiplier:");

        profileNameTextField = new JTextField(profileName, 10);

        centerOffsetTextField = new JTextField("0", 10);
        tryOffsetBtn = new JButton("Go");
        tryOffsetBtn.addActionListener(this::tryOffsetBtnActionPerformed);

        multiplierTextField = new JTextField("1", 10);
        tryMultBtn = new JButton("Go");
        tryMultBtn.addActionListener(this::tryMultBtnActionPerformed);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fieldsPanel.add(profileNameLabel);
        fieldsPanel.add(profileNameTextField);
        fieldsPanel.add(centerOffsetLabel);
        fieldsPanel.add(centerOffsetTextField);
        fieldsPanel.add(tryOffsetBtn);
        fieldsPanel.add(multiplierLabel);
        fieldsPanel.add(multiplierTextField);
        fieldsPanel.add(tryMultBtn);

        getContentPane().add(fieldsPanel);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this::cancelBtnActionPerformed);

        JButton doneBtn = new JButton("Save");
        doneBtn.addActionListener(this::doneBtnActionPerformed);

        JPanel btnsPanel = new JPanel();
        btnsPanel.add(doneBtn);
        btnsPanel.add(cancelBtn);
        getContentPane().add(btnsPanel, BorderLayout.SOUTH);

        setSize(300, 300);
//        pack();
        setVisible(true);
    }
    
    private void cancelBtnActionPerformed(ActionEvent evt) {
        dispose();
    }

    private void doneBtnActionPerformed(ActionEvent evt) {
        String profileName = profileNameTextField.getText();
        if (profileName.equals("")) {
            System.err.println("No profile name specified");
            JOptionPane.showMessageDialog(this, "No profile name specified", "Incomplete Fields", JOptionPane.ERROR_MESSAGE);
            return;
        }
        calibrationProfile.setName(profileName);
        try {
            calibrationProfile.setOffset(Integer.parseInt(centerOffsetTextField.getText()));
            calibrationProfile.setMultiplier(Integer.parseInt(multiplierTextField.getText()));
        } catch (NumberFormatException ex) {
            System.err.println("Fields must be numbers");
            JOptionPane.showMessageDialog(this, "Fields must be numbers", "NumberFormatExcpetion", JOptionPane.ERROR_MESSAGE);
        }

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(profileName + ".profile"), "utf-8"))) {
            writer.println(profileName);
            writer.println("" + calibrationProfile.getOffset());
            writer.println("" + calibrationProfile.getMultiplier());

        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        dispose();
    }

    private void tryOffsetBtnActionPerformed(ActionEvent evt) {
        try {
            calibrationProfile.setOffset(Integer.parseInt(centerOffsetTextField.getText()));
            window.loadTestProfile(calibrationProfile, 1);
        } catch (NumberFormatException ex) {
            System.err.println("Fields must be numbers");
            JOptionPane.showMessageDialog(this, "Fields must be numbers", "NumberFormatExcpetion", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tryMultBtnActionPerformed(ActionEvent evt) {
        try {
            calibrationProfile.setMultiplier(Integer.parseInt(multiplierTextField.getText()));
            window.loadTestProfile(calibrationProfile, 2);
        } catch (NumberFormatException ex) {
            System.err.println("Fields must be numbers");
            JOptionPane.showMessageDialog(this, "Fields must be numbers", "NumberFormatExcpetion", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returns CalibrationProfile object containing calibration information for the robot
     * @return
     */
    public CalibrationProfile getCalibrationProfile() {
        return calibrationProfile;
    }

}
