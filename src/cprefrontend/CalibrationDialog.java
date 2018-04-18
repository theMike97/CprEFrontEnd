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
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

    private CalibrationProfile calibrationProfile;
    private JTextField botNumberTextField;
    private JTextField profileNameTextField;
    
    private String profileName;

    public CalibrationDialog(JFrame parent) {
        super(parent, "Create new calibration profile", true);
        calibrationProfile = new CalibrationProfile();

        if (parent != null) {
            Dimension parentSize = parent.getSize();
            Point p = parent.getLocation();
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
        }
    }
    
    public CalibrationDialog(JFrame parent, String profileName) {
        this(parent);
        this.profileName = profileName;
    }

    public void createDialog() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel profileNameLabel = new JLabel("Profile Name:");

        JLabel botNumberLabel = new JLabel("Robot Number:");

        profileNameTextField = new JTextField(profileName, 10);

        botNumberTextField = new JTextField(10);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new FlowLayout());
        fieldsPanel.add(profileNameLabel);
        fieldsPanel.add(profileNameTextField);
        fieldsPanel.add(botNumberLabel);
        fieldsPanel.add(botNumberTextField);
        getContentPane().add(fieldsPanel);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this::cancelBtnActionPerformed);

        JButton doneBtn = new JButton("Save");
        doneBtn.addActionListener(this::doneBtnActionPerformed);

        JPanel btnsPanel = new JPanel();
        btnsPanel.add(doneBtn);
        btnsPanel.add(cancelBtn);
        getContentPane().add(btnsPanel, BorderLayout.SOUTH);

        setSize(300, 150);
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
            calibrationProfile.setBot(Integer.parseInt(botNumberTextField.getText()));
        } catch (NumberFormatException ex) {
            System.err.println("Bot must be a number");
            JOptionPane.showMessageDialog(this, "Bot must be a number", "NumberFormatExcpetion", JOptionPane.ERROR_MESSAGE);
        }


            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(profileName + ".profile"), "utf-8"))) {
                writer.println(profileName);
                writer.println("" + calibrationProfile.getBot());

            } catch (FileNotFoundException ex) {

            } catch (UnsupportedEncodingException ex) {

            } catch (IOException ex) {
                
            }
            dispose();
        }

    

    public CalibrationProfile getCalibrationProfile() {
        return calibrationProfile;
    }

}
