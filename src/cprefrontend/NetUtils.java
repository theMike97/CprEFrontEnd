/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
    private DataOutputStream os;
    private ActivityLog log;

    /**
     * Constructor sets parent JFrame and ActivityLog for interface with the user
     * @param parent
     * @param log
     */
    public NetUtils(JFrame parent, ActivityLog log) {
        this.log = log;
        this.parent = parent;
        br = null;
        os = null;
        ip = null;
        port = 0;
    }

    /**
     * Constructor sets ActivityLog, parent JFrame, IP address and port
     * @param parent parent window
     * @param log output ActivityLog
     * @param ip IP address
     * @param port port number
     */
    public NetUtils(JFrame parent, ActivityLog log, String ip, int port) {
        this(parent, log);
        this.ip = ip;
        this.port = port;
    }

    /**
     * Invoked with Thread.start() method. Initializes socket at specified IP
     * and port and stream readers and writers.
     */
    @Override
    public void run() {
        createSocket();
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
     * Creates new Socket, BufferedReader, and DataOutputStream for connecting
     * to a target server, reading incoming data, and writing outgoing data. *
     */
    public void createSocket() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 500);
            System.out.println("Socket connected " + socket);
            log.logPrintln("Socket connected");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new DataOutputStream(socket.getOutputStream());

        } catch (SocketTimeoutException e) {
            JOptionPane.showMessageDialog(parent, "Connection timed out!\nCheck your IP and port.", "SocketTimeoutException", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, "Could not connect!\nCheck your IP and port.", "IOException", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Closes socket, BufferedReader, and DataOutputStream
     *
     * @throws IOException
     */
    public void closeSocket() throws IOException {
        // close output stream writer
        os.flush();
        os.close();

        //close input stream reader
        br.close();

        // close socket
        socket.close();
    }
    
    /**
     * Clears the output stream
     */
    public void flushOutputStream() {
        try {
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(NetUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sends a String via socket to IP:port
     *
     * @param command String to send
     */
    public void sendLine(String command) {
        for (int i = 0; i < command.length(); i++) {
            sendChar(command.charAt(i));
        }
//            os.writeChars(command);
//            os.flush();
    }

    /**
     * Sends a character via socket to IP:port
     *
     * @param c character to send
     */
    public void sendChar(char c) {
        try {
            os.writeByte(c);
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads byte stream from socket and filters into a string
     *
     * @param timeout
     * @return String from byte stream
     */
    public String readLine(long timeout) {

        String line = "";
//        long startTime = System.currentTimeMillis();

        try {
            int i;
            while ((i = br.read()) != ';') { // ';'
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
