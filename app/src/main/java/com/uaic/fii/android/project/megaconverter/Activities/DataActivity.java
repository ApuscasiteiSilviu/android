package com.uaic.fii.android.project.megaconverter.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.uaic.fii.android.project.megaconverter.Logging.History;
import com.uaic.fii.android.project.megaconverter.R;

public class DataActivity extends AppCompatActivity {

    public static int HISTORY_CONVERSION = 1;
    public static int HISTORY_RATES = 2;

    ListView dataList;
    ArrayAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        dataList = findViewById(R.id.dataList);
        int listFlag = getIntent().getIntExtra("listFlag", 1);
        if(listFlag == HISTORY_CONVERSION){
            listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, History.getListOfRates());
        }else if(listFlag == HISTORY_RATES){
            listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, History.getListOfConvertedValues());

        }
        dataList.setAdapter(listAdapter);
    }
}
