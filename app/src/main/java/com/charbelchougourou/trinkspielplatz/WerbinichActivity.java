package com.charbelchougourou.trinkspielplatz;

import static java.lang.System.out;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class WerbinichActivity extends AppCompatActivity {
    private AdView adView;
    //private Intent intent;
    private Intent intent0;
    private Button buttonAuswaehlen;
    private Button buttonZuweisen;
    private Button testButton;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private String username;
    private String roomId;
    private String ersteller;

    private EditText mInputMessageView;
    private Socket mSocket;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_werbinich);
        initViews();
        //next();
        initToolbars();
        connectSocketIO();

        mSocket.on("new message", onNewMessage);

        mSocket.connect();
        mSocket.emit("chat", "hallo");
        setListening();




        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        //AdSize adSize = new AdSize(300, 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // show menu when menu button is pressed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean openAnleitungen(MenuItem item) {
        intent0 = new Intent(this, AnleitungActivity.class);
        if (item.getItemId() == R.id.anleitungen){
            startActivity(intent0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbars() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.btn_werbinich);
    }

    public void initViews() {
        buttonAuswaehlen = findViewById(R.id.werbinichAuswaehlenButton);
        buttonZuweisen = findViewById(R.id.werbinichZuweisenButton);
        textView1 = findViewById(R.id.textViewWerBinIch1);
        textView2 = findViewById(R.id.textViewWerBinIch2);
        textView3 = findViewById(R.id.textViewWerBinIch3);
        textView4 = findViewById(R.id.textViewWerBinIch4);
        textView5 = findViewById(R.id.textViewWerBinIch5);
        textView6 = findViewById(R.id.textViewWerBinIch6);

        mInputMessageView = findViewById(R.id.editTextUsername);
        testButton = findViewById(R.id.testButton);
       // testButton.setOnClickListener(view -> attemptSend());
        testButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String message = mInputMessageView.getText().toString().trim();
                mInputMessageView.setText("");
                if (!message.isEmpty()) {
                    //send message
                    String jsonString = "{message: " + "'" + message + "'" + "}";

                    try {
                        JSONObject jsonData = new JSONObject(jsonString);
                        Log.i("WerbinichActivity", jsonString);
                        mSocket.emit("CHAT", jsonData);
                    } catch (JSONException e) {
                        Log.d("me", "error send message " + e.getMessage());
                    }
                }
            }
        });
    }

    public void auswaehlen(){
        buttonAuswaehlen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //textView.setText(nochnieList.get(i));
            }
        });
    }

    public void zuweisen(){
        buttonZuweisen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //textView.setText(nochnieList.get(i));
            }
        });
    }

    private void setListening() {
        mSocket.on("CHAT", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject messageJson = new JSONObject(args[0].toString());
                    String message = messageJson.getString("message");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView6.setText(message);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }

    private void attemptSend() {
        String message = mInputMessageView.getText().toString().trim();
        Log.i("WerbinichActivity", message);
        if (TextUtils.isEmpty(message)) {
            return;
        }

        //mInputMessageView.setText("");
        mSocket.emit("new message", message);
        mInputMessageView.setText("");
    }

    public void connectSocketIO() {
        try {
            mSocket = IO.socket("http://localhost:8000");
            Log.i("WerbinichActivity", "Successful connection");
        } catch (URISyntaxException e) {
            Log.e("WerbinichActivity", "Error creating Socket.IO socket: " + e.getMessage());
            return;
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            /*getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    addMessage(username, message);
                }
            });*/
        }
    };

}