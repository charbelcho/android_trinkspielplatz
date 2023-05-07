package com.charbelchougourou.trinkspielplatz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        initToolbars();

        listView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> nochnieList = databaseAccess.getNochnieList();
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nochnieList);
        listView.setAdapter(adapter);

        Button button = findViewById(R.id.btnAddQuestion);
        button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  addQuestion();
              }
        });

        EditText searchText = findViewById(R.id.editTextSearch);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initToolbars() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent = new Intent(this, NochnieActivity.class);
        Intent intent2 = new Intent(this, EherActivity.class);
        Intent intent3 = new Intent(this, WahrheitPflichtActivity.class);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(AddQuestionActivity.this, view);
                popup.inflate(R.menu.popup_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.ich_hab_noch_nie:
                                startActivity(intent);
                                return true;
                            case R.id.wer_wuerde_eher:
                                startActivity(intent2);
                                return true;
                            case R.id.wahrheit_pflicht:
                                startActivity(intent3);
                                return true;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.anleitungen:
                Intent intent = new Intent(this, NochnieActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addQuestion() {
        EditText searchText = findViewById(R.id.editTextSearch);
        if (searchText.getText().toString() != "") {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            //databaseAccess.insertNochnie(searchText.getText().toString());
            databaseAccess.close();
            searchText.setText(null);
            Toast toast = Toast.makeText(getApplicationContext(), "Input added successfully", Toast.LENGTH_LONG);
            toast.show();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Input was empty", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}