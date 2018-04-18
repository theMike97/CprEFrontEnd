/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author Mike
 */
public class NetUtils extends Thread {

    private String ip;
    private int port;
    private JFrame parent;
    private boolean isConnected;
    
    public NetUtils(JFrame parent) {
        this.parent = parent;
        isConnected = false;
        ip = null;
        port = 0;
    }

    public NetUtils(JFrame parent, String ip, int port) {
        this(parent);
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket();) {
            socket.connect(new InetSocketAddress(ip, port));
            System.out.println("Socket connected " + socket);
            
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            
//            int counter = 0;
            
            ActivityLogWriter writer = new ActivityLogWriter();
            
            // endlessly receieve infp from robot
            try (InputStreamReader isr = new InputStreamReader(socket.getInputStream())) {
                while (true) {
                    writer.logPrintln(readLine(isr));
//                    Window.getActivityLog().append("Robot: " + readLine(socket, isr) + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Could not connect!\nCheck your IP and port.", "IOException", JOptionPane.ERROR_MESSAGE);
            }
            socket.close();
        } catch (SocketTimeoutException e) {
            JOptionPane.showMessageDialog(parent, "Connection timed out!\nCheck your IP and port.", "SocketTimeoutException", JOptionPane.ERROR_MESSAGE);
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, "Could not connect!\nCheck your IP and port.", "IOException", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void sendLine() {

    }

    /**
     * Reads byte stream from socket and filters into a string
     *
     * @param socket The socket used to pass in the stream
     * @return String from byte stream
     */
    private String readLine(InputStreamReader isr) {

        String line;
        boolean isDone = false;

        line = "";
        isDone = false;
        while (!isDone) {
            
            try {
                if (isr.ready()) {
                    line += (char) isr.read();
                }
                if (!isr.ready() && line != "") {
                    isDone = true;
                }
            } catch (IOException ex) {
                Logger.getLogger(NetUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return line;
    }
    
    public boolean isConnected() {
        return isConnected;
    }
    
    public void setIP(String ip) {
        this.ip = ip;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
}
