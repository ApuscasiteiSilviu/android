package com.uaic.fii.android.project.megaconverter.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uaic.fii.android.project.megaconverter.Logging.History;
import com.uaic.fii.android.project.megaconverter.R;
import com.uaic.fii.android.project.megaconverter.Units.MeasureUnit;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertorActivity extends AppCompatActivity {

    private static class UnitViewsHolder {
        List<EditText> editTextList;
        List<TextView> textViewList;
        Button convertButton;
        Integer lastModifiedEditTextPosition;
        Integer numberOfElements;
        List<Double> values;
    }

    private Map<String, Double> initalMapValues;
    private Boolean lockModifications = false;
    UnitViewsHolder unitViewsHolder;

    private void init(){
        unitViewsHolder = new UnitViewsHolder();
        unitViewsHolder.convertButton = findViewById(R.id.convertButton);
        unitViewsHolder.editTextList = new ArrayList<>();
        unitViewsHolder.textViewList = new ArrayList<>();
        unitViewsHolder.editTextList.addAll(
                Arrays.asList(
                        (EditText)findViewById(R.id.et1),
                        (EditText)findViewById(R.id.et2),
                        (EditText)findViewById(R.id.et3),
                        (EditText)findViewById(R.id.et4),
                        (EditText)findViewById(R.id.et5),
                        (EditText)findViewById(R.id.et6),
                        (EditText)findViewById(R.id.et7),
                        (EditText)findViewById(R.id.et8),
                        (EditText)findViewById(R.id.et9)
                ));
        unitViewsHolder.textViewList.addAll(
                Arrays.asList(
                        (TextView)findViewById(R.id.tv1),
                        (TextView)findViewById(R.id.tv2),
                        (TextView)findViewById(R.id.tv3),
                        (TextView)findViewById(R.id.tv4),
                        (TextView)findViewById(R.id.tv5),
                        (TextView)findViewById(R.id.tv6),
                        (TextView)findViewById(R.id.tv7),
                        (TextView)findViewById(R.id.tv8),
                        (TextView)findViewById(R.id.tv9)
                ));
    }

    private View.OnClickListener getConvertClickListener(final int unitType){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockModifications = true;
                if(unitType == MeasureUnit.TEMPERATURE){
                    computeFieldsTemperature();
                }else {
                    computeFieldsNonTemperature();
                }
                lockModifications = false;
            }
        };
    }

    private void computeFieldsNonTemperature() {
        Double valueToConvert = Double.parseDouble(
                unitViewsHolder.editTextList
                        .get(unitViewsHolder.lastModifiedEditTextPosition)
                        .getText().toString());
        List<Double> initValues = new ArrayList<>(initalMapValues.values());
        unitViewsHolder.values.set(0, valueToConvert * (initValues.get(0) / initValues.get(unitViewsHolder.lastModifiedEditTextPosition)));

        Double base = unitViewsHolder.values.get(0);
        
        for(int i=1; i<unitViewsHolder.numberOfElements; i++){
            if(i!=unitViewsHolder.lastModifiedEditTextPosition){
                unitViewsHolder.values.set(i, initValues.get(i) * base);
            }else{
                unitViewsHolder.values.set(i, valueToConvert);
            }
        }
        rewriteEditTexts();
    }

    private void computeFieldsTemperature() {
        Double valueToConvert = Double.parseDouble(
                unitViewsHolder.editTextList
                        .get(unitViewsHolder.lastModifiedEditTextPosition)
                        .getText().toString());
        List<Double> initValues = new ArrayList<>(initalMapValues.values());
        switch (unitViewsHolder.lastModifiedEditTextPosition){
            case 0:{
                unitViewsHolder.values.set(0, valueToConvert * initValues.get(0));
                break;
            }
            case 1:{
                unitViewsHolder.values.set(0, (valueToConvert - 32) / 1.8);
                break;
            }
            case 2:{
                unitViewsHolder.values.set(0, valueToConvert / 1.8 - 273.15);
                break;
            }
            case 3:{
                unitViewsHolder.values.set(0, valueToConvert * initValues.get(0));
                break;
            }
            case 4:{
                unitViewsHolder.values.set(0, valueToConvert - 273);
                break;
            }
        }
        Double base = unitViewsHolder.values.get(0);
        unitViewsHolder.values.set(1, (base) * 1.8 + 32);
        unitViewsHolder.values.set(2, (base + 273.15) * 1.8);
        unitViewsHolder.values.set(3, base);
        unitViewsHolder.values.set(4, (base) + 273);
        unitViewsHolder.values.set(unitViewsHolder.lastModifiedEditTextPosition, valueToConvert);
        rewriteEditTexts();
    }

    private void rewriteEditTexts(){
        Map<String, String> toHistory = new HashMap<>();
        if(unitViewsHolder.lastModifiedEditTextPosition!=null)
            toHistory.put("BASE UNIT", unitViewsHolder.textViewList.get(unitViewsHolder.lastModifiedEditTextPosition).getText().toString());
        for(int i=0; i<unitViewsHolder.numberOfElements; i++){
            unitViewsHolder.editTextList.get(i).setText(unitViewsHolder.values.get(i).toString());
            toHistory.put(unitViewsHolder.textViewList.get(i).getText().toString(), unitViewsHolder.editTextList.get(i).getText().toString());
        }
        History.addElementToListOfConvertedValues(new JSONObject(toHistory).toString());
    }

    private void rewriteTextViews(List<String> keys){
        for(int i=0; i<unitViewsHolder.numberOfElements; i++){
            unitViewsHolder.textViewList.get(i).setText(keys.get(i));
        }
    }

    private void hideUnwantedEditTexts(){
        for(int i=unitViewsHolder.numberOfElements; i<unitViewsHolder.editTextList.size(); i++){
            unitViewsHolder.textViewList.get(i).setVisibility(View.INVISIBLE);
            unitViewsHolder.editTextList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    private TextWatcher getEditTextChangeListener(final int position) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!lockModifications)
                    unitViewsHolder.lastModifiedEditTextPosition = position;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void setOnTextChangeListeners(int unitType){
        unitViewsHolder.convertButton.setOnClickListener(getConvertClickListener(unitType));
        int size = unitViewsHolder.editTextList.size();
        for(int i=0; i<size; i++){
            unitViewsHolder.editTextList.get(i).addTextChangedListener(getEditTextChangeListener(i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertor);
        init();

        Intent intent = getIntent();
        MeasureUnit measureUnit = (MeasureUnit) intent.getSerializableExtra("MeasureUnit");
        initalMapValues = measureUnit.values;

        List<String> keys = new ArrayList<>(initalMapValues.keySet());
        unitViewsHolder.values = new ArrayList<>(initalMapValues.values());
        unitViewsHolder.numberOfElements = Math.min(unitViewsHolder.editTextList.size(), measureUnit.values.size());

        rewriteTextViews(keys);
        rewriteEditTexts();
        hideUnwantedEditTexts();
        setOnTextChangeListeners(measureUnit.unitType);

    }
}
