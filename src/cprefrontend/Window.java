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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Mike
 */
public class Window extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Variable Declaration">
    // GUI
    private JPanel actionLogPanel;
    private JScrollPane activityLogScrollPane;
    private ActivityLog log;
    private JPanel bottomLeftSpacePanel;
    private JPanel bottomRightSpacePanel;
    private JButton bottomRotateBtn;
    private JPanel bottomRotatePanel;
    private JPanel connectBtnPanel;
    private JLabel calibrationLabel;
    private JPanel calibrationLabelPanel;
    private JPanel calibrationPanel;
    private JPanel centerOrientationPanel;
    private JButton connectBtn;
    private JPanel contentPanel;
    private JPanel controlsPanel;
    private JMenu editMenu;
    private JMenuItem emProfileDirectoryMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private JButton graphicsClearBtn;
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
    private JPanel mapContainer;
    private JLabel mapLabel;
    private JPanel mapLabelContainer;
    private Map mapPanel;
    private JMenuBar menuBar;
    private JButton moveBtn;
    private JLabel moveLabel;
    private JPanel movePanel;
    private JTextField moveTextField;
    private JButton musicBtn;
    private JButton rightRotateBtn;
    private JPanel rightRotatePanel;
    private JMenuItem rmRestartMenuItem;
//    private JMenuItem rmRunMenuItem;
    private JPanel robotOrientationPanel;
    private JPanel rotateControlPanel;
    private JMenu runMenu;
    private JPanel scanConnectPanel;
    private JPanel topLeftSpacePanel;
    private JPanel topRightSpacePanel;
    private JButton topRotateBtn;
    private JPanel topRotatePanel;
    private JButton scanBtn;
    private JFrame frame;

    private JButton bottomRightRotateBtn;
    private JButton bottomLeftRotateBtn;
    private JButton topRightRotateBtn;
    private JButton topLeftRotateBtn;
    
    private ImageIcon northWestIcon = new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-north-west.png"));
    private ImageIcon northIcon = new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-north.png"));
    private ImageIcon northEastIcon = new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-north-east.png"));
    private ImageIcon eastIcon = new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-east.png"));
    private ImageIcon southIcon = new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-south.png"));
    private ImageIcon southEastIcon = new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-south-east.png"));
    private ImageIcon southWestIcon = new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-south-west.png"));
    private ImageIcon westIcon = new ImageIcon(getClass().getResource("/cprefrontend/robot-orientation-west.png"));
    // End GUI

    // Logic
