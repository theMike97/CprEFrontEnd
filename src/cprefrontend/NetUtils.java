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
    private Socket socket;
    private InputStreamReader isr;
    
    public NetUtils(JFrame parent) {
        this.parent = parent;
        isr = null;
        ip = null;
        port = 0;
    }

    public NetUtils(JFrame parent, String ip, int port) {
        this(parent);
        this.ip = ip;
        this.port = port;
    }

    /**
     * Invoked with Thread.start() method.  Initializes socket at specified IP and port and stream readers and writers.
     */
    @Override
    public void run() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 500);
            System.out.println("Socket connected " + socket);
            
            isr = new InputStreamReader(socket.getInputStream());
            
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            ActivityLogWriter writer = new ActivityLogWriter();
            
        } catch (SocketTimeoutException e) {
            JOptionPane.showMessageDialog(parent, "Connection timed out!\nCheck your IP and port.", "SocketTimeoutException", JOptionPane.ERROR_MESSAGE);
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, "Could not connect!\nCheck your IP and port.", "IOException", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Returns field socket
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }
    
    /**
     * Closes socket and InputStreamReader
     * @throws IOException 
     */
    public void closeSocket() throws IOException {
        isr.close();
        socket.close();
    }
    
    public void sendLine() {

    }

    /**
     * Reads byte stream from socket and filters into a string
     * @return String from byte stream
     */
    public String readLine() {

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
    
    /**
     * Sets IP for socket
     * @param ip IP number for socket to connect to
     */
    public void setIP(String ip) {
        this.ip = ip;
    }
    
    /**
     * Sets port for socket
     * @param port Port number for socket to connect through
     */
    public void setPort(int port) {
        this.port = port;
    }
}
