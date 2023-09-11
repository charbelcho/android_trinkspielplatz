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

public class Spieler100Adapter extends ArrayAdapter<Spieler100> {

    private Context context;
    private List<Spieler100> spielerListe;

    public Spieler100Adapter(@NonNull Context context, ArrayList<Spieler100> spielerListe) {
        super(context, 0 , spielerListe);
        this.context = context;
        this.spielerListe = spielerListe;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        Spieler100 currentSpieler = spielerListe.get(position);

        TextView name = listItem.findViewById(R.id.list_child);
        name.setText(currentSpieler.getName());

        Button deleteButton = listItem.findViewById(R.id.openBearbeitenDialogButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSpielerBearbeitenDialog(position);
            }
        });

        return listItem;
    }

    public void openSpielerBearbeitenDialog(int position) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_spieler_100_bearbeiten);

        Spieler100 currentSpieler = spielerListe.get(position);

        EditText editName = dialog.findViewById(R.id.editTextName);
        editName.setText(currentSpieler.getName());


        Button saveButton = dialog.findViewById(R.id.saveSpieler100Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editName.getText().toString();

                currentSpieler.setName(newName);

                notifyDataSetChanged();
                dialog.cancel();
            }
        });

        Button deleteButton = dialog.findViewById(R.id.delete100Button);
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
