package com.charbelchougourou.trinkspielplatz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class WuerfelActivity extends AppCompatActivity {
    private AdView adView;

    private Button wuerfelnButton;
    private Button alleWaehlenButton;
    private Button speichernButton;
    private ImageButton bierButton;
    private ImageButton settingsButton;
    private ImageView wuerfel1;
    private ImageView wuerfel2;
    private ImageView wuerfel3;
    private ImageView wuerfel4;
    private ImageView wuerfel5;
    private ImageView wuerfel6;
    private ImageView wuerfel7;
    private ImageView wuerfel8;
    private ImageView wuerfel9;
    private ImageView wuerfel10;
    private ImageView wuerfel11;
    private ImageView wuerfel12;

    private AlertDialog.Builder builder;

    private int anzahlWuerfel = 12;
    private final int[] wuerfelValue = {1,1,1,1,1,1,1,1,1,1,1,1};
    private final boolean[] savedWuerfel = {false,false,false,false,false,false,false,false,false,false,false,false};
    private final int[] trinkanzahl = {3,4,5,6,7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuerfel);

        initViews();
        initToolbars();
        showSettingsDialog();
        alleWaehlenButton();
        safeWuerfel();
        wuerfeln();
        randomTrinkanzahl();

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
        //private Intent intent;
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
        toolbar.setTitle(R.string.btn_wuerfel);
    }

    public void initViews() {
        wuerfelnButton = findViewById(R.id.wuerfelButton);
        alleWaehlenButton = findViewById(R.id.alleWaehlenEntfernenButton);

        bierButton = findViewById(R.id.bierButtonWuerfel);
        settingsButton = findViewById(R.id.settingsButton);

        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

        wuerfel1 = findViewById(R.id.imageViewWuerfel1);
        wuerfel2 = findViewById(R.id.imageViewWuerfel2);
        wuerfel3 = findViewById(R.id.imageViewWuerfel3);
        wuerfel4 = findViewById(R.id.imageViewWuerfel4);
        wuerfel5 = findViewById(R.id.imageViewWuerfel5);
        wuerfel6 = findViewById(R.id.imageViewWuerfel6);
        wuerfel7 = findViewById(R.id.imageViewWuerfel7);
        wuerfel8 = findViewById(R.id.imageViewWuerfel8);
        wuerfel9 = findViewById(R.id.imageViewWuerfel9);
        wuerfel10 = findViewById(R.id.imageViewWuerfel10);
        wuerfel11 = findViewById(R.id.imageViewWuerfel11);
        wuerfel12 = findViewById(R.id.imageViewWuerfel12);


    }

    public void alleWaehlenButton() {
        alleWaehlenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkArray(savedWuerfel)) {
                    for (int i = 0; i < savedWuerfel.length; i ++) {
                        savedWuerfel[i] = true;
                    }
                    wuerfel1.setImageResource(changeImageGreen(wuerfelValue[0]));
                    wuerfel2.setImageResource(changeImageGreen(wuerfelValue[1]));
                    wuerfel3.setImageResource(changeImageGreen(wuerfelValue[2]));
                    wuerfel4.setImageResource(changeImageGreen(wuerfelValue[3]));
                    wuerfel5.setImageResource(changeImageGreen(wuerfelValue[4]));
                    wuerfel6.setImageResource(changeImageGreen(wuerfelValue[5]));
                    wuerfel7.setImageResource(changeImageGreen(wuerfelValue[6]));
                    wuerfel8.setImageResource(changeImageGreen(wuerfelValue[7]));
                    wuerfel9.setImageResource(changeImageGreen(wuerfelValue[8]));
                    wuerfel10.setImageResource(changeImageGreen(wuerfelValue[9]));
                    wuerfel11.setImageResource(changeImageGreen(wuerfelValue[10]));
                    wuerfel12.setImageResource(changeImageGreen(wuerfelValue[11]));
                } else {
                    for (int i = 0; i < savedWuerfel.length; i ++) {
                        savedWuerfel[i] = false;
                    }
                    wuerfel1.setImageResource(changeImage(wuerfelValue[0]));
                    wuerfel2.setImageResource(changeImage(wuerfelValue[1]));
                    wuerfel3.setImageResource(changeImage(wuerfelValue[2]));
                    wuerfel4.setImageResource(changeImage(wuerfelValue[3]));
                    wuerfel5.setImageResource(changeImage(wuerfelValue[4]));
                    wuerfel6.setImageResource(changeImage(wuerfelValue[5]));
                    wuerfel7.setImageResource(changeImage(wuerfelValue[6]));
                    wuerfel8.setImageResource(changeImage(wuerfelValue[7]));
                    wuerfel9.setImageResource(changeImage(wuerfelValue[8]));
                    wuerfel10.setImageResource(changeImage(wuerfelValue[9]));
                    wuerfel11.setImageResource(changeImage(wuerfelValue[10]));
                    wuerfel12.setImageResource(changeImage(wuerfelValue[11]));
                }
            }
        });
    }

    public void showSettingsDialog() {
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings();
            }
        });
    }

    public void settings() {
        LinearLayout einsBisDrei = findViewById(R.id.einsBisDrei);
        LinearLayout vierBisSechs = findViewById(R.id.vierBisSechs);
        LinearLayout siebenBisNeun = findViewById(R.id.siebenBisNeun);
        LinearLayout zehnBisZwoelf = findViewById(R.id.zehnBisZwoelf);

        NumberPicker numberPicker = new NumberPicker(WuerfelActivity.this);

        numberPicker.setMaxValue(12);
        numberPicker.setMinValue(1);
        numberPicker.setValue(anzahlWuerfel);

        numberPicker.setValue(anzahlWuerfel);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                anzahlWuerfel = i1;
                System.out.println(anzahlWuerfel);

            }
        });

        builder.setCancelable(false)
                .setView(numberPicker)
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        switch (anzahlWuerfel) {
                            case 1:
                                wuerfel2.setVisibility(View.INVISIBLE);
                                wuerfel3.setVisibility(View.INVISIBLE);
                                vierBisSechs.setVisibility(View.GONE);
                                siebenBisNeun.setVisibility(View.GONE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                                break;
                            case 2:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.INVISIBLE);
                                vierBisSechs.setVisibility(View.GONE);
                                siebenBisNeun.setVisibility(View.GONE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                                break;
                            case 3:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                vierBisSechs.setVisibility(View.GONE);
                                siebenBisNeun.setVisibility(View.GONE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                            case 4:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.INVISIBLE);
                                wuerfel6.setVisibility(View.INVISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.GONE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                                break;
                            case 5:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.VISIBLE);
                                wuerfel6.setVisibility(View.INVISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.GONE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                                break;
                            case 6:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.VISIBLE);
                                wuerfel6.setVisibility(View.VISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.GONE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                                break;
                            case 7:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.VISIBLE);
                                wuerfel6.setVisibility(View.VISIBLE);
                                wuerfel7.setVisibility(View.VISIBLE);
                                wuerfel8.setVisibility(View.INVISIBLE);
                                wuerfel9.setVisibility(View.INVISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.VISIBLE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                                break;
                            case 8:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.VISIBLE);
                                wuerfel6.setVisibility(View.VISIBLE);
                                wuerfel7.setVisibility(View.VISIBLE);
                                wuerfel8.setVisibility(View.VISIBLE);
                                wuerfel9.setVisibility(View.INVISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.VISIBLE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                                break;
                            case 9:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.VISIBLE);
                                wuerfel6.setVisibility(View.VISIBLE);
                                wuerfel7.setVisibility(View.VISIBLE);
                                wuerfel8.setVisibility(View.VISIBLE);
                                wuerfel9.setVisibility(View.VISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.VISIBLE);
                                zehnBisZwoelf.setVisibility(View.GONE);
                                break;
                            case 10:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.VISIBLE);
                                wuerfel6.setVisibility(View.VISIBLE);
                                wuerfel7.setVisibility(View.VISIBLE);
                                wuerfel8.setVisibility(View.VISIBLE);
                                wuerfel9.setVisibility(View.VISIBLE);
                                wuerfel10.setVisibility(View.VISIBLE);
                                wuerfel11.setVisibility(View.INVISIBLE);
                                wuerfel12.setVisibility(View.INVISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.VISIBLE);
                                zehnBisZwoelf.setVisibility(View.VISIBLE);
                                break;
                            case 11:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.VISIBLE);
                                wuerfel6.setVisibility(View.VISIBLE);
                                wuerfel7.setVisibility(View.VISIBLE);
                                wuerfel8.setVisibility(View.VISIBLE);
                                wuerfel9.setVisibility(View.VISIBLE);
                                wuerfel10.setVisibility(View.VISIBLE);
                                wuerfel11.setVisibility(View.VISIBLE);
                                wuerfel12.setVisibility(View.INVISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.VISIBLE);
                                zehnBisZwoelf.setVisibility(View.VISIBLE);
                                break;
                            case 12:
                                wuerfel2.setVisibility(View.VISIBLE);
                                wuerfel3.setVisibility(View.VISIBLE);
                                wuerfel4.setVisibility(View.VISIBLE);
                                wuerfel5.setVisibility(View.VISIBLE);
                                wuerfel6.setVisibility(View.VISIBLE);
                                wuerfel7.setVisibility(View.VISIBLE);
                                wuerfel8.setVisibility(View.VISIBLE);
                                wuerfel9.setVisibility(View.VISIBLE);
                                wuerfel10.setVisibility(View.VISIBLE);
                                wuerfel11.setVisibility(View.VISIBLE);
                                wuerfel12.setVisibility(View.VISIBLE);
                                vierBisSechs.setVisibility(View.VISIBLE);
                                siebenBisNeun.setVisibility(View.VISIBLE);
                                zehnBisZwoelf.setVisibility(View.VISIBLE);
                                break;
                        }

                    }
                });

        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Anzahl Würfel");

        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.blue));
    }

    public void safeWuerfel() {
        wuerfel1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[0]) {
                    wuerfel1.setImageResource(changeImageGreen(wuerfelValue[0]));
                    savedWuerfel[0] = true;
                } else {
                    wuerfel1.setImageResource(changeImage(wuerfelValue[0]));
                    savedWuerfel[0] = false;
                }
            }
        });
        wuerfel2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[1]) {
                    wuerfel2.setImageResource(changeImageGreen(wuerfelValue[1]));
                    savedWuerfel[1] = true;
                } else {
                    wuerfel2.setImageResource(changeImage(wuerfelValue[1]));
                    savedWuerfel[1] = false;
                }
            }
        });
        wuerfel3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[2]) {
                    wuerfel3.setImageResource(changeImageGreen(wuerfelValue[2]));
                    savedWuerfel[2] = true;
                } else {
                    wuerfel3.setImageResource(changeImage(wuerfelValue[2]));
                    savedWuerfel[2] = false;
                }
            }
        });
        wuerfel4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[3]) {
                    wuerfel4.setImageResource(changeImageGreen(wuerfelValue[3]));
                    savedWuerfel[3] = true;
                } else {
                    wuerfel4.setImageResource(changeImage(wuerfelValue[3]));
                    savedWuerfel[3] = false;
                }
            }
        });
        wuerfel5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[4]) {
                    wuerfel5.setImageResource(changeImageGreen(wuerfelValue[4]));
                    savedWuerfel[4] = true;
                } else {
                    wuerfel5.setImageResource(changeImage(wuerfelValue[4]));
                    savedWuerfel[4] = false;
                }
            }
        });
        wuerfel6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[5]) {
                    wuerfel6.setImageResource(changeImageGreen(wuerfelValue[5]));
                    savedWuerfel[5] = true;
                } else {
                    wuerfel6.setImageResource(changeImage(wuerfelValue[5]));
                    savedWuerfel[5] = false;
                }
            }
        });
        wuerfel7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[6]) {
                    wuerfel7.setImageResource(changeImageGreen(wuerfelValue[6]));
                    savedWuerfel[6] = true;
                } else {
                    wuerfel7.setImageResource(changeImage(wuerfelValue[6]));
                    savedWuerfel[6] = false;
                }
            }
        });
        wuerfel8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[7]) {
                    wuerfel8.setImageResource(changeImageGreen(wuerfelValue[7]));
                    savedWuerfel[7] = true;
                } else {
                    wuerfel8.setImageResource(changeImage(wuerfelValue[7]));
                    savedWuerfel[7] = false;
                }
            }
        });
        wuerfel9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[8]) {
                    wuerfel9.setImageResource(changeImageGreen(wuerfelValue[8]));
                    savedWuerfel[8] = true;
                } else {
                    wuerfel9.setImageResource(changeImage(wuerfelValue[8]));
                    savedWuerfel[8] = false;
                }
            }
        });
        wuerfel10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[9]) {
                    wuerfel10.setImageResource(changeImageGreen(wuerfelValue[9]));
                    savedWuerfel[9] = true;
                } else {
                    wuerfel10.setImageResource(changeImage(wuerfelValue[9]));
                    savedWuerfel[9] = false;
                }
            }
        });
        wuerfel11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[10]) {
                    wuerfel11.setImageResource(changeImageGreen(wuerfelValue[10]));
                    savedWuerfel[10] = true;
                } else {
                    wuerfel11.setImageResource(changeImage(wuerfelValue[10]));
                    savedWuerfel[10] = false;
                }
            }
        });
        wuerfel12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!savedWuerfel[11]) {
                    wuerfel12.setImageResource(changeImageGreen(wuerfelValue[11]));
                    savedWuerfel[11] = true;
                } else {
                    wuerfel12.setImageResource(changeImage(wuerfelValue[11]));
                    savedWuerfel[11] = false;
                }
            }
        });
    }

    public void wuerfelRotieren() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0,  // Start angle (in degrees)
                360,  // End angle (in degrees)
                Animation.RELATIVE_TO_SELF,  // Pivot point X (relative to the view)
                0.5f,  // Pivot point X (relative to the view)
                Animation.RELATIVE_TO_SELF,  // Pivot point Y (relative to the view)
                0.5f); // Pivot point Y (relative to the view)

        rotateAnimation.setDuration(3000);  // Animation duration (in milliseconds)


        // Create AnimationSet and add animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);

        // Apply AnimationSet to the view
        if(!savedWuerfel[0] && anzahlWuerfel >= 1) { wuerfel1.startAnimation(animationSet); }
        if(!savedWuerfel[1] && anzahlWuerfel >= 2) { wuerfel2.startAnimation(animationSet); }
        if(!savedWuerfel[2] && anzahlWuerfel >= 3) { wuerfel3.startAnimation(animationSet); }
        if(!savedWuerfel[3] && anzahlWuerfel >= 4) { wuerfel4.startAnimation(animationSet); }
        if(!savedWuerfel[4] && anzahlWuerfel >= 5) { wuerfel5.startAnimation(animationSet); }
        if(!savedWuerfel[5] && anzahlWuerfel >= 6) { wuerfel6.startAnimation(animationSet); }
        if(!savedWuerfel[6] && anzahlWuerfel >= 7) { wuerfel7.startAnimation(animationSet); }
        if(!savedWuerfel[7] && anzahlWuerfel >= 8) { wuerfel8.startAnimation(animationSet); }
        if(!savedWuerfel[8] && anzahlWuerfel >= 9) { wuerfel9.startAnimation(animationSet); }
        if(!savedWuerfel[9] && anzahlWuerfel >= 10) { wuerfel10.startAnimation(animationSet); }
        if(!savedWuerfel[10] && anzahlWuerfel >= 11) { wuerfel11.startAnimation(animationSet); }
        if(!savedWuerfel[11] && anzahlWuerfel >= 12) { wuerfel12.startAnimation(animationSet); }
    }

    public void changeWuerfelImage() {
        for (int i = 0; i < wuerfelValue.length; i++) {
            wuerfelValue[i] = new Random().nextInt(6) + 1;
        }

        CountDownTimer timer = new CountDownTimer(2599, 2599) {
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                if (!savedWuerfel[0]) {
                    wuerfel1.setImageResource(changeImage(wuerfelValue[0]));
                }
                if (!savedWuerfel[1]) {
                    wuerfel2.setImageResource(changeImage(wuerfelValue[1]));
                }
                if (!savedWuerfel[2]) {
                    wuerfel3.setImageResource(changeImage(wuerfelValue[2]));
                }
                if (!savedWuerfel[3]) {
                    wuerfel4.setImageResource(changeImage(wuerfelValue[3]));
                }
                if (!savedWuerfel[4]) {
                    wuerfel5.setImageResource(changeImage(wuerfelValue[4]));
                }
                if (!savedWuerfel[5]) {
                    wuerfel6.setImageResource(changeImage(wuerfelValue[5]));
                }
                if (!savedWuerfel[6]) {
                    wuerfel7.setImageResource(changeImage(wuerfelValue[6]));
                }
                if (!savedWuerfel[7]) {
                    wuerfel8.setImageResource(changeImage(wuerfelValue[7]));
                }
                if (!savedWuerfel[8]) {
                    wuerfel9.setImageResource(changeImage(wuerfelValue[8]));
                }
                if (!savedWuerfel[9]) {
                    wuerfel10.setImageResource(changeImage(wuerfelValue[9]));
                }
                if (!savedWuerfel[10]) {
                    wuerfel11.setImageResource(changeImage(wuerfelValue[10]));
                }
                if (!savedWuerfel[11]) {
                    wuerfel12.setImageResource(changeImage(wuerfelValue[11]));
                }

            }
        };
        timer.start();
    }

    public void wuerfeln() {
        wuerfelnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeWuerfelImage();
                wuerfelRotieren();
            }
        });
    }

    public void randomTrinkanzahl() {
        bierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTrinkzahlDialog();
            }
        });
    }

    public void openTrinkzahlDialog() {
        int randomTrinkzahl = trinkanzahl[new Random().nextInt(trinkanzahl.length)];
        TextView messageTextView = new TextView(WuerfelActivity.this);
        messageTextView.setText(String.format(Locale.GERMANY, "Trinke %d Schlucke", randomTrinkzahl));
        messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 90);
        messageTextView.setTextColor(Color.RED);
        messageTextView.setGravity(Gravity.CENTER);
        builder.setCancelable(false)
                .setView(messageTextView)
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Strafe");

        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.blue));
    }

    public int changeImage(int value) {
        return getResources().getIdentifier(String.format(Locale.GERMANY, "wuerfel_%d_black", value), "drawable", getPackageName());
    }

    public int changeImageGreen(int value) {
        return getResources().getIdentifier(String.format(Locale.GERMANY, "wuerfel_%d_green", value), "drawable", getPackageName());
    }

    public boolean checkArray(boolean[] array) {
        for (int i = 0; i < array.length; i++) {
            if (!array[i]) return true;
        }
        return false;
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