//    private final static String IP = "192.168.1.1";
//    private final static int PORT = 288;
    private NetUtils comms;
    private RobotInterpreter interpreter;

    private int currentDirection;

    /**
     * CalibrationProfile object which contains user-specified calibration information for the robot
     */
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
        mapContainer = new JPanel();
        mapLabel = new JLabel();
        mapLabelContainer = new JPanel();
        mapPanel = new Map();
        controlsPanel = new JPanel();
        leftControlPanel = new JPanel();
        movePanel = new JPanel();
        moveLabel = new JLabel();
        moveTextField = new JTextField();
        moveBtn = new JButton();
        calibrationPanel = new JPanel();
        calibrationLabel = new JLabel();
        calibrationLabelPanel = new JPanel();
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
        log = new ActivityLog();
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
//        rmRunMenuItem = new JMenuItem();
        rmRestartMenuItem = new JMenuItem();
        connectBtnPanel = new JPanel();
        connectBtn = new JButton();
        scanBtn = new JButton();
        graphicsClearBtn = new JButton();
        frame = this;
        musicBtn = new JButton();

        bottomRightRotateBtn = new JButton();
        bottomLeftRotateBtn = new JButton();
        topRightRotateBtn = new JButton();
        topLeftRotateBtn = new JButton();

        comms = new NetUtils(this, log); // start socket thread

        currentDirection = 0;
        calibrationProfile = new CalibrationProfile();
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
        setMinimumSize(new Dimension(1000, 783));
        setName("outsideFrame"); // NOI18N
        setResizable(true);

        java.awt.GridBagLayout contentPanelLayout = new java.awt.GridBagLayout();
        contentPanelLayout.rowHeights = new int[]{75, 10, 250, 10};
        contentPanelLayout.columnWeights = new double[]{1.0};
        contentPanelLayout.rowWeights = new double[]{1.0};
        contentPanel.setLayout(contentPanelLayout);

        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[]{1.0};
        mapContainer.setLayout(jPanel2Layout);

        mapLabel.setFont(new Font("Tahoma", 1, 13)); // NOI18N
        mapLabel.setText("Playing Field");
        mapLabelContainer.add(mapLabel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        mapContainer.add(mapLabelContainer, gridBagConstraints);

        mapPanel.setBackground(new Color(255, 255, 255));
        mapPanel.setBorder(BorderFactory.createEtchedBorder());

        GroupLayout mapPanelLayout = new GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
                mapPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        mapPanelLayout.setVerticalGroup(
                mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 433, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 788;
        gridBagConstraints.ipady = 404;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        mapContainer.add(mapPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        contentPanel.add(mapContainer, gridBagConstraints);

        interpreter = new RobotInterpreter(mapPanel, log);

        controlsPanel.setLayout(new GridLayout(1, 3));

        movePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        moveLabel.setText("Linear Move (cm):");
        movePanel.add(moveLabel);

        moveTextField.setText("0");
        moveTextField.setMinimumSize(new Dimension(35, 22));
        moveTextField.setName(""); // NOI18N
        moveTextField.setPreferredSize(new Dimension(35, 22));
        movePanel.add(moveTextField);

        moveBtn.setText("Go");
        moveBtn.addActionListener(this::moveBtnActionPerformed);
        moveBtn.setEnabled(hasProfile);
        movePanel.add(moveBtn);

        calibrationPanel.setLayout(new GridLayout(2, 0));

        calibrationLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        String profileName = calibrationProfile.getName();
        if (profileName == null) {
            profileName = "No Profile Selected";
            calibrationLabel.setForeground(Color.red);
        } else {
            calibrationLabel.setForeground(Color.green);
        }
        calibrationLabel.setText("Calibration Profile: " + profileName);
        calibrationLabelPanel.add(calibrationLabel);
        calibrationPanel.add(calibrationLabelPanel);

        connectBtnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        connectBtn.setText("Connect/Disconnect");
        connectBtn.addActionListener(this::connectBtnActionPerformed);
        connectBtnPanel.add(connectBtn);
        calibrationPanel.add(connectBtnPanel);

        scanConnectPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        scanBtn.setText("Scan");
        scanBtn.setEnabled(false);
        scanBtn.addActionListener(this::scanBtnActionPerformed);
        scanConnectPanel.add(scanBtn);
        
        graphicsClearBtn.setText("Reset map");
        graphicsClearBtn.setEnabled(false);
        graphicsClearBtn.addActionListener(this::graphicsClearActionPerformed);
        scanConnectPanel.add(graphicsClearBtn);

        musicBtn.setText("Play music");
        musicBtn.setEnabled(false);
        musicBtn.addActionListener(this::musicBtnActionPerformed);
        scanConnectPanel.add(musicBtn);

        GroupLayout leftControlPanelLayout = new GroupLayout(leftControlPanel);
        leftControlPanel.setLayout(leftControlPanelLayout);
        leftControlPanelLayout.setHorizontalGroup(
                leftControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(leftControlPanelLayout.createSequentialGroup()
                                .addGroup(leftControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(movePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(calibrationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scanConnectPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        leftControlPanelLayout.setVerticalGroup(
                leftControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(leftControlPanelLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(movePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(calibrationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scanConnectPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        controlsPanel.add(leftControlPanel);

        rotateControlPanel.setLayout(new GridBagLayout());

        ImageIcon icon = new ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-left-top.png"));
        topLeftRotateBtn.setIcon(icon);
        topLeftRotateBtn.addActionListener(this::topLeftRotateBtnActionPerformed);
        topLeftRotateBtn.setEnabled(false);

        GroupLayout topLeftSpacePanelLayout = new GroupLayout(topLeftSpacePanel);
        topLeftSpacePanel.setLayout(topLeftSpacePanelLayout);
        topLeftSpacePanelLayout.setHorizontalGroup(
                topLeftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(topLeftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(topLeftSpacePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(topLeftRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        topLeftSpacePanelLayout.setVerticalGroup(
                topLeftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(topLeftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(topLeftSpacePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(topLeftRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );

        rotateControlPanel.add(topLeftSpacePanel, new GridBagConstraints());

        topRotateBtn.setIcon(new ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-up.png"))); // NOI18N
        topRotateBtn.addActionListener(this::topRotateBtnActionPerformed);
        topRotateBtn.setEnabled(false);

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

        topRightRotateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-right-top.png"))); // NOI18N
        topRightRotateBtn.addActionListener(this::topRightRotateBtnActionPerformed);
        topRightRotateBtn.setEnabled(false);

        GroupLayout topRightSpacePanelLayout = new GroupLayout(topRightSpacePanel);
        topRightSpacePanel.setLayout(topRightSpacePanelLayout);
        topRightSpacePanelLayout.setHorizontalGroup(
                topRightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(topRightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(topRightSpacePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(topRightRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        topRightSpacePanelLayout.setVerticalGroup(
                topRightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(topRightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(topRightSpacePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(topRightRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
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

        bottomLeftRotateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-bottom-left.png"))); // NOI18N
        bottomLeftRotateBtn.addActionListener(this::bottomLeftRotateBtnActionPerformed);
        bottomLeftRotateBtn.setEnabled(false);

        javax.swing.GroupLayout bottomLeftSpacePanelLayout = new javax.swing.GroupLayout(bottomLeftSpacePanel);
        bottomLeftSpacePanel.setLayout(bottomLeftSpacePanelLayout);
        bottomLeftSpacePanelLayout.setHorizontalGroup(
                bottomLeftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(bottomLeftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(bottomLeftSpacePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(bottomLeftRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        bottomLeftSpacePanelLayout.setVerticalGroup(
                bottomLeftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(bottomLeftSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(bottomLeftSpacePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(bottomLeftRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
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

        bottomRightRotateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cprefrontend/direction-arrow-right-bottom.png")));
        bottomRightRotateBtn.addActionListener(this::bottomRightRotateBtnActionPerformed);
        bottomRightRotateBtn.setEnabled(false);

        javax.swing.GroupLayout bottomRightSpacePanelLayout = new javax.swing.GroupLayout(bottomRightSpacePanel);
        bottomRightSpacePanel.setLayout(bottomRightSpacePanelLayout);
        bottomRightSpacePanelLayout.setHorizontalGroup(
                bottomRightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(bottomRightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(bottomRightSpacePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(bottomRightRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        bottomRightSpacePanelLayout.setVerticalGroup(
                bottomRightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(bottomRightSpacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(bottomRightSpacePanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(bottomRightRotateBtn)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        rotateControlPanel.add(bottomRightSpacePanel, gridBagConstraints);

        controlsPanel.add(rotateControlPanel);

        logLabel.setFont(new Font("Tahoma", 1, 13)); // NOI18N
        logLabel.setText("Robot Activity Log");
        logLabelPanel.add(logLabel);

        log.setEditable(false);
        DefaultCaret caret = (DefaultCaret) log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        activityLogScrollPane.setViewportView(log);

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
                actionLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(logTextFieldPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logLabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
        );

        actionLogPanelLayout.setVerticalGroup(
                actionLogPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(actionLogPanelLayout.createSequentialGroup()
                                .addComponent(logLabelPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logTextFieldPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        controlsPanel.add(actionLogPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        contentPanel.add(controlsPanel, gridBagConstraints);

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

//        rmRunMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
//        rmRunMenuItem.setText("Start");
//        rmRunMenuItem.addActionListener(this::rmRunMenuItemActionPerformed);
//        runMenu.add(rmRunMenuItem);
        rmRestartMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        rmRestartMenuItem.setText("Restart");
        rmRestartMenuItem.addActionListener(this::rmRestartMenuItemActionPerformed);
        runMenu.add(rmRestartMenuItem);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
        );

        pack();

        // init robot position to center of map
        mapPanel.moveRobot(mapPanel.getWidth() / 2, mapPanel.getHeight() / 2);
        mapPanel.repaint();
//        System.out.println(this.getHeight() + ", " + this.getWidth());
    }
// </editor-fold>                        

    // <editor-fold defaultstate="collapsed" desc="Action Listeners">
//    private void moveTextFieldActionPerformed(ActionEvent evt) {                                              
//        // TODO add your handling code here:
//    }
    private void moveBtnActionPerformed(ActionEvent evt) {
        musicBtn.setEnabled(false);
        graphicsClearBtn.setEnabled(false);
        scanBtn.setEnabled(false);
        topRotateBtn.setEnabled(false);
        rightRotateBtn.setEnabled(false);
        bottomRotateBtn.setEnabled(false);
        leftRotateBtn.setEnabled(false);
        topLeftRotateBtn.setEnabled(false);
        topRightRotateBtn.setEnabled(false);
        bottomLeftRotateBtn.setEnabled(false);
        bottomRightRotateBtn.setEnabled(false);
        try {
            int distance = Integer.parseInt(moveTextField.getText());
            interpreter.parseResponse("move," + distance);
            if (distance == 0) {
                throw new NumberFormatException("Distance was 0, try again.");
            }
            log.logPrintln("move" + distance + ";");
            comms.sendLine("move" + distance + ";");

            String line = comms.readLine(5000);
            log.logPrintln(line); // for dedugging purposes
            interpreter.parseResponse(line);
//            String line = "move,cliff,14";
//            interpreter.parseResponse(line);

        } catch (NumberFormatException e) {
            System.err.println("Distance must only contain non-zero numbers");
            JOptionPane.showMessageDialog(this, "Distance may only contain non-zero numbers", "NumberFormatExcpetion", JOptionPane.ERROR_MESSAGE);
        }
        scanBtn.setEnabled(true);
        graphicsClearBtn.setEnabled(true);
        musicBtn.setEnabled(true);
        moveBtn.setEnabled(true);
        topRotateBtn.setEnabled(true);
        rightRotateBtn.setEnabled(true);
        bottomRotateBtn.setEnabled(true);
        leftRotateBtn.setEnabled(true);
        topLeftRotateBtn.setEnabled(true);
        topRightRotateBtn.setEnabled(true);
        bottomLeftRotateBtn.setEnabled(true);
        bottomRightRotateBtn.setEnabled(true);
    }

    private void fmLoadCalibrationProfileMenuItemActionPerformed(ActionEvent evt) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Calibration Profiles", "profile");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            loadProfile(file);
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

            log.logPrintln("Updated profile" + "\"" + profileName + "\"");
            loadProfile(file);
        }
    }

    private void fmNewCalibrationProfileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        CalibrationDialog cDialog = new CalibrationDialog(this);
        cDialog.createDialog();
        calibrationProfile = cDialog.getCalibrationProfile();
        if (calibrationProfile.getName() == null) {
            log.logPrintErr("Invalid calibration profile");
            return;
        }
        log.logPrintln("Created calibration profile \"" + calibrationProfile.getName() + "\"");
        loadProfile(calibrationProfile);
    }

    private void exitMenuItemActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void emProfileDirectoryMenuItemActionPerformed(ActionEvent evt) {

    }

    private void rmRestartMenuItemActionPerformed(ActionEvent evt) {
        try {
            log.clear();

            comms.closeSocket(); // close connection
            log.logPrintln("Socket closed");

            calibrationLabel.setText("Calibration Profile: No Profile Selected");
            calibrationLabel.setForeground(Color.red);
            calibrationProfile.setOffset(0);
            calibrationProfile.setMultiplier(1);
            calibrationProfile.setName(null);
            log.logPrintln("Unloaded calibration data");
            
            robotOrientationImage.setIcon(northIcon);
            currentDirection = Robot.NORTH;
            Robot.setDirection(Robot.NORTH);

            scanBtn.setEnabled(false);
            graphicsClearBtn.setEnabled(false);
            moveBtn.setEnabled(false);
            topLeftRotateBtn.setEnabled(false);
            topRotateBtn.setEnabled(false);
            topRightRotateBtn.setEnabled(false);
            rightRotateBtn.setEnabled(false);
            bottomRightRotateBtn.setEnabled(false);
            bottomRotateBtn.setEnabled(false);
            bottomLeftRotateBtn.setEnabled(false);
            leftRotateBtn.setEnabled(false);
            musicBtn.setEnabled(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void connectBtnActionPerformed(ActionEvent evt) {
        if (comms.getSocket() != null && !comms.getSocket().isClosed()) {

            try {
                int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to disconnect?", "Disconnect", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    comms.closeSocket();
                    log.logPrintln("Socket closed");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            ConnectionDialog cd = new ConnectionDialog(this);
            cd.createDialog();
            String ip = cd.getIP();
            int port = cd.getPort();
            if (!ip.equals("")) {
//                try {
                comms.setIP(ip);
                comms.setPort(port);
                comms.createSocket();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
            }
        }
    }

    private void scanBtnActionPerformed(ActionEvent evt) throws NumberFormatException {
        log.logPrintln("scan;");
        musicBtn.setEnabled(false);
        graphicsClearBtn.setEnabled(false);
        moveBtn.setEnabled(false);
        topRotateBtn.setEnabled(false);
        rightRotateBtn.setEnabled(false);
        bottomRotateBtn.setEnabled(false);
        leftRotateBtn.setEnabled(false);
        topLeftRotateBtn.setEnabled(false);
        topRightRotateBtn.setEnabled(false);
        bottomLeftRotateBtn.setEnabled(false);
        bottomRightRotateBtn.setEnabled(false);
        comms.sendLine("scan;"); // send scan command
        String line;
        line = comms.readLine(5000); // argument is time in milliseconds | this line gets total number of obstacles
        log.logPrintln(line);
        System.out.println(line);
        int obstacles=0;
        try {
            obstacles = Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            try {
                obstacles = Integer.parseInt(comms.readLine(5000));
            } catch (NumberFormatException ex1) {
                System.err.println("you done seriously fucked up");
                ex1.printStackTrace();
            }
        }
        String[] lines = new String[obstacles];
        for (int i = 0; i < obstacles; i++) {
//            System.out.println("here");
            lines[i] = comms.readLine(5000);
            interpreter.parseResponse(lines[i]);
            log.logPrintln(lines[i]); // for debugging purposes
            System.out.println(lines[i]);
        }
        String line2 = "obstacle,22,90,12";
        interpreter.parseResponse(line2);
        line = "obstacle,20.7,141.0,12.3";
        interpreter.parseResponse(line);
        System.out.println(line2);
        
        moveBtn.setEnabled(true);
        graphicsClearBtn.setEnabled(true);
        musicBtn.setEnabled(true);
        moveBtn.setEnabled(true);
        topRotateBtn.setEnabled(true);
        rightRotateBtn.setEnabled(true);
        bottomRotateBtn.setEnabled(true);
        leftRotateBtn.setEnabled(true);
        topLeftRotateBtn.setEnabled(true);
        topRightRotateBtn.setEnabled(true);
        bottomLeftRotateBtn.setEnabled(true);
        bottomRightRotateBtn.setEnabled(true);
    }

    private void topLeftRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(northWestIcon);
        log.logPrintln("rotate" + getDirection(currentDirection, Robot.NORTH_WEST) + ";");
        comms.sendLine("rotate" + getDirection(currentDirection, Robot.NORTH_WEST) + ";");
        currentDirection = Robot.NORTH_WEST;
        Robot.setDirection(Robot.NORTH_WEST); // use a static method because I want DIRECTION to carry accross all Robot instances
    }

    private void topRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(northIcon);
        log.logPrintln("rotate" + getDirection(currentDirection, Robot.NORTH) + ";");
        comms.sendLine("rotate" + getDirection(currentDirection, Robot.NORTH) + ";");
        currentDirection = Robot.NORTH;
        Robot.setDirection(Robot.NORTH); // use a static method because I want DIRECTION to carry accross all Robot instances
    }

    private void topRightRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(northEastIcon);
        log.logPrintln("rotate" + getDirection(currentDirection, Robot.NORTH_EAST) + ";");
        comms.sendLine("rotate" + getDirection(currentDirection, Robot.NORTH_EAST) + ";");
        currentDirection = Robot.NORTH_EAST;
        Robot.setDirection(Robot.NORTH_EAST); // use a static method because I want DIRECTION to carry accross all Robot instances
    }

    private void rightRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(eastIcon);
        log.logPrintln("rotate" + getDirection(currentDirection, Robot.EAST) + ";");
        comms.sendLine("rotate" + getDirection(currentDirection, Robot.EAST) + ";");
        currentDirection = Robot.EAST;
        Robot.setDirection(Robot.EAST);
    }

    private void bottomRightRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(southEastIcon);
        log.logPrintln("rotate" + getDirection(currentDirection, Robot.SOUTH_EAST) + ";");
        comms.sendLine("rotate" + getDirection(currentDirection, Robot.SOUTH_EAST) + ";");
        currentDirection = Robot.SOUTH_EAST;
        Robot.setDirection(Robot.SOUTH_EAST); // use a static method because I want DIRECTION to carry accross all Robot instances
    }

    private void bottomRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(southIcon);
        log.logPrintln("rotate" + getDirection(currentDirection, Robot.SOUTH) + ";");
        comms.sendLine("rotate" + getDirection(currentDirection, Robot.SOUTH) + ";");
        currentDirection = Robot.SOUTH;
        Robot.setDirection(Robot.SOUTH);
    }

    private void bottomLeftRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(southWestIcon);
        log.logPrintln("rotate" + getDirection(currentDirection, Robot.SOUTH_WEST) + ";");
        comms.sendLine("rotate" + getDirection(currentDirection, Robot.SOUTH_WEST) + ";");
        currentDirection = Robot.SOUTH_WEST;
        Robot.setDirection(Robot.SOUTH_WEST);
    }

    private void leftRotateBtnActionPerformed(ActionEvent evt) {
        robotOrientationImage.setIcon(westIcon);
        log.logPrintln("rotate" + getDirection(currentDirection, Robot.WEST) + ";");
        comms.sendLine("rotate" + getDirection(currentDirection, Robot.WEST) + ";");
        currentDirection = Robot.WEST;
        Robot.setDirection(Robot.WEST);
    }

    private void musicBtnActionPerformed(ActionEvent evt) {
        log.logPrintln("play;");
        comms.sendLine("play;");
        // enjoy
    }
    
    private void graphicsClearActionPerformed(ActionEvent evt) {
        int n = JOptionPane.showOptionDialog(frame, 
                "Are you sure you want to reset the map?\nThis action cannot be undone.",
                "Reset map", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE,
                null,
                new Object[] {"Yes", "No"},
                JOptionPane.YES_OPTION
        );
        switch (n) {
            case JOptionPane.YES_OPTION:
                mapPanel.clear();
                break;
            default: break;
        }
    }
    // </editor-fold>

    private int getDirection(int cardinalDir0, int cardinalDir1) {
        int direction = (cardinalDir1 - cardinalDir0) * 45;

        if (direction < 0) {
            direction += 360;
        }

        return direction;
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

            if (comms.getSocket() != null && !comms.getSocket().isClosed()) {
                try {
                    calibrationProfile.setOffset(Integer.parseInt(profile[1]));
                    calibrationProfile.setMultiplier(Integer.parseInt(profile[2]));
                    loadProfile(calibrationProfile);
                    log.logPrintln("Loaded calibration profile \"" + calibrationProfile.getName() + "\"");
                } catch (NumberFormatException ex) {
                    System.err.println("Profile is invalid");
                    JOptionPane.showMessageDialog(this, "Profile is invalid", "NumberFormatExcpetion", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                log.logPrintErr("Could not connect to target.");
            }

        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
            JOptionPane.showMessageDialog(this, "\"" + file + "\" not found", "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Coud not open \"" + file + "\"", "IOException", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Sends calibration information created by the user to the robot
     * @param cpf calibration profile which contains calibration information
     * @param status calibration status used for testing values and actually loading a profile
     */
    protected void loadTestProfile(CalibrationProfile cpf, int status) {
        if (status != 0) {
            Integer offset = cpf.getOffset();
            Integer mult = cpf.getMultiplier();

            System.out.println((char) (status + 48) + ", " + offset + ", " + mult);

            char c = (char) (status + 48);
            comms.sendLine(c + ";");
            if (status == 1) {
                comms.sendLine(offset + ";");
            } else {
                comms.sendLine(mult + ";");
            }
        } else {
//            loadProfile(cpf);
        }
    }

    private void loadProfile(CalibrationProfile cpf) {
        char status = '0';
        int offset = calibrationProfile.getOffset();
        int mult = calibrationProfile.getMultiplier();

        comms.sendLine(status + ";");
        comms.sendLine(offset + ";");
        comms.sendLine(mult + ";");

        calibrationLabel.setText("Calibration Profile: " + cpf.getName());
        calibrationLabel.setForeground(Color.green);
        scanBtn.setEnabled(true);
        graphicsClearBtn.setEnabled(true);
        musicBtn.setEnabled(true);
        moveBtn.setEnabled(true);
        topRotateBtn.setEnabled(true);
        rightRotateBtn.setEnabled(true);
        bottomRotateBtn.setEnabled(true);
        leftRotateBtn.setEnabled(true);
        topLeftRotateBtn.setEnabled(true);
        topRightRotateBtn.setEnabled(true);
        bottomLeftRotateBtn.setEnabled(true);
        bottomRightRotateBtn.setEnabled(true);

    }
}
