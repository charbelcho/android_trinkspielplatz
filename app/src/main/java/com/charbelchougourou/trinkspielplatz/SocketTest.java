package com.charbelchougourou.trinkspielplatz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketTest extends AppCompatActivity {
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://localhost:3000");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocket.connect();
        setContentView(R.layout.activity_socket_test);
    }
}