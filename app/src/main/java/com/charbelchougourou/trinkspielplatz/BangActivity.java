package com.charbelchougourou.trinkspielplatz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.Random;

public class BangActivity extends AppCompatActivity {
    private AdView adView;
    private Intent intent;
    private int countdown = 3;
    private long timeSpieler1 = 0;
    private long timeSpieler2 = 0;
    private CountDownTimer timer = null;
    private int randomTime = 0;
    private int[] trinkanzahl = {1,2,3,4,5,6,7};
    int randomAnzahl = 0;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private Button button;
    private Button button2;
    private Button closeDialogBtn1;
    private Button closeDialogBtn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang);
        initViews();
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
        intent = new Intent(this, AnleitungActivity.class);
        switch (item.getItemId()) {
            case R.id.anleitungen:
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initToolbars() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.btn_bang);
    }

    public void initViews(){
        textView = findViewById(R.id.textView5);
        textView2 = findViewById(R.id.textView8);
        button = findViewById(R.id.button10);
        button2 = findViewById(R.id.button11);
        textView.setText(R.string.start);
        textView2.setText(R.string.start);
        button.setText(R.string.start);
        button2.setText(R.string.start);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { startCountdown(); }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { startCountdown(); }
        });
    }

    public void startCountdown() {
        randomTime = new Random().nextInt(5001);
        timer = new CountDownTimer(3999, 1000) {
            @Override
            public void onTick(long l) {
                long time = l / 1000;
                if (time == 0l) {
                    setTimeout(() -> {
                        textView.setText("BANG!");
                        textView2.setText("BANG!");
                        button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                timeSpieler1 = System.currentTimeMillis();
                                checkTime();
                            }
                        });
                        button2.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                timeSpieler2 = System.currentTimeMillis();
                                checkTime();
                            }
                        });
                    }, randomTime);
                }
                else {
                    textView.setText("" + time);
                    textView2.setText("" + time);
                    button.setText("BANG!");
                    button2.setText("BANG!");
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            timer.cancel();
                            showZuschnellDialog(1);
                            initViews();
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            timer.cancel();
                            showZuschnellDialog(2);
                            initViews();
                        }
                    });
                }
            }
            @Override
            public void onFinish() {}
        };
        timer.start();
    }

    public void checkTime() {
        if ((timeSpieler1 > timeSpieler2 && timeSpieler2 > 0) || (timeSpieler2 > 0 && timeSpieler1 == 0)) {
            showVerliererDialog(1);
        }
        else if ((timeSpieler2 > timeSpieler1 && timeSpieler1 > 0) || (timeSpieler1 > 0 && timeSpieler2 == 0))  {
            showVerliererDialog(2);
        }
        initViews();
    }

    public void showZuschnellDialog(int zuschnell) {
        final Dialog dialog=new Dialog(this,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.zuschnell_dialog);

        randomAnzahl = trinkanzahl[new Random().nextInt(trinkanzahl.length)];

        textView3 = dialog.findViewById(R.id.textView13);
        textView3.setText("Spieler "+ zuschnell + " hat zu schnell geschossen und trinkt " + randomAnzahl + " Schluck/e!");
        textView4 = dialog.findViewById(R.id.textView14);
        textView4.setText("Spieler "+ zuschnell + " hat zu schnell geschossen und trinkt " + randomAnzahl + " Schluck/e!");
        closeDialogBtn1 = dialog.findViewById(R.id.button14);
        closeDialogBtn2 = dialog.findViewById(R.id.saveSpielerPferderennenButton);
        closeDialogBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        closeDialogBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void showVerliererDialog(int verlierer) {
        final Dialog dialog=new Dialog(this,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.verlierer_dialog);

        randomAnzahl = trinkanzahl[new Random().nextInt(trinkanzahl.length)];

        textView3 = dialog.findViewById(R.id.textView13);
        textView3.setText("Spieler "+ verlierer + " hat verloren und trinkt " + randomAnzahl + " Schluck/e!");
        textView4 = dialog.findViewById(R.id.textView14);
        textView4.setText("Spieler "+ verlierer + " hat verloren und trinkt " + randomAnzahl + " Schluck/e!");
        closeDialogBtn1 = dialog.findViewById(R.id.button14);
        closeDialogBtn2 = dialog.findViewById(R.id.saveSpielerPferderennenButton);
        timeSpieler1 = 0;
        timeSpieler2 = 0;
        closeDialogBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        closeDialogBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
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

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

}