package com.charbelchougourou.trinkspielplatz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class ShitheadActivity extends AppCompatActivity {
    private AdView adView;
    private Intent intent0;
    private TextView textView;
    private TextView textView2;
    private Button naechsteKarteButton;
    private ImageView imageView;
    private Card firstCard;
    private List<Card> deck = new ArrayList<>();
    private List<Card> randomDeck;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shithead);
        firstCard = new Card(0, "back2", 0, "back", "");
        randomDeck = Deck.randomizeListOfCards(Deck.createDeck());
        deck.add(firstCard);
        deck.addAll(randomDeck);
        initViews();
        next();
        initToolbars();

        textView.setText(String.format("Verbleibende Karten: %s", 51 - i));
        textView2.setText("");
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
        toolbar.setTitle(R.string.btn_shithead);
    }

    public void initViews() {
        textView = findViewById(R.id.textViewKingsCup1);
        textView2 = findViewById(R.id.textViewKingsCup2);
        imageView = findViewById(R.id.imageView);
        naechsteKarteButton = findViewById(R.id.saveSpielerPferderennenButton);
    }

    public void next(){
        naechsteKarteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i++;
                textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
                imageView.setImageResource(changeImage(i));
                if (i > 0) {
                    if ((deck.get(i).getFarbe() == "rot") && (between(0, deck.get(i).getValue(), 11))) {
                        textView2.setText("Verteile " + deck.get(i).getValue() + " Schlucke!");
                    }
                    else if ((deck.get(i).getFarbe() == "schwarz") && (between(0, deck.get(i).getValue(), 11))){
                        textView2.setText("Trinke " + deck.get(i).getValue() + " Schlucke");
                    }
                    else if (deck.get(i).getValue() == 11) {
                        textView2.setText("Alle Männer trinken 5 Schlucke");
                    }
                    else if (deck.get(i).getValue() == 12) {
                        textView2.setText("Alle Frauen trinken 5 Schlucke");
                    }
                    else if (deck.get(i).getValue() == 13) {
                        textView2.setText("Du bist Captain Shithead");
                    }
                    else if (deck.get(i).getValue() == 14) {
                        textView2.setText("Du bist vor Captain Shithead geschützt");
                    }
                    else {
                        textView2.setText("");
                    }
                }
            }
        });

        if (i == deck.size()-1) {
            naechsteKarteButton.setText("Neustart");
            naechsteKarteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    deck = Deck.randomizeListOfCards(Deck.createDeck());
                    i = 0;
                }
            });
        }
        textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
    }

    public int changeImage(int i) {
        int resID = getResources().getIdentifier(deck.get(i).getCard(), "drawable", getPackageName());
        return resID;
    }

    public boolean between(int start, int value, int end) {
        if (start < value && value < end){
            return true;
        }
        else { return false; }

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