package com.charbelchougourou.trinkspielplatz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
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

public class KartenActivity extends AppCompatActivity {
    private AdView adView;

    private Button naechsteKarteButton;
    private ImageButton settingsButton;
    private ImageView imageView;
    private TextView textView;

    private AlertDialog.Builder builder;
    private boolean switchHerz = true;
    private boolean switchKreuz = true;
    private boolean switchKaro = true;
    private boolean switchPik = true;
    private boolean switch2Bis6 = true;
    private boolean switch7Bis10 = true;
    private boolean switchBubeBisAss = true;
    private List<Card> deck = new ArrayList<>();
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karten);

        List<Card> randomDeck = Deck.randomizeListOfCards(Deck.createDeck());
        deck.addAll(randomDeck);

        initViews();
        initToolbars();

        textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));

        imageView.setImageResource(changeImage(i));

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
        toolbar.setTitle(R.string.btn_karten);
    }

    public void initViews() {
        naechsteKarteButton = findViewById(R.id.naechsteKarteButtonKarten);
        naechsteKarteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkIndex();
            }
        });

        settingsButton = findViewById(R.id.settingsButtonKarten);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings();
            }
        });

        textView = findViewById(R.id.textViewKarten);
        imageView = findViewById(R.id.imageViewKarten);


        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
    }

    public void settings() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View switchView = inflater.inflate(R.layout.dialog_karten, null, false);


        Switch switch1 = switchView.findViewById(R.id.switchHerz);
        Switch switch2 = switchView.findViewById(R.id.switchKreuz);
        Switch switch3 = switchView.findViewById(R.id.switchKaro);
        Switch switch4 = switchView.findViewById(R.id.switchPik);
        Switch switch5 = switchView.findViewById(R.id.switch2bis6);
        Switch switch6 = switchView.findViewById(R.id.switch7bis10);
        Switch switch7 = switchView.findViewById(R.id.switchBubeBisAss);

        if (switchHerz) {
            switch1.setOnCheckedChangeListener (null);
            switch1.setChecked(true);
        }
        if (switchKreuz) {
            switch2.setOnCheckedChangeListener (null);
            switch2.setChecked(true);
        }
        if (switchKaro) {
            switch3.setOnCheckedChangeListener (null);
            switch3.setChecked(true);
        }
        if (switchPik) {
            switch4.setOnCheckedChangeListener (null);
            switch4.setChecked(true);
        }
        if (switch2Bis6) {
            switch5.setOnCheckedChangeListener (null);
            switch5.setChecked(true);
        }
        if (switch7Bis10) {
            switch6.setOnCheckedChangeListener (null);
            switch6.setChecked(true);
        }
        if (switchBubeBisAss) {
            switch7.setOnCheckedChangeListener (null);
            switch7.setChecked(true);
        }

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchHerz = b;
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchKreuz = b;
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchKaro = b;
            }
        });

        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchPik = b;
            }
        });

        switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch2Bis6 = b;
            }
        });

        switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch7Bis10 = b;
            }
        });

        switch7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchBubeBisAss = b;
            }
        });

        builder.setCancelable(false)
                .setView(switchView)
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deck.removeAll(deck);
                        List<Card> randomDeck = Deck.randomizeListOfCards(Deck.createDeck());
                        deck.addAll(randomDeck);
                        List<Card> cardsToRemove = new ArrayList<>();
                        for (Card card : deck) {
                            if (!switchHerz && card.getZeichen().equals("herz")) {
                                cardsToRemove.add(card);
                            }
                            if (!switchKreuz && card.getZeichen().equals("kreuz")) {
                                cardsToRemove.add(card);
                            }
                            if (!switchKaro && card.getZeichen().equals("karo")) {
                                cardsToRemove.add(card);
                            }
                            if (!switchPik && card.getZeichen().equals("pik")) {
                                cardsToRemove.add(card);
                            }
                            if (!switch2Bis6 && card.getValue() >= 2 && card.getValue() <= 6) {
                                cardsToRemove.add(card);
                            }
                            if (!switch7Bis10 && card.getValue() >= 7 && card.getValue() <= 10) {
                                cardsToRemove.add(card);
                            }
                            if (!switchBubeBisAss && card.getValue() >= 11) {
                                cardsToRemove.add(card);
                            }
                        }
                        deck.removeAll(cardsToRemove);
                        naechsteKarteButton.setText(R.string.naechste_karte);
                        textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));

                        imageView.setImageResource(changeImage(i));
                    }
                });

        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Filter Karten");

        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.blue));
    }

    public void checkIndex(){
        if (i == deck.size()-1) {
            naechsteKarteButton.setText(R.string.naechste_karte);
            i = 0;
            deck = Deck.randomizeListOfCards(deck);
        }
        else if (i < deck.size()-1) {
            i++;
        }
        if (i == deck.size()-1) {
            naechsteKarteButton.setText("Neustart");
        }

        textView.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
        imageView.setImageResource(changeImage(i));
    }

    public int changeImage(int i) {
        return getResources().getIdentifier(deck.get(i).getCard(), "drawable", getPackageName());
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