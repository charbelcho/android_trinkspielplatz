package com.charbelchougourou.trinkspielplatz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

public class BusfahrerActivity extends AppCompatActivity {
    private AdView adView;
    //private Intent intent;
    private Intent intent0;
    private Button button;
    private TextView textView;
    private List<String> nochnieList;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busfahrer);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        nochnieList = MainActivity.randomizeList(databaseAccess.getNochnieList());
        databaseAccess.close();
        initViews();
        next();
        initToolbars();
        textView.setText(nochnieList.get(0));

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
        switch (item.getItemId()) {
            case R.id.anleitungen:
                startActivity(intent0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initToolbars() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.btn_busfahrer);
    }

    public void initViews() {
        button = findViewById(R.id.kingsCupNaechsteKarteButton);
        textView = findViewById(R.id.textView2);
    }

    public void next(){
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i++;
                textView.setText(nochnieList.get(i));
                checkIndex(i);
            }
        });
    }

    public void checkIndex(int i){
        if (i == nochnieList.size()-1){
            button.setText("Neustart");
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }
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