package com.uaic.fii.android.project.megaconverter.Units;

import com.uaic.fii.android.project.megaconverter.HTTP.CallAPI;
import com.uaic.fii.android.project.megaconverter.Logging.History;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.uaic.fii.android.project.megaconverter.Units.MeasureUnit.CURRENCY;
import static com.uaic.fii.android.project.megaconverter.Units.MeasureUnit.LENGTH;
import static com.uaic.fii.android.project.megaconverter.Units.MeasureUnit.PRESSURE;
import static com.uaic.fii.android.project.megaconverter.Units.MeasureUnit.TEMPERATURE;
import static com.uaic.fii.android.project.megaconverter.Units.MeasureUnit.WEIGTH;

public class MeasureUnitMapFactory {

    public static Map<String, Double> getMeasureUnitMap(int unitType){
        switch (unitType){
            case CURRENCY: {
                return getCurrencyMap();
            }
            case LENGTH: {
                return getLengthMap();
            }
            case PRESSURE: {
                return getPressureMap();
            }
            case TEMPERATURE: {
                return getTemperatureMap();
            }
            case WEIGTH: {
                return getWeigthMap();
            }
            default:{
                return null;
            }
        }
    }

    private static Map<String, Double> getCurrencyMap(){
        Map<String, Double> map = new LinkedHashMap<>();
        try {
            String toConvert = new CallAPI().execute("https://api.exchangeratesapi.io/latest").get();
            if(toConvert!=null){
                JSONObject jsonObj = new JSONObject(toConvert);
                if(jsonObj.has("base")){
                    map.put(jsonObj.get("base").toString(), 1.0);
                }
                if(jsonObj.has("rates")){
                    JSONObject rates = jsonObj.getJSONObject("rates");

                    if(rates.has("USD")){
                        map.put("USD", Double.parseDouble(rates.get("USD").toString()));
                    }
                    if(rates.has("CAD")){
                        map.put("CAD", Double.parseDouble(rates.get("CAD").toString()));
                    }
                    if(rates.has("RON")){
                        map.put("RON", Double.parseDouble(rates.get("RON").toString()));
                    }
                    if(rates.has("PLN")){
                        map.put("PLN", Double.parseDouble(rates.get("PLN").toString()));
                    }
                    if(rates.has("GBP")){
                        map.put("GBP", Double.parseDouble(rates.get("GBP").toString()));
                    }
                    if(rates.has("CZK")){
                        map.put("CZK", Double.parseDouble(rates.get("CZK").toString()));
                    }
                    if(rates.has("RUB")){
                        map.put("RUB", Double.parseDouble(rates.get("RUB").toString()));
                    }
                    if(rates.has("NZD")){
                        map.put("NZD", Double.parseDouble(rates.get("NZD").toString()));
                    }
                    if(rates.has("HKD")){
                        map.put("HKD", Double.parseDouble(rates.get("HKD").toString()));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    private static Map<String, Double> getLengthMap(){
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("(cm) Centimeters", 1.0);
        map.put("(dm) Decimeters",0.1);
        map.put("(ft) Feet", 0.0328084);
        map.put("(in) Inches", 0.3937008);
        map.put("(km) Kilometers", 0.00001);
        map.put("(m) Meters", 0.01);
        map.put("(mi) Miles", 0.000006213712);
        map.put("(mm) Millimeters", 10.0);
        map.put("(nmi) Nautical", 0.000005399568);
        return map;
    }

    private static Map<String, Double> getPressureMap(){
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("(b) Bar", 1.0);
        map.put("(atm) Atmosphere", 0.986923266716);
        map.put("(Pa) Pascal", 100000.0);
        map.put("(mmHg) Millimeter of mercury",750.0616827042);
        map.put("(psi) Pounds per square inch", 4.0);
        map.put("(psf) Pounds per square foot",2088.545632547);
        map.put("(torr) Torr", 750.0616827042);
        map.put("(hPa) Hectopascal", 1000.0);
        map.put("(kPa) Kilopascal", 100.0);
        return map;
    }

    private static Map<String, Double> getTemperatureMap(){
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("(C) Celsius", 1.0);
        map.put("(F) Fahrenheit", 33.8);
        map.put("(R) Rankine", 493.47);
        map.put("(C) Centigrade", 1.0);
        map.put("(K) Kelvin", 274.15);
        return map;
    }

    private static Map<String, Double> getWeigthMap(){
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("(kg) Kilograms", 1.0);
        map.put("(g) Grams", 1000.0);
        map.put("(mg) Milligrams", 1000000.0);
        map.put("(st) Stones", 0.157473);
        map.put("(t) Metric tons", 0.001);
        map.put("(lb) Pounds", 2.204623);
        map.put("(oz) Ounces", 35.27396);
        return map;
    }
}
