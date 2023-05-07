package com.charbelchougourou.trinkspielplatz;

import android.app.Application;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class SocketApp extends Application {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://localhost:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
