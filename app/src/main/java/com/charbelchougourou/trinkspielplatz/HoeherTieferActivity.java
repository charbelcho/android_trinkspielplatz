package com.charbelchougourou.trinkspielplatz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

public class HoeherTieferActivity extends AppCompatActivity {
    private AdView adView;
    private Intent intent;
    private Intent intent2;
    private Intent intent3;
    private Intent intent4;
    private Intent intent5;
    private Intent intent6;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private Button hoeherButton;
    private Button gleichButton;
    private Button tieferButton;
    private Button nochmalButton;
    private ImageView imageView;
    private boolean correct;
    private int correctInRow = 0;
    private List<Card> deck;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoehertiefer);
        deck = Deck.randomizeListOfCards(Deck.createDeck());
        initViews();
        next();
        initToolbars();

        textView.setText(String.format("Verbleibende Karten: %s", 51 - i));
        textView2.setText("");
        textView3.setText(String.format("Richtig in Folge: %s", correctInRow));
        textView4.setText("");
        imageView.setImageResource(changeImage(i));

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
        toolbar.setTitle(R.string.btn_hoeher_tiefer);
    }

    public void initViews() {
        textView = findViewById(R.id.textViewKingsCup1);
        textView2 = findViewById(R.id.textView6);
        textView3 = findViewById(R.id.textView7);
        textView4 = findViewById(R.id.textViewKingsCup2);
        imageView = findViewById(R.id.imageView);
        hoeherButton = findViewById(R.id.kingsCupNaechsteKarteButton);
        gleichButton = findViewById(R.id.button6);
        tieferButton = findViewById(R.id.button7);
        nochmalButton = findViewById(R.id.button9);
    }

    public void next(){
        hoeherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i++;
                textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
                imageView.setImageResource(changeImage(i));
                if (i > 0) {
                    if (deck.get(i).getValue() > deck.get(i - 1).getValue()) {
                        correct = true;
                    }
                    else {
                        correct = false;
                    }
                }
                afterButtonClick();
            }
        });
        gleichButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i++;
                textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
                imageView.setImageResource(changeImage(i));
                if (i > 0) {
                    if (deck.get(i).getValue() == deck.get(i - 1).getValue()) {
                        correct = true;
                    }
                    else {
                        correct = false;
                    }
                }
                afterButtonClick();
            }
        });
        tieferButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i++;
                textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
                imageView.setImageResource(changeImage(i));
                if (i > 0) {
                    if (deck.get(i).getValue() < deck.get(i - 1).getValue()) {
                        correct = true;
                    }
                    else {
                        correct = false;
                    }
                    afterButtonClick();
                }
            }
        });
        if (i == deck.size()-1) {
            hoeherButton.setVisibility(View.GONE);
            gleichButton.setVisibility(View.GONE);
            tieferButton.setVisibility(View.GONE);
            nochmalButton.setVisibility(View.VISIBLE);
        }

        nochmalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deck = MainActivity.randomizeListOfCards(deck);
                i = 0;
            }
        });
        textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
        textView3.setText(String.format("Richtig in Folge: %s", correctInRow));
    }

    public void afterButtonClick() {
        if (correct) {
            textView2.setText("Richtig");
            textView2.setTextColor(getResources().getColor(R.color.green));
            correctInRow++;
        }
        else if (correct == false) {
            textView2.setText("Falsch");
            textView2.setTextColor(getResources().getColor(R.color.red));
            correctInRow = 0;
        }
        textView3.setText(String.format("Richtig in Folge: %s", correctInRow));
        if (correctInRow == 3){
            textView4.setText("Nächster Spieler!");
            correctInRow = 0;
        }
        else {
            textView4.setText("");
        }
    }

    public int changeImage(int i) {
        int resID = getResources().getIdentifier(deck.get(i).getName(), "drawable", getPackageName());
        return resID;
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