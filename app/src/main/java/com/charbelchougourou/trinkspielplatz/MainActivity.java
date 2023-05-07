package com.charbelchougourou.trinkspielplatz;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView adView;
    private Intent intent0;
    private Intent intent1;
    private Intent intent2;
    private Intent intent3;
    private Intent intent4;
    private Intent intent5;
    private Intent intent6;
    private Intent intent7;
    private Intent intent8;
    private Intent intent9;
    private Intent intent10;
    private Intent intent11;
    private Intent intent12;
    private Intent intent13;
    private Intent intent14;


    private String serverAdress = "localhost";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        initIntents();
        initToolbars();
        redirectToGame();
        MobileAds.initialize(this, initializationStatus -> {
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
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

    public void initIntents() {
        intent1 = new Intent(this, NochnieActivity.class);
        intent2 = new Intent(this, WahrheitPflichtActivity.class);
        intent3 = new Intent(this, EherActivity.class);
        intent4 = new Intent(this, HoeherTieferActivity.class);
        intent5 = new Intent(this, BangActivity.class);
        intent6 = new Intent(this, ShitheadActivity.class);
        intent7 = new Intent(this, PferderennenActivity.class);
        intent8 = new Intent(this, KingsCupActivity.class);
        intent9 = new Intent(this, WerbinichActivity.class);
        intent10 = new Intent(this, BusfahrerActivity.class);
        intent11 = new Intent(this, MaexchenActivity.class);
        intent12 = new Intent(this, EinhundertActivity.class);
        intent13 = new Intent(this, WuerfelActivity.class);
        intent14 = new Intent(this, KartenActivity.class);
    }

    private void initToolbars() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public static List<String> randomizeList(List<String> listToRandomize) {
        Collections.shuffle(listToRandomize);
        List<String> randomList = new ArrayList<>(listToRandomize);
        return randomList;
    }

    public static List<Card> randomizeListOfCards(List<Card> listToRandomize) {
        Collections.shuffle(listToRandomize);
        return new ArrayList<>(listToRandomize);
    }

    public void redirectToGame(){
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button10 = findViewById(R.id.button10);
        Button button11 = findViewById(R.id.button11);
        Button button12 = findViewById(R.id.button12);
        Button button13 = findViewById(R.id.button13);
        Button button14 = findViewById(R.id.button14);
        button1.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "okay fish");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            startActivity(intent1);
        });
        button2.setOnClickListener(view -> startActivity(intent2));
        button3.setOnClickListener(view -> startActivity(intent3));
        button4.setOnClickListener(view -> startActivity(intent4));
        button5.setOnClickListener(view -> startActivity(intent5));
        button6.setOnClickListener(view -> startActivity(intent6));
        button7.setOnClickListener(view -> startActivity(intent7));
        button8.setOnClickListener(view -> startActivity(intent8));
        button9.setOnClickListener(view -> startActivity(intent9));
        button10.setOnClickListener(view -> startActivity(intent10));
        button11.setOnClickListener(view -> startActivity(intent11));
        button12.setOnClickListener(view -> startActivity(intent12));
        button13.setOnClickListener(view -> startActivity(intent13));
        button14.setOnClickListener(view -> startActivity(intent14));
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
    }
}