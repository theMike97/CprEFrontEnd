/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Mike
 */
public class Window extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Variable Declaration">
    // GUI
    private JPanel CalibrationPanel;
    private JPanel actionLogPanel;
    private JScrollPane activityLogScrollPane;
    private static JTextArea activityLogTextArea;
    private JPanel bottomLeftSpacePanel;
    private JPanel bottomRightSpacePanel;
    private JButton bottomRotateBtn;
    private JPanel bottomRotatePanel;
    private JLabel calibrationLabel;
    private JPanel centerOrientationPanel;
    private JButton connectBtn;
    private JPanel contentPanel;
    private JPanel controlsPanel;
    private JMenu editMenu;
    private JMenuItem emProfileDirectoryMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private JMenuItem fmLoadCalibrationProfileMenuItem;
    private JMenuItem fmNewCalibrationProfileMenuItem;
    private JLabel robotOrientationImage;
    private JLabel jLabel2;
    private JMenuItem editCalibrationProfileMenuItem;
    private JPopupMenu.Separator jSeparator1;
    private JPanel leftControlPanel;
    private JButton leftRotateBtn;
    private JPanel leftRotatePanel;
    private JLabel logLabel;
    private JPanel logLabelPanel;
    private JPanel logTextFieldPanel;
    private JLabel mapLabel;
    private Map mapPanel;
    private JMenuBar menuBar;
    private JButton moveBtn;
    private JLabel moveLabel;
    private JPanel movePanel;
    private JTextField moveTextField;
    private JButton rightRotateBtn;
    private JPanel rightRotatePanel;
    private JMenuItem rmRestartMenuItem;
    private JMenuItem rmRunMenuItem;
    private JPanel robotOrientationPanel;
    private JPanel rotateControlPanel;
    private JMenu runMenu;
    private JPanel scanConnectPanel;
    private JPanel topLeftSpacePanel;
    private JPanel topRightSpacePanel;
    private JButton topRotateBtn;
    private JPanel topRotatePanel;
    private JButton scanBtn;
    // End GUI
    
    // Logic

//    private final static String IP = "192.168.1.1";
//    private final static int PORT = 288;
    private NetUtils comms;
    private ActivityLogWriter writer;
    private RobotInterpreter interpreter;

    private int currentDirection;
    protected CalibrationProfile calibrationProfile;
    private boolean hasProfile;
    // End Logic
    // End of variables declaration
    // </editor-fold>

    /**
     * Creates new Window
     */
    public Window() {

        // <editor-fold defaultstate="collapsed" desc="Variable Initialization">
        contentPanel = new JPanel();
        mapLabel = new JLabel();
        mapPanel = new Map();
        controlsPanel = new JPanel();
        leftControlPanel = new JPanel();
        movePanel = new JPanel();
        moveLabel = new JLabel();
        moveTextField = new JTextField();
        moveBtn = new JButton();
        CalibrationPanel = new JPanel();
        calibrationLabel = new JLabel();
        jLabel2 = new JLabel();
        scanConnectPanel = new JPanel();
        rotateControlPanel = new JPanel();
        topLeftSpacePanel = new JPanel();
        topRotatePanel = new JPanel();
        topRotateBtn = new JButton();
        topRightSpacePanel = new JPanel();
        leftRotatePanel = new JPanel();
        leftRotateBtn = new JButton();
        centerOrientationPanel = new JPanel();
        robotOrientationPanel = new JPanel();
        robotOrientationImage = new JLabel();
        rightRotatePanel = new JPanel();
        rightRotateBtn = new JButton();
        bottomLeftSpacePanel = new JPanel();
        bottomRotatePanel = new JPanel();
        bottomRotateBtn = new JButton();
        bottomRightSpacePanel = new JPanel();
        actionLogPanel = new JPanel();
        logLabelPanel = new JPanel();
        logLabel = new JLabel();
        logTextFieldPanel = new JPanel();
        activityLogScrollPane = new JScrollPane();
        activityLogTextArea = new JTextArea();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        fmNewCalibrationProfileMenuItem = new JMenuItem();
        fmLoadCalibrationProfileMenuItem = new JMenuItem();
        editCalibrationProfileMenuItem = new JMenuItem();
        jSeparator1 = new JPopupMenu.Separator();
        exitMenuItem = new JMenuItem();
        editMenu = new JMenu();
        emProfileDirectoryMenuItem = new JMenuItem();
        runMenu = new JMenu();
        rmRunMenuItem = new JMenuItem();
        rmRestartMenuItem = new JMenuItem();
        connectBtn = new JButton();
        scanBtn = new JButton();

        comms = new NetUtils(this); // start socket thread
        writer = new ActivityLogWriter();

        currentDirection = 0;
        calibrationProfile = new CalibrationProfile();
        hasProfile = false;
        // </editor-fold>

        createWindow();
    }

    @SuppressWarnings("unchecked")
    private void createWindow() {

        // <editor-fold defaultstate="collapsed" desc="GUI Code">        
        GridBagConstraints gridBagConstraints;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                if (comms.isAlive() && comms.getSocket() != null) {
                    try {
                        comms.closeSocket();
                        comms.interrupt();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        setTitle("Team N2 iRobot Front End");
        setBounds(new Rectangle(0, 0, 0, 0));
        setMinimumSize(new Dimension(640, 480));
        setName("outsideFrame"); // NOI18N
        setResizable(false);

        mapLabel.setFont(new Font("Tahoma", 1, 13)); // NOI18N
        mapLabel.setText("Playing Field");

        mapPanel.setBackground(new Color(255, 255, 255));
        mapPanel.setBorder(BorderFactory.createEtchedBorder());

        GroupLayout mapPanelLayout = new GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
                mapPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        mapPanelLayout.setVerticalGroup(
                mapPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 404, Short.MAX_VALUE)
        );
        
        interpreter = new RobotInterpreter(mapPanel);

        controlsPanel.setLayout(new GridLayout(1, 3));

        movePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        moveLabel.setText("Linear Move (cm):");
        movePanel.add(moveLabel);

        moveTextField.setText("0");
        moveTextField.setMinimumSize(new Dimension(35, 22));
        moveTextField.setName(""); // NOI18N
        moveTextField.setPreferredSize(new Dimension(35, 22));
//        moveTextField.addActionListener(this::moveTextFieldActionPerformed);
        movePanel.add(moveTextField);

        moveBtn.setText("Go");
        moveBtn.addActionListener(this::moveBtnActionPerformed);
        moveBtn.setEnabled(hasProfile);
        movePanel.add(moveBtn);

        CalibrationPanel.setLayout(new GridLayout(2, 0));

        String profileName = calibrationProfile.getName();
        if (profileName == null) {
            profileName = "No Profile Selected";
            calibrationLabel.setForeground(Color.red);
        } else {
            calibrationLabel.setForeground(Color.green);
        }
        calibrationLabel.setText("Calibration Profile: " + profileName);
        CalibrationPanel.add(calibrationLabel);

        connectBtn.setText("Connect");
        connectBtn.addActionListener(this::connectBtnActionPerformed);
        CalibrationPanel.add(connectBtn);

        scanConnectPanel.setLayout(new FlowLayout());

        scanBtn.setText("Scan");
        scanBtn.addActionListener(this::scanBtnActionPerformed);
        scanConnectPanel.add(scanBtn);

        GroupLayout leftControlPanelLayout = new GroupLayout(leftControlPanel);
        leftControlPanel.setLayout(leftControlPanelLayout);
        leftControlPanelLayout.setHorizontalGroup(
                leftControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(leftControlPanelLayout.createSequentialGroup()
                                .addGroup(leftControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(movePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(CalibrationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scanConnectPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        leftControlPanelLayout.setVerticalGroup(
                leftControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(leftControlPanelLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(movePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CalibrationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scanConnectPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        controlsPanel.add(leftControlPanel);

        rotateControlPanel.setLayout(new GridBagLayout());

        GroupLayout topLeftSpacePanelLayout = new GroupLayout(topLeftSpacePanel);
        topLeftSpacePanel.setLayout(topLeftSpacePanelLayout);
        topLeftSpacePanelLayout.setHorizontalGroup(
                topLeftSpacePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        topLeftSpacePanelLayout.setVerticalGroup(
                topLeftSpacePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        rotateControlPanel.add(topLeftSpacePanel, new GridBagConstraints());

        topRotateBtn.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-up.png"))); // NOI18N
        topRotateBtn.addActionListener(this::topRotateBtnActionPerformed);
        topRotateBtn.setEnabled(hasProfile);

        GroupLayout topRotatePanelLayout = new GroupLayout(topRotatePanel);
        topRotatePanel.setLayout(topRotatePanelLayout);
        topRotatePanelLayout.setHorizontalGroup(
                topRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(topRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(topRotatePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(topRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        topRotatePanelLayout.setVerticalGroup(
                topRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(topRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(topRotatePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(topRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );

        rotateControlPanel.add(topRotatePanel, new GridBagConstraints());

        GroupLayout topRightSpacePanelLayout = new GroupLayout(topRightSpacePanel);
        topRightSpacePanel.setLayout(topRightSpacePanelLayout);
        topRightSpacePanelLayout.setHorizontalGroup(
                topRightSpacePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        topRightSpacePanelLayout.setVerticalGroup(
                topRightSpacePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        rotateControlPanel.add(topRightSpacePanel, new GridBagConstraints());

        leftRotateBtn.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-left.png"))); // NOI18N
        leftRotateBtn.addActionListener(this::leftRotateBtnActionPerformed);
        leftRotateBtn.setEnabled(hasProfile);

        GroupLayout leftRotatePanelLayout = new GroupLayout(leftRotatePanel);
        leftRotatePanel.setLayout(leftRotatePanelLayout);
        leftRotatePanelLayout.setHorizontalGroup(
                leftRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(leftRotatePanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(leftRotateBtn)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        leftRotatePanelLayout.setVerticalGroup(
                leftRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(leftRotatePanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(leftRotateBtn)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        rotateControlPanel.add(leftRotatePanel, gridBagConstraints);

        robotOrientationPanel.setMinimumSize(new Dimension(80, 80));

        robotOrientationImage.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-north.png"))); // NOI18N

        GroupLayout robotOrientationPanelLayout = new GroupLayout(robotOrientationPanel);
        robotOrientationPanel.setLayout(robotOrientationPanelLayout);
        robotOrientationPanelLayout.setHorizontalGroup(
                robotOrientationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(robotOrientationImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        robotOrientationPanelLayout.setVerticalGroup(
                robotOrientationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(robotOrientationImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        GroupLayout centerOrientationPanelLayout = new GroupLayout(centerOrientationPanel);
        centerOrientationPanel.setLayout(centerOrientationPanelLayout);
        centerOrientationPanelLayout.setHorizontalGroup(
                centerOrientationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, centerOrientationPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(robotOrientationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        centerOrientationPanelLayout.setVerticalGroup(
                centerOrientationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(centerOrientationPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(robotOrientationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        rotateControlPanel.add(centerOrientationPanel, gridBagConstraints);

        rightRotateBtn.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-right.png"))); // NOI18N
        rightRotateBtn.addActionListener(this::rightRotateBtnActionPerformed);
        rightRotateBtn.setEnabled(hasProfile);

        GroupLayout rightRotatePanelLayout = new GroupLayout(rightRotatePanel);
        rightRotatePanel.setLayout(rightRotatePanelLayout);
        rightRotatePanelLayout.setHorizontalGroup(
                rightRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(rightRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(rightRotatePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(rightRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        rightRotatePanelLayout.setVerticalGroup(
                rightRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(rightRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(rightRotatePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(rightRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        rotateControlPanel.add(rightRotatePanel, gridBagConstraints);

        GroupLayout bottomLeftSpacePanelLayout = new GroupLayout(bottomLeftSpacePanel);
        bottomLeftSpacePanel.setLayout(bottomLeftSpacePanelLayout);
        bottomLeftSpacePanelLayout.setHorizontalGroup(
                bottomLeftSpacePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        bottomLeftSpacePanelLayout.setVerticalGroup(
                bottomLeftSpacePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        rotateControlPanel.add(bottomLeftSpacePanel, gridBagConstraints);

        bottomRotateBtn.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-down.png"))); // NOI18N
        bottomRotateBtn.addActionListener(this::bottomRotateBtnActionPerformed);
        bottomRotateBtn.setEnabled(hasProfile);

        GroupLayout bottomRotatePanelLayout = new GroupLayout(bottomRotatePanel);
        bottomRotatePanel.setLayout(bottomRotatePanelLayout);
        bottomRotatePanelLayout.setHorizontalGroup(
                bottomRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(bottomRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(bottomRotatePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(bottomRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        bottomRotatePanelLayout.setVerticalGroup(
                bottomRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(bottomRotatePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(bottomRotatePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(bottomRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        rotateControlPanel.add(bottomRotatePanel, gridBagConstraints);

        GroupLayout bottomRightSpacePanelLayout = new GroupLayout(bottomRightSpacePanel);
        bottomRightSpacePanel.setLayout(bottomRightSpacePanelLayout);
        bottomRightSpacePanelLayout.setHorizontalGroup(
                bottomRightSpacePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        bottomRightSpacePanelLayout.setVerticalGroup(
                bottomRightSpacePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        rotateControlPanel.add(bottomRightSpacePanel, gridBagConstraints);

        controlsPanel.add(rotateControlPanel);

        logLabel.setFont(new Font("Tahoma", 1, 13)); // NOI18N
        logLabel.setText("Robot Activity Log");
        logLabelPanel.add(logLabel);

        activityLogTextArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret) activityLogTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        activityLogScrollPane.setViewportView(activityLogTextArea);

        GroupLayout logTextFieldPanelLayout = new GroupLayout(logTextFieldPanel);
        logTextFieldPanel.setLayout(logTextFieldPanelLayout);
        logTextFieldPanelLayout.setHorizontalGroup(
                logTextFieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, logTextFieldPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(activityLogScrollPane))
        );
        logTextFieldPanelLayout.setVerticalGroup(
                logTextFieldPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(activityLogScrollPane, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        );

        GroupLayout actionLogPanelLayout = new GroupLayout(actionLogPanel);
        actionLogPanel.setLayout(actionLogPanelLayout);
        actionLogPanelLayout.setHorizontalGroup(
                actionLogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(logTextFieldPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, actionLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logLabelPanel, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
        );
        actionLogPanelLayout.setVerticalGroup(
                actionLogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(actionLogPanelLayout.createSequentialGroup()
                                .addComponent(logLabelPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logTextFieldPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        controlsPanel.add(actionLogPanel);

        GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
                contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(controlsPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(mapPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addGap(383, 383, 383)
                                .addComponent(mapLabel)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(
                contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mapLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mapPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(controlsPanel, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                .addContainerGap())
        );

        fileMenu.setText("File");

        fmNewCalibrationProfileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        fmNewCalibrationProfileMenuItem.setText("New Calibration Profile");
        fmNewCalibrationProfileMenuItem.addActionListener(this::fmNewCalibrationProfileMenuItemActionPerformed);
        fileMenu.add(fmNewCalibrationProfileMenuItem);

        fmLoadCalibrationProfileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        fmLoadCalibrationProfileMenuItem.setText("Load Calibration Profile");
        fmLoadCalibrationProfileMenuItem.addActionListener(this::fmLoadCalibrationProfileMenuItemActionPerformed);
        fileMenu.add(fmLoadCalibrationProfileMenuItem);

        editCalibrationProfileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        editCalibrationProfileMenuItem.setText("Edit Calibration Profile");
        editCalibrationProfileMenuItem.addActionListener(this::editCalibrationProfileMenuItemActionPerformed);
        fileMenu.add(editCalibrationProfileMenuItem);
        fileMenu.add(jSeparator1);

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(this::exitMenuItemActionPerformed);
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);

        emProfileDirectoryMenuItem.setText("Change Profile Directory");
        emProfileDirectoryMenuItem.addActionListener(this::emProfileDirectoryMenuItemActionPerformed);
        editMenu.add(emProfileDirectoryMenuItem);

        runMenu.setText("Run");
        menuBar.add(runMenu);

        rmRunMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
        rmRunMenuItem.setText("Start");
        rmRunMenuItem.addActionListener(this::rmRunMenuItemActionPerformed);
        runMenu.add(rmRunMenuItem);

        rmRestartMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        rmRestartMenuItem.setText("Restart");
        rmRestartMenuItem.addActionListener(this::rmRestartMenuItemActionPerformed);
        runMenu.add(rmRestartMenuItem);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        // init robot position to center of map
        mapPanel.moveRobot(mapPanel.getWidth() / 2, mapPanel.getHeight() / 2);
    }
// </editor-fold>                        

    // <editor-fold defaultstate="collapsed" desc="Action Listeners">
//    private void moveTextFieldActionPerformed(ActionEvent evt) {                                              
//        // TODO add your handling code here:
//    }
    private void moveBtnActionPerformed(ActionEvent evt) {
        try {
            int distance = Integer.parseInt(moveTextField.getText());
            activityLogTextArea.append("Sending command \"move(" + distance + ")\"\n");

            // TODO send message over WiFi to robot to move
        } catch (NumberFormatException e) {
            System.err.println("Distance must only contain numbers");
            JOptionPane.showMessageDialog(this, "Distance must only contain numbers", "NumberFormatExcpetion", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fmLoadCalibrationProfileMenuItemActionPerformed(ActionEvent evt) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Calibration Profiles", "profile");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            loadProfile(file);
            writer.logPrintln("Loaded calibration profile \"" + calibrationProfile.getName() + "\"");
        }
    }

    private void editCalibrationProfileMenuItemActionPerformed(ActionEvent evt) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Calibration Profiles", "profile");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String profileName;
            File file = fc.getSelectedFile();
            Pattern r = Pattern.compile("^([a-z,A-Z]:)?[\\\\,\\/](.+[\\\\,\\/])*(.+)\\.(.+)$");
            Matcher m = r.matcher(file.toString());

            profileName = (m.find()) ? m.group(3) : "";

            CalibrationDialog cDialog = new CalibrationDialog(this, profileName);
            cDialog.createDialog();
            calibrationProfile = cDialog.getCalibrationProfile();

            loadProfile(file);
            writer.logPrintln("Updated profile" + "\"" + profileName + "\"");
        }
    }

    private void fmNewCalibrationProfileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        CalibrationDialog cDialog = new CalibrationDialog(this);
        cDialog.createDialog();
        calibrationProfile = cDialog.getCalibrationProfile();
        loadProfile(calibrationProfile);
        writer.logPrintln("Created calibration profile \"" + calibrationProfile.getName() + "\"");
        writer.logPrintln("Loaded calibration profile \"" + calibrationProfile.getName() + "\"");
    }

    private void exitMenuItemActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void emProfileDirectoryMenuItemActionPerformed(ActionEvent evt) {

    }

    private void rmRunMenuItemActionPerformed(ActionEvent evt) {

    }

    private void rmRestartMenuItemActionPerformed(ActionEvent evt) {

    }

    private void connectBtnActionPerformed(ActionEvent evt) {
        ConnectionDialog cd = new ConnectionDialog(this);
        cd.createDialog();
        String ip = cd.getIP();
        int port = cd.getPort();
        if (!ip.equals("")) {
            comms.setIP(ip);
            comms.setPort(port);
            comms.start();
        }
    }

    private void scanBtnActionPerformed(ActionEvent evt) {
//        if (!comms.isAlive()) {
//            JOptionPane.showMessageDialog(this, "Not connected to iRobot", "NullPointerException", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
        String line;
        // try 10 times until give up
        line = comms.readLine(5000); // argument is time in milliseconds
        interpreter.parseResponse(line);
        writer.logPrintln(line);
    }

    private void topRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-north.png")));
        writer.logPrintln("Sending command \"rotate(" + getDirection(currentDirection, Robot.NORTH) + ")\"");
        currentDirection = Robot.NORTH;
    }

    private void rightRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-east.png")));
        writer.logPrintln("Sending command \"rotate(" + getDirection(currentDirection, Robot.EAST) + ")\"");
        currentDirection = Robot.EAST;
    }

    private void bottomRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-south.png")));
        writer.logPrintln("Sending command \"rotate(" + getDirection(currentDirection, Robot.SOUTH) + ")\"");
        currentDirection = Robot.SOUTH;
    }

    private void leftRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-west.png")));
        writer.logPrintln("Sending command \"rotate(" + getDirection(currentDirection, Robot.WEST) + ")\"");
        currentDirection = Robot.WEST;
    }
    // </editor-fold>

    private int getDirection(int cardinalDir0, int cardinalDir1) {
        return (cardinalDir1 - cardinalDir0) * 90;
    }

    private void loadProfile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(",");
                line = br.readLine();
            }
            String[] profile = sb.toString().split(",");
            calibrationProfile.setName(profile[0]);

            try {
                calibrationProfile.setBot(Integer.parseInt(profile[1]));
                loadProfile(calibrationProfile);
            } catch (NumberFormatException ex) {
                System.err.println("Profile is invalid");
                JOptionPane.showMessageDialog(this, "Profile is invalid", "NumberFormatExcpetion", JOptionPane.ERROR_MESSAGE);
            }

        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
            JOptionPane.showMessageDialog(this, "\"" + file + "\" not found", "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Coud not open \"" + file + "\"", "IOException", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void loadProfile(CalibrationProfile cpf) {
        calibrationLabel.setText("Calibration Profile: " + cpf.getName());
        calibrationLabel.setForeground(Color.green);
        hasProfile = true;
        moveBtn.setEnabled(hasProfile);
        topRotateBtn.setEnabled(hasProfile);
        rightRotateBtn.setEnabled(hasProfile);
        bottomRotateBtn.setEnabled(hasProfile);
        leftRotateBtn.setEnabled(hasProfile);
        // TODO send bot number to robot
    }

    public static JTextArea getActivityLog() {
        return activityLogTextArea;
    }
}
