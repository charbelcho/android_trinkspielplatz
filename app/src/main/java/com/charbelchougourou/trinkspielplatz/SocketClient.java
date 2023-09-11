package com.charbelchougourou.trinkspielplatz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
    private Socket socket;
    private BufferedReader reader;
    private OutputStream outputStream;

    public void connect() {
        try {
            // Replace SERVER_IP and SERVER_PORT with your server details
            socket = new Socket("http://localhost", 8000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = socket.getOutputStream();

            // Connection successful, handle communication here
            // For example, you can start a separate thread to listen for incoming data
            Thread incomingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String data;
                        while ((data = reader.readLine()) != null) {
                            // Handle incoming data from the server
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            incomingThread.start();

            // Example: sending data to the server
            String message = "Hello, server!";
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
