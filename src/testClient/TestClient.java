/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Mike
 */
public class TestClient {

    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("192.168.1.1", 288), 15000);
            System.out.println("Socket connected " + socket);

            String line;
            boolean isDone = false;

            try (InputStreamReader isr = new InputStreamReader(socket.getInputStream())) {
                while (true) {
                    line = "";
                    isDone = false;
                    while (!isDone) {
                        if (isr.ready()) {
                            line += (char) isr.read();
                        }
                        if (!isr.ready() && line != "") {
                            isDone = true;
                            System.out.println(line);
                        }
                    }
                }
            }
        }

    }

}
