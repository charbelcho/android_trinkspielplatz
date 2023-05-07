package com.charbelchougourou.trinkspielplatz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.PopupMenu;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnleitungActivity extends AppCompatActivity {
    private AdView adView;
    private ExpandableListView expendableListView;
    private List<String> listGroup;
    private HashMap<String,List<String>> listItem;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anleitung);
        initToolbars();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        expendableListView = findViewById(R.id.expendable_listView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this, listGroup, listItem);
        expendableListView.setAdapter(adapter);
        initListData();
    }

    private void initListData() {
        listGroup.add(getString(R.string.nochnie));
        listGroup.add(getString(R.string.eher));
        listGroup.add(getString(R.string.wahrheit_pflicht));
        listGroup.add(getString(R.string.hoeher_tiefer));
        listGroup.add(getString(R.string.bang));

        String [] array;

        List<String> list = new ArrayList<>();
        array = getResources().getStringArray(R.array.nochnie);
        for (String item : array) {
            list.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.eher);
        for (String item : array) {
            list2.add(item);
        }

        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.wahrheit_pflicht);
        for (String item : array) {
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.hoeher_tiefer);
        for (String item : array) {
            list4.add(item);
        }

        List<String> list5 = new ArrayList<>();
        array = getResources().getStringArray(R.array.wahrheit_pflicht);
        for (String item : array) {
            list5.add(item);
        }

        listItem.put(listGroup.get(0),list);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        listItem.put(listGroup.get(4),list5);
        adapter.notifyDataSetChanged();
    }

    private void initToolbars() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.anleitungen);
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