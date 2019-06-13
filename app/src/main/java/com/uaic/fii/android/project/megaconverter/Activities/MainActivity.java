package com.uaic.fii.android.project.megaconverter.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;


import com.google.gson.reflect.TypeToken;
import com.uaic.fii.android.project.megaconverter.Logging.History;
import com.uaic.fii.android.project.megaconverter.R;
import com.uaic.fii.android.project.megaconverter.Units.MeasureUnit;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_TAG = "SharedPrefs";
    private static final String RATES = "Rates";
    private static final String HISTORY = "History";

    private Toolbar toolbar;

    private List<String> getDataFromSharedPreferences(String TAG){
        Gson gson = new Gson();
        List<String> productFromShared = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(TAG, "");
        Type type = new TypeToken<List<String>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);
        return productFromShared;
    }

    private void setDataFromSharedPreferences(String TAG, List<String> curProduct){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(curProduct);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TAG, jsonCurProduct);
        editor.commit();
    }

    Map<String, Integer> measureUnitMap;
    List<String> unitListAdapterStrings;
    ListView unitsList;
    ArrayAdapter<String> unitsListAdapter;

    private void init(){
        unitsList = findViewById(R.id.unitsList);
        unitListAdapterStrings = new ArrayList<>();
        measureUnitMap = new HashMap<>();
        measureUnitMap.put("Currency", MeasureUnit.CURRENCY);
        measureUnitMap.put("Length", MeasureUnit.LENGTH);
        measureUnitMap.put("Pressure", MeasureUnit.PRESSURE);
        measureUnitMap.put("Temperature", MeasureUnit.TEMPERATURE);
        measureUnitMap.put("Weight", MeasureUnit.WEIGTH);
    }

    private AdapterView.OnItemClickListener unitsListItemClickListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startConvertorActivity(new MeasureUnit(measureUnitMap.get(unitListAdapterStrings.get(position))));
            }
        };
    }

    private void startConvertorActivity(MeasureUnit measureUnit){
        Intent intent = new Intent(this, ConvertorActivity.class);
        intent.putExtra("MeasureUnit", (Serializable) measureUnit);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_history) {
            startDataActivity(DataActivity.HISTORY_CONVERSION);
            return true;
        }else if(id == R.id.action_rates){
            startDataActivity(DataActivity.HISTORY_RATES);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        History.setListOfRates(getDataFromSharedPreferences(RATES));
        History.setListOfConvertedValues(getDataFromSharedPreferences(HISTORY));
        unitListAdapterStrings.addAll(measureUnitMap.keySet());
        unitsListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, unitListAdapterStrings);
        unitsList.setAdapter(unitsListAdapter);
        unitsList.setOnItemClickListener(unitsListItemClickListener());
    }

    private void startDataActivity(int listFlag){
        Intent intent = new Intent(this, DataActivity.class);
        intent.putExtra("listFlag", listFlag);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        setDataFromSharedPreferences(RATES, History.getListOfRates());
        setDataFromSharedPreferences(HISTORY, History.getListOfConvertedValues());
        super.onPause();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

}
