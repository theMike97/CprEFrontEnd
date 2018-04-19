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
import java.net.SocketException;
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
    private BufferedReader br;
    private ActivityLogWriter writer;

    public NetUtils(JFrame parent) {
        this.parent = parent;
        writer = new ActivityLogWriter();
        br = null;
        ip = null;
        port = 0;
    }

    public NetUtils(JFrame parent, String ip, int port) {
        this(parent);
        this.ip = ip;
        this.port = port;
    }

    /**
     * Invoked with Thread.start() method. Initializes socket at specified IP
     * and port and stream readers and writers.
     */
    @Override
    public void run() {
        writer = new ActivityLogWriter();
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 500);
            System.out.println("Socket connected " + socket);
            writer.logPrintln("Socket connected");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        } catch (SocketTimeoutException e) {
            JOptionPane.showMessageDialog(parent, "Connection timed out!\nCheck your IP and port.", "SocketTimeoutException", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, "Could not connect!\nCheck your IP and port.", "IOException", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returns field socket
     *
     * @return socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Closes socket and InputStreamReader
     *
     * @throws IOException
     */
    public void closeSocket() throws IOException {
        br.close();
        socket.close();
    }

    public void sendLine() {

    }

    /**
     * Reads byte stream from socket and filters into a string
     *
     * @param timeout
     * @return String from byte stream
     * @throws java.net.SocketException
     */
    public String readLine(int timeout) {

        String line = "";
        long startTime = System.currentTimeMillis();
        long elapsedTime = startTime;
        
        try {
            
            int i;
            
            while ((i = br.read()) != ';' && (elapsedTime = System.currentTimeMillis() - startTime) < timeout) { // ';'
                char c = (char) i;
                line += c;
//                System.out.print(c);
            }
//            System.out.println("here");
        } catch (IOException ex) {
            Logger.getLogger(NetUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }

    /**
     * Sets IP for socket
     *
     * @param ip IP number for socket to connect to
     */
    public void setIP(String ip) {
        this.ip = ip;
    }

    /**
     * Sets port for socket
     *
     * @param port Port number for socket to connect through
     */
    public void setPort(int port) {
        this.port = port;
    }
}
