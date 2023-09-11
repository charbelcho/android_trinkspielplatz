package com.charbelchougourou.trinkspielplatz;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class MaexchenActivity extends AppCompatActivity {
    private AdView adView;
    private Button naechsterSpielerButton;
    private Button stimmtButton;
    private Button stimmtNichtButton;
    private CountDownTimer timer = null;
    private TextView textViewTippe;
    private TextView textViewTrinkzahl;
    private AlertDialog.Builder builder;
    private ImageButton bierButton;
    private ImageView imageViewWuerfel1;
    private ImageView imageViewWuerfel2;
    private int wuerfelValue1;
    private int wuerfelValue2;
    private final int[] trinkanzahl = {3,4,5,6,7};
    private int randomTrinkzahl = 0;
    private View view1;
    private View view2;
    private int i = 0;
    private boolean trinkanzahlOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maexchen);

        initViews();
        randomTrinkanzahl();
        wuerfeln();
        next();
        stimmtOderStimmtNicht();
        initToolbars();

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
        toolbar.setTitle(R.string.btn_maexchen);
    }

    public void initViews() {
        bierButton = findViewById(R.id.bierButtonMaexchen);
        naechsterSpielerButton = findViewById(R.id.naechsterSpielerButtonMaexchen);
        stimmtButton = findViewById(R.id.stimmtButtonMaexchen);
        stimmtNichtButton = findViewById(R.id.stimmtNichtButtonMaexchen);
        textViewTrinkzahl = findViewById(R.id.textViewTrinkzahlMaexchen);

        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

        imageViewWuerfel1 = findViewById(R.id.imageViewWuerfelMaexchen1);
        imageViewWuerfel2 = findViewById(R.id.imageViewWuerfelMaexchen2);
//        view1 = findViewById(R.id.imageViewWuerfelMaexchen1);
//        view2 = findViewById(R.id.imageViewWuerfelMaexchen2);
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

        // Create ScaleAnimation
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f,  // Start scale X
                1.1f,  // End scale X
                1.0f,  // Start scale Y
                1.1f,  // End scale Y
                Animation.RELATIVE_TO_SELF,  // Pivot point X (relative to the view)
                0.5f,  // Pivot point X (relative to the view)
                Animation.RELATIVE_TO_SELF,  // Pivot point Y (relative to the view)
                0.5f); // Pivot point Y (relative to the view)

        scaleAnimation.setDuration(3000);  // Animation duration (in milliseconds)

        // Create AnimationSet and add animations
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);

        // Apply AnimationSet to the view
        imageViewWuerfel1.startAnimation(animationSet);
        imageViewWuerfel2.startAnimation(animationSet);
    }

    public void changeWuerfelImage() {
        timer = new CountDownTimer(2999, 200) {
            @Override
            public void onTick(long l) {
                wuerfelValue1 = new Random().nextInt(6);
                wuerfelValue2 = new Random().nextInt(6);
                wuerfelValue1 += 1;
                wuerfelValue2 += 1;
                imageViewWuerfel1.setImageResource(changeImage(wuerfelValue1));
                imageViewWuerfel2.setImageResource(changeImage(wuerfelValue2));
            }
            @Override
            public void onFinish() {
                naechsterSpielerButton.setEnabled(true);
                wuerfelValue1 = new Random().nextInt(6);
                wuerfelValue2 = new Random().nextInt(6);
                wuerfelValue1 += 1;
                wuerfelValue2 += 1;
                imageViewWuerfel1.setImageResource(changeImage(wuerfelValue1));
                imageViewWuerfel2.setImageResource(changeImage(wuerfelValue2));
            }
        };
        timer.start();
    }
    
    public void wuerfeln() {
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                view1.setEnabled(false);
                view2.setEnabled(false);
                changeWuerfelImage();
                wuerfelRotieren();
            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                view1.setEnabled(false);
                view2.setEnabled(false);
                changeWuerfelImage();
                wuerfelRotieren();
            }
        });
    }

    public void next(){
        naechsterSpielerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewWuerfel1.setImageResource(changeImage(0));
                imageViewWuerfel2.setImageResource(changeImage(0));
                naechsterSpielerButton.setVisibility(View.GONE);
                stimmtButton.setVisibility(View.VISIBLE);
                stimmtNichtButton.setVisibility(View.VISIBLE);
            }
        });
    }

    public void stimmtOderStimmtNicht(){
        stimmtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewWuerfel1.setEnabled(true);
                imageViewWuerfel2.setEnabled(true);
                naechsterSpielerButton.setVisibility(View.VISIBLE);
                naechsterSpielerButton.setEnabled(false);
                stimmtButton.setVisibility(View.GONE);
                stimmtNichtButton.setVisibility(View.GONE);
            }
        });
        stimmtNichtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewWuerfel1.setImageResource(changeImage(wuerfelValue1));
                imageViewWuerfel1.setEnabled(true);
                imageViewWuerfel2.setImageResource(changeImage(wuerfelValue2));
                imageViewWuerfel2.setEnabled(true);
                naechsterSpielerButton.setVisibility(View.VISIBLE);
                naechsterSpielerButton.setEnabled(false);
                stimmtButton.setVisibility(View.GONE);
                stimmtNichtButton.setVisibility(View.GONE);
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
        randomTrinkzahl = trinkanzahl[new Random().nextInt(trinkanzahl.length)];
        TextView messageTextView = new TextView(MaexchenActivity.this);
        messageTextView.setText(String.format("Trinke %d Schlucke", randomTrinkzahl));
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
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.blue));
    }

    public int changeImage(int value) {
        return getResources().getIdentifier(String.format("wuerfel_%d_black", value), "drawable", getPackageName());
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