package com.charbelchougourou.trinkspielplatz;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Random;

public class KingsCupActivity extends AppCompatActivity {
    private AdView adView;
    private AlertDialog.Builder builder;
    private Button naechsteKarteButton;
    private ImageView imageView;
    private final List<Card> deck = new ArrayList<>();
    private TextView textView1;
    private TextView textView2;
    private int i = 0;

    private final int[] trinkanzahl = {2,3,4,5};

    private int randomAnzahl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kingscup);
        Card firstCard = new Card(0, "back2", 0, "back", "");
        List<Card> randomDeck = Deck.randomizeListOfCards(Deck.createDeck());
        deck.add(firstCard);
        deck.addAll(randomDeck);
        initViews();
        openStartKingsCupDialog();
        next();
        initToolbars();
        setKingsCupText();
        randomTrinkanzahl();

        textView1.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
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
        toolbar.setTitle(R.string.btn_kingscup);
    }

    public void initViews() {
        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        textView1 = findViewById(R.id.textViewKingsCup1);
        textView2 = findViewById(R.id.textViewKingsCup2);
        imageView = findViewById(R.id.imageView);
        naechsteKarteButton = findViewById(R.id.saveSpielerPferderennenButton);
    }

    public void openStartKingsCupDialog() {
        builder.setCancelable(true)
                .setMessage("Ihr braucht zum Spielen ein weiteres Glas als King's Cup")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Spielstart");
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.blue));
    }


    public void next(){
        naechsteKarteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i++;
                textView1.setText(String.format("Verbleibende Karten: %s", deck.size() - 1 - i));
                imageView.setImageResource(changeImage(i));
                setKingsCupText();
                checkIndex(i);
            }
        });
    }

    public void setKingsCupText(){
        switch(deck.get(i).getValue()) {
            case 2:
                textView2.setText(String.format("Verteile %s Schlucke", randomTrinkanzahl()));
                break;
            case 3:
                textView2.setText(String.format("Trinke %s Schlucke", randomTrinkanzahl()));
                break;
            case 4:
                textView2.setText(String.format("Wer zuletzt den Boden berührt, trinkt %s Schlucke", randomTrinkanzahl()));
                break;
            case 5:
                textView2.setText(String.format("Wer zuletzt den Daumen auf den Tisch legt, trinkt %s Schlucke", randomTrinkanzahl()));
                break;
            case 6:
                textView2.setText(String.format("Alle Frauen trinken %s Schlucke", randomTrinkanzahl()));
                break;
            case 7:
                textView2.setText(String.format("Wer zuletzt die Hand hebt, trinkt %s Schlucke", randomTrinkanzahl()));
                break;
            case 8:
                textView2.setText("Wähle einen Trinkpartner, er/sie trinkt wenn du trinkst");
                break;
            case 9:
                textView2.setText(String.format("Beginne mit einem Wort, die Mitspieler nennen Reimwörter, der Verlierer trinkt %s Schlucke", randomTrinkanzahl()));
                break;
            case 10:
                textView2.setText(String.format("Alle Männer trinken %s Schlucke", randomTrinkanzahl()));
                break;
            case 11:
                textView2.setText("Erstelle eine Spielregel, alte Regeln können nicht außer Kraft gesetzt werden");
                break;
            case 12:
                textView2.setText("Spielt \"Ich hab noch nie..\"");
                break;
            case 13:
                textView2.setText("Kippe ein Getränk deiner Wahl in den King's Cup");
                break;
            case 14:
                textView2.setText("Wasserfall");
                break;
        }
    }

    public int randomTrinkanzahl() {
        randomAnzahl = trinkanzahl[new Random().nextInt(trinkanzahl.length)];
        return randomAnzahl;
    }

    public void checkIndex(int i){
        if (i == deck.size()-1){
            naechsteKarteButton.setText("Neustart");
            naechsteKarteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }
    }

    public int changeImage(int i) {
        int resID = getResources().getIdentifier(deck.get(i).getCard(), "drawable", getPackageName());
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