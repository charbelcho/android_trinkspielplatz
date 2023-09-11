package com.charbelchougourou.trinkspielplatz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class WahrheitPflichtActivity extends AppCompatActivity {
    private AdView adView;
    private List<String> wahrheitList;
    private List<String> pflichtList;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private Button button;
    private ImageButton imageButton;
    private boolean enableWahrheit;
    private boolean enablePflicht;
    private int i = 0;
    private int j = 0;
    private final int[] trinkanzahl = {3,4,5,6,7};
    int randomAnzahl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wahrheitpflicht);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        wahrheitList = databaseAccess.getWahrheitList();
        pflichtList = databaseAccess.getPflichtList();
        databaseAccess.close();
        enableWahrheit = false;
        enablePflicht = false;
        i = new Random().nextInt(wahrheitList.size() - 1);
        j = new Random().nextInt(pflichtList.size() - 1);
        initViews();
        next();
        randomTrinkanzahl();
        initToolbars();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
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
        Intent intent0 = new Intent(this, AnleitungActivity.class);
        if (item.getItemId() == R.id.anleitungen) {
            startActivity(intent0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbars() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.btn_wahrheit_pflicht);
    }

    public void initViews() {
        button = findViewById(R.id.saveSpielerPferderennenButton);
        textView = findViewById(R.id.textViewWerBinIch1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView4);
        imageButton = findViewById(R.id.imageButton);
    }

    public void next() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(enableWahrheit || enablePflicht) {
                    i = new Random().nextInt(wahrheitList.size());
                    j = new Random().nextInt(pflichtList.size());
                    textView.setBackgroundResource(R.drawable.rounded_corner);
                    textView.setTextColor(Color.parseColor("#ffffff"));
                    textView.setTextSize(24);
                    textView.setText(R.string.wahrheit);
                    textView.setVisibility(View.VISIBLE);
                    textView2.setBackgroundResource(R.drawable.rounded_corner);
                    textView2.setTextColor(Color.parseColor("#ffffff"));
                    textView2.setTextSize(24);
                    textView2.setText(R.string.pflicht);
                    textView2.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.GONE);
                    enableWahrheit = false;
                    enablePflicht = false;
                }
            }
        });
    }

    public void onClickWahrheit(View v) {
        if(!enablePflicht){
            enableWahrheit = true;
            textView.setBackgroundColor(Color.parseColor("#ffffff"));
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextSize(16);
            textView.setText(wahrheitList.get(i));
        }
    }

    public void onClickPflicht(View v) {
        if(!enableWahrheit) {
            enablePflicht = true;
            textView2.setBackgroundColor(Color.parseColor("#ffffff"));
            textView2.setTextColor(Color.parseColor("#000000"));
            textView2.setTextSize(16);
            textView2.setText(pflichtList.get(j));
        }
    }

    public void randomTrinkanzahl() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enableWahrheit || enablePflicht) {
                    randomAnzahl = trinkanzahl[new Random().nextInt(trinkanzahl.length)];
                    textView.setVisibility(View.GONE);
                    textView2.setVisibility(View.GONE);
                    textView3.setVisibility(View.VISIBLE);
                    textView3.setText(String.format(Locale.GERMANY, "Trinke %d Schlucke!", randomAnzahl));
                    enableWahrheit = true;
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
    }

}