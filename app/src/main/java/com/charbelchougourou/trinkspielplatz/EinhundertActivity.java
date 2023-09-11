package com.charbelchougourou.trinkspielplatz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

public class EinhundertActivity extends AppCompatActivity {
    private AdView adView;
    private Button speichernButton;
    private CountDownTimer timer = null;
    private final Handler handler = new Handler();
    private EditText editName;
    private ArrayAdapter<Spieler100> adapter;
    private final ArrayList<Spieler100> spieler = new ArrayList<>();
    private AlertDialog.Builder builder;
    private TextView textViewSpielerAmZug;
    private TextView textViewSpielerPunkte;
    private TextView textViewSpielerPunkteAktuell;
    private TextView textViewStrafe;
    private ImageView imageViewWuerfel;
    final int[] aktuellePunkte = {0};
    private int wuerfelValue;

    private int randomTrinkzahl = 0;
    private int i = 0;
    private List<Spieler100> filteredLoser  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einhundert);

        showSpielerDialog();
        initViews();
        wuerfeln();
        speichern();
        initToolbars();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
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
        toolbar.setTitle(R.string.btn_einhundert);
    }

    public void initViews() {
        speichernButton = findViewById(R.id.saveButton100);

        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

        textViewSpielerAmZug = findViewById(R.id.textViewSpielerAmZug);
        textViewSpielerPunkte = findViewById(R.id.textViewSpielerPunkte);
        textViewSpielerPunkteAktuell = findViewById(R.id.textViewSpielerPunkteAktuell);
        textViewStrafe = findViewById(R.id.textViewStrafe);
        imageViewWuerfel = findViewById(R.id.imageViewWuerfelMaexchen1);
    }

    public void setSpieler(int position) {
        String punkte = String.valueOf(spieler.get(position).getPunkte());
        textViewSpielerAmZug.setText(spieler.get(position).getName());
        textViewSpielerPunkte.setText(punkte);
        textViewSpielerPunkteAktuell.setText(punkte);
        if (spieler.size() > 0) {
            aktuellePunkte[0] = spieler.get(position).getPunkte();
        }
    }

    public void next() {
        if (i < spieler.size() - 1) {
            i++;
        }
        else {
            i = 0;
        }
        setSpieler(i);
    }

    public void wuerfeln() {
        imageViewWuerfel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                speichernButton.setVisibility(View.INVISIBLE);
                textViewStrafe.setText("");
                timer = new CountDownTimer(2999, 50) {
                    @Override
                    public void onTick(long l) {
                        wuerfelValue = new Random().nextInt(6);
                        wuerfelValue += 1;
                        imageViewWuerfel.setImageResource(changeImage(wuerfelValue));
                    }
                    @Override
                    public void onFinish() {
                        wuerfelValue = new Random().nextInt(6);
                        wuerfelValue += 1;
                        imageViewWuerfel.setImageResource(changeImage(wuerfelValue));
                        if (wuerfelValue != 6) {
                            aktuellePunkte[0] += wuerfelValue;
                        }
                        randomTrinkzahl = new Random().nextInt(6);
                        randomTrinkzahl += 1;

                        String punkte = String.valueOf(aktuellePunkte[0]);
                        Runnable timeoutRunnable = new Runnable() {
                            @Override
                            public void run() {
                                speichernButton.setVisibility(View.VISIBLE);
                                if (wuerfelValue == 1) {
                                    textViewStrafe.setText(String.format(Locale.GERMANY, "Alle außer dir trinken %d Schluck/e", randomTrinkzahl));
                                } else if (wuerfelValue == 6) {
                                    textViewStrafe.setText(String.format(Locale.GERMANY, "Du (%s) trinkst %d Schluck/e", spieler.get(i).getName(), randomTrinkzahl));
                                    if (spieler.size() > 0) {
                                        spieler.get(i).setPunkte(spieler.get(i).getPunkte());
                                    }
                                    imageViewWuerfel.setEnabled(false);
                                }

                                if (aktuellePunkte[0] >= 100) {
                                    if (spieler.size() > 0) {
                                        spieler.get(i).setPunkte(spieler.get(i).getPunkte() + aktuellePunkte[0]);
                                    }
                                    randomTrinkzahl = new Random().nextInt(6);
                                    randomTrinkzahl += 1;
                                    openAlertDialog(randomTrinkzahl);
                                }
                                speichernButton.setText(wuerfelValue == 6 ? R.string.naechster_spieler : R.string.save);
                                textViewSpielerPunkteAktuell.setText(punkte);
                            }
                        };
                        handler.postDelayed(timeoutRunnable, 500);
                    }
                };
                timer.start();

                RotateAnimation rotateAnimation = new RotateAnimation(
                        0,  // Start angle (in degrees)
                        1080,  // End angle (in degrees)
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
                imageViewWuerfel.startAnimation(animationSet);

            }
        });
    }

    public void speichern() {
        speichernButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (spieler.size() > 0) {
                    spieler.get(i).setPunkte(spieler.get(i).getPunkte() + aktuellePunkte[0]);
                    next();
                    textViewStrafe.setText("");
                    speichernButton.setVisibility(View.INVISIBLE);
                    imageViewWuerfel.setEnabled(true);
                }
            }
        });
    }

    public void showSpielerDialog() {
        final Dialog dialog = new Dialog(this,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_spieler_100);

        ListView spielerListe = dialog.findViewById(R.id.spielerList100);

        adapter = new Spieler100Adapter(this, spieler);
        spielerListe.setAdapter(adapter);

        editName = dialog.findViewById(R.id.editTextName);
        Button addSpielerButton = dialog.findViewById(R.id.saveSpieler100Button);
        Button playButton = dialog.findViewById(R.id.openBearbeitenDialogButton);

        addSpielerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();

                if (editName.getText().toString().length() == 0) {
                    Toast.makeText(EinhundertActivity.this,
                            "Gib einen Namen ein", Toast.LENGTH_LONG).show();
                }
                else {
                    adapter.add(new Spieler100(name));
                    editName.setText("");
                }
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spieler.size() >= 2) {
                    setSpieler(0);
                    dialog.cancel();
                } else {
                    Toast.makeText(EinhundertActivity.this,
                            "Füge min. 2 Spieler hinzu", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    public void openAlertDialog(int randomTrinkzahl) {
        List<String> list = new ArrayList<>();
        String buttonText = "";

        filteredLoser = spieler.stream().filter(s -> s.getPunkte() < 100).collect(Collectors.toList());
        for (Spieler100 spielernamen : filteredLoser) {
            list.add(String.format(Locale.GERMANY, "%s hat verloren, trinkt %d Schluck/e", spielernamen.getName(), randomTrinkzahl));
        }
        buttonText = "Neustart";

        String[] stringArray = list.toArray(new String[0]);

        builder.setCancelable(false)
                .setItems(stringArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                    }
                })
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        neustart();
                    }
                });

        AlertDialog alert = builder.create();
        //Setting the title manually
        if (spieler.size() > 0) {
            alert.setTitle(String.format("%s hat gewonnen!", spieler.get(i).getName()));
        }
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.blue));
    }

    public void neustart() {
        i = 0;
        aktuellePunkte[0] = 0;
        filteredLoser = new ArrayList<>();
        for (int i = 0; i < spieler.size(); i++) {
            spieler.get(i).setPunkte(0);
        }
        if (spieler.size() > 0) {
            setSpieler(0);
        }
        textViewStrafe.setText("");
        speichernButton.setVisibility(View.INVISIBLE);
        showSpielerDialog();
    }

    public int changeImage(int value) {
        return getResources().getIdentifier(String.format(Locale.GERMANY, "wuerfel_%d_black", value), "drawable", getPackageName());
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