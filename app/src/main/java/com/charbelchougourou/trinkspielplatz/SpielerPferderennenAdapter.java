package com.charbelchougourou.trinkspielplatz;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SpielerPferderennenAdapter extends ArrayAdapter<SpielerPferderennen> {

    private Context context;
    private List<SpielerPferderennen> spielerListe;

    public SpielerPferderennenAdapter(@NonNull Context context, ArrayList<SpielerPferderennen> spielerListe) {
        super(context, 0 , spielerListe);
        this.context = context;
        this.spielerListe = spielerListe;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        SpielerPferderennen currentSpieler = spielerListe.get(position);

        TextView name = listItem.findViewById(R.id.list_child);
        name.setText(String.format("%s wettet %d Schluck/e auf %s", currentSpieler.getName(), currentSpieler.getSchlucke(), currentSpieler.getZeichen()));

        Button deleteButton = listItem.findViewById(R.id.openBearbeitenDialogButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSpielerBearbeitenDialog(position);
            }
        });


//        int selectedId = zeichenWahl.getCheckedRadioButtonId();
//        if (selectedId != -1){
//            zeichenGewaehlt = listItem.findViewById(selectedId);
//            zeichenGewaehlt.setText(currentSpieler.getZeichen());
//        }

        return listItem;
    }

    public void openSpielerBearbeitenDialog(int position) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_spieler_pferderennen_bearbeiten);

        SpielerPferderennen currentSpieler = spielerListe.get(position);

        EditText editName = dialog.findViewById(R.id.editTextName);
        editName.setText(currentSpieler.getName());
        EditText editSchlucke = dialog.findViewById(R.id.editTextSchlucke);
        editSchlucke.setText(String.valueOf(currentSpieler.getSchlucke()));
        RadioGroup zeichenWahl = dialog.findViewById(R.id.zeichenWahlB);

        switch(currentSpieler.getZeichen()) {
            case "Herz":
                zeichenWahl.check(R.id.radioButtonB);
                break;
            case "Kreuz":
                zeichenWahl.check(R.id.radioButton2B);
                break;
            case "Karo":
                zeichenWahl.check(R.id.radioButton3B);
                break;
            case "Pik":
                zeichenWahl.check(R.id.radioButton4B);
                break;
            default:break;
        }

        Button saveButton = dialog.findViewById(R.id.saveSpielerPferderennenButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editName.getText().toString();
                int newSchlucke = Integer.parseInt(editSchlucke.getText().toString());
                int selectedId = zeichenWahl.getCheckedRadioButtonId();
                if (selectedId != -1){
                    RadioButton zeichenGewaehlt = dialog.findViewById(selectedId);
                    String zeichenText = zeichenGewaehlt.getText().toString();
                    currentSpieler.setName(newName);
                    currentSpieler.setSchlucke(newSchlucke);
                    currentSpieler.setZeichen(zeichenText);
                }
                notifyDataSetChanged();
                dialog.cancel();
            }
        });

        Button deleteButton = dialog.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spielerListe.remove(position);
                notifyDataSetChanged();
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
