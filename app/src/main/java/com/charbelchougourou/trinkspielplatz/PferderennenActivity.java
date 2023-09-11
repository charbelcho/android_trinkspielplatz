package com.charbelchougourou.trinkspielplatz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.common.primitives.Ints;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PferderennenActivity extends AppCompatActivity {
    private AdView adView;
    private Intent intent0;
    private Button naechsteKarteButton;
    private Button addSpielerButton;
    private Button playButton;
    private ListView spielerListe;
    private EditText editName;
    private EditText editSchlucke;
    private RadioGroup zeichenWahl;
    private RadioButton zeichenGewaehlt;
    private ImageView imageView;
    private ArrayAdapter<SpielerPferderennen> adapter;
    private ArrayList<SpielerPferderennen> spieler = new ArrayList<>();
    private List<Card> randomStapel;
    private List<Card> randomVerdeckt;
    private LinearLayout lVerdeckt;
    private LinearLayout lHerz;
    private LinearLayout lKreuz;
    private LinearLayout lKaro;
    private LinearLayout lPik;
    private AlertDialog.Builder builder;
    private int i = -1;
    private int[] trinkanzahl = {3,4,5,6,7};
    private int randomTrinkzahl = 0;
    private String alertTitle = "";
    private boolean[] turnedArray  = new boolean[]{ false, false, false, false, false, false };
    private int position1 = 7;
    private int position2 = 7;
    private int position3 = 7;
    private int position4 = 7;
    private int maxValue = 0;
    private List<SpielerPferderennen> filteredWinner  = new ArrayList<>();
    private List<SpielerPferderennen> filteredLoser  = new ArrayList<>();
    private List<SpielerPferderennen> filteredStrafe  = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pferderennen);

        showSpielerDialog();
        setCardStapel();
        initViews();
        next();
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
        toolbar.setTitle(R.string.btn_pferderennen);
    }

    public void initViews() {
        naechsteKarteButton = findViewById(R.id.pferderennenNaechsteKarteButton);
        imageView = findViewById(R.id.imageView47);

        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

        lVerdeckt = findViewById(R.id.linearLayoutVerdeckt);
        lHerz = findViewById(R.id.linearLayoutHerz);
        lKreuz = findViewById(R.id.linearLayoutKreuz);
        lKaro = findViewById(R.id.linearLayoutKaro);
        lPik = findViewById(R.id.linearLayoutPik);
    }

    public void showSpielerDialog() {
        final Dialog dialog = new Dialog(this,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_spieler_pferderennen);

        spielerListe = dialog.findViewById(R.id.spielerList);

        adapter = new SpielerPferderennenAdapter(this, spieler);
        spielerListe.setAdapter(adapter);

        editName = dialog.findViewById(R.id.editTextName);
        editSchlucke = dialog.findViewById(R.id.editTextSchlucke);
        editSchlucke.setText("1");
        zeichenWahl = dialog.findViewById(R.id.zeichenWahlB);
        addSpielerButton = dialog.findViewById(R.id.saveSpielerPferderennenButton);
        playButton = dialog.findViewById(R.id.openBearbeitenDialogButton);

        addSpielerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String schluckeToParse = editSchlucke.getText().toString();
                int schlucke;
                int selectedId = zeichenWahl.getCheckedRadioButtonId();
                if (schluckeToParse.equals("")) {
                    schlucke = 0;
                }
                else {
                    schlucke = Integer.parseInt(schluckeToParse);
                }

                if (editName.getText().toString().length() == 0) {
                    Toast.makeText(PferderennenActivity.this,
                            "Gib einen Namen ein", Toast.LENGTH_LONG).show();
                }
                else if (schlucke == 0) {
                    Toast.makeText(PferderennenActivity.this,
                            "Gib die Anzahl der Schlucke ein", Toast.LENGTH_LONG).show();
                }
                else if (selectedId == -1) {
                    Toast.makeText(PferderennenActivity.this,
                            "Wähle ein Zeichen aus", Toast.LENGTH_LONG).show();
                }
                else {
                    zeichenGewaehlt = dialog.findViewById(selectedId);
                    String zeichenText = zeichenGewaehlt.getText().toString();
                    adapter.add(new SpielerPferderennen(name, schlucke, zeichenText));
                    editName.setText("");
                    editSchlucke.setText("1");
                    zeichenWahl.clearCheck();
                }
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spieler.size() >= 2) {
                    dialog.cancel();
                } else {
                    Toast.makeText(PferderennenActivity.this,
                            "Füge min. 2 Spieler hinzu", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    public void setCardStapel() {
        randomStapel = Deck.randomizeListOfCardsPferderennen(Deck.createDeck());
        randomVerdeckt = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            randomVerdeckt.add(randomStapel.get(i));
            randomStapel.remove(randomStapel.get(i));
        }
    }

    public void next() {
        naechsteKarteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i++;
                imageView.setImageResource(changeImage(i));
                setCards();
                checkPosition();
            }
        });
    }

    public void setCards() {
        String text = randomStapel.get(i).getZeichen();
        switch (text) {
            case "herz":
                position1 -= 1;
                loopChildren(lHerz, position1);
                break;
            case "kreuz":
                position2 -= 1;
                loopChildren(lKreuz, position2);
                break;
            case "karo":
                position3 -= 1;
                loopChildren(lKaro, position3);
                break;
            case "pik":
                position4 -= 1;
                loopChildren(lPik, position4);
                break;
            default: break;
        }
    }

    public void loopChildren(ViewGroup parent, int pos) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
                if (child != null) {
                    if (i == pos) {
                        child.setVisibility(View.VISIBLE);
                    } else {
                        child.setVisibility(View.INVISIBLE);
                    }
                }
        }
    }

    public void openVerdecktChildren(ViewGroup parent, int pos) {
        int resID = getResources().getIdentifier(randomVerdeckt.get(pos).getCard(), "drawable", getPackageName());
        final ImageView child = (ImageView) parent.getChildAt(pos);
        if (child != null) {
            child.setImageResource(resID);
        }
    }

    public void verdeckeChildren(ViewGroup parent) {
        int resID = getResources().getIdentifier("back2", "drawable", getPackageName());
        for (int i = 0; i < parent.getChildCount(); i++) {
            final ImageView child = (ImageView) parent.getChildAt(i);
            if (child != null) {
                child.setImageResource(resID);
            }
        }
    }


    public void checkPosition() {
        int[] positionArray = new int[]{position1, position2, position3, position4};
        maxValue = Arrays.stream(positionArray).max().getAsInt();
        int winner = Ints.indexOf(positionArray, -1);
        switch(maxValue) {
            case 6:
                if (!turnedArray[0]) {
                    openVerdecktChildren(lVerdeckt, maxValue);
                    goBack(maxValue);
                    turnedArray[0] = true;
                }
                break;
            case 5:
                if (!turnedArray[1]) {
                    openVerdecktChildren(lVerdeckt, maxValue);
                    goBack(maxValue);
                    turnedArray[1] = true;
                }
                break;
            case 4:
                if (!turnedArray[2]) {
                    openVerdecktChildren(lVerdeckt, maxValue);
                    goBack(maxValue);
                    turnedArray[2] = true;
                }
                break;
            case 3:
                if (turnedArray[3] == false) {
                    openVerdecktChildren(lVerdeckt, maxValue);
                    goBack(maxValue);
                    turnedArray[3] = true;
                }
                break;
            case 2:
                if (!turnedArray[4]) {
                    openVerdecktChildren(lVerdeckt, maxValue);
                    goBack(maxValue);
                    turnedArray[4] = true;
                }
                break;
            case 1:
                if (!turnedArray[5]) {
                    openVerdecktChildren(lVerdeckt, maxValue);
                    goBack(maxValue);
                    turnedArray[5] = true;
                }
                break;
            case 0:
                if (!turnedArray[6]) {
                    openVerdecktChildren(lVerdeckt, maxValue);
                    goBack(maxValue);
                    turnedArray[6] = true;
                }
                break;
            default: break;
        }
        if (winner > -1) {
            setWinnerInfo(winner);
        }
    }

    public void setWinnerInfo(int winner) {
        String winnerZeichen = "";
        switch (winner) {
            case 0:
                winnerZeichen = "Herz";
                break;
            case 1:
                winnerZeichen = "Kreuz";
                break;
            case 2:
                winnerZeichen = "Karo";
                break;
            case 3:
                winnerZeichen = "Pik";
                break;
            default:
                break;
        }
        String finalWinnerZeichen = winnerZeichen;
        filteredWinner = spieler.stream().filter(s -> s.getZeichen().equals(finalWinnerZeichen)).collect(Collectors.toList());
        filteredLoser = spieler.stream().filter(s -> s.getZeichen().equals(finalWinnerZeichen) == false).collect(Collectors.toList());
        alertTitle = String.format("%s hat gewonnen!", winnerZeichen);
        openAlertDialog(alertTitle, 0);
    }

    public void goBack(int i) {
        String text = randomVerdeckt.get(i).getZeichen();
        randomTrinkzahl = trinkanzahl[new Random().nextInt(trinkanzahl.length)];
        switch (text) {
            case "herz":
                position1 += 1;
                loopChildren(lHerz, position1);
                break;
            case "kreuz":
                position2 += 1;
                loopChildren(lKreuz, position2);
                break;
            case "karo":
                position3 += 1;
                loopChildren(lKaro, position3);
                break;
            case "pik":
                position4 += 1;
                loopChildren(lPik, position4);
                break;
            default: break;
        }
        final String zeichen = StringUtils.capitalize(text);
        alertTitle = String.format("Strafe für %s", zeichen);
        filteredStrafe = spieler.stream().filter(s -> s.getZeichen().equals(zeichen)).collect(Collectors.toList());
        openAlertDialog(alertTitle, randomTrinkzahl);
    }

    public void openAlertDialog(String alertTitle, int randomTrinkzahl) {
        List<String> list = new ArrayList<>();
        String buttonText = "";
        if (randomTrinkzahl > 0) {
            for (SpielerPferderennen spielernamen : filteredStrafe) {
                list.add(String.format("%s trinkt %d Schlucke", spielernamen.getName(), randomTrinkzahl));
            }
            buttonText = "Trink!";
        } else {
            for (SpielerPferderennen spielernamen : filteredWinner) {
                list.add(String.format("%s hat gewonnen, verteilt %d Schlucke", spielernamen.getName(), (spielernamen.getSchlucke() * 2)));
            }
            for (SpielerPferderennen spielernamen : filteredLoser) {
                list.add(String.format("%s hat verloren, trinkt %d Schluck/e", spielernamen.getName(), spielernamen.getSchlucke()));
            }
            buttonText = "Neustart";
        }
        String[] stringArray = list.toArray(new String[0]);

        builder.setCancelable(false)
                .setItems(stringArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                    }
                })
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (randomTrinkzahl > 0) {
                            dialog.cancel();
                        } else {
                            neustart();
                        }
                    }
                });

        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(alertTitle);
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.blue));
    }

    public void neustart() {
        i = -1;
        imageView.setImageResource(getResources().getIdentifier("back2", "drawable", getPackageName()));
        setCardStapel();
        position1 = 7;
        position2 = 7;
        position3 = 7;
        position4 = 7;
        loopChildren(lHerz, position1);
        loopChildren(lKreuz, position2);
        loopChildren(lKaro, position3);
        loopChildren(lPik, position4);
        verdeckeChildren(lVerdeckt);
        maxValue = 7;
        turnedArray = new boolean[]{false, false, false, false, false, false, false};
        alertTitle = "";
        randomTrinkzahl = 0;
        filteredWinner = new ArrayList<>();
        filteredLoser = new ArrayList<>();
        filteredStrafe = new ArrayList<>();
        showSpielerDialog();
    }

    public int changeImage(int i) {
        int resID = getResources().getIdentifier(randomStapel.get(i).getCard(), "drawable", getPackageName());
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