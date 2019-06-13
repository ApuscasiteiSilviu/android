package com.uaic.fii.android.project.megaconverter.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class History {
    private static List<String> listOfRates = new ArrayList<>();
    private static List<String> listOfConvertedValues = new ArrayList<>();

    public static void addElementToListOfRates(String element){
        if(listOfRates == null){
            listOfRates = new ArrayList<>();
        }
        listOfRates.add(element);
    }

    public static void addElementToListOfConvertedValues(String element){
        if(listOfConvertedValues == null){
            listOfConvertedValues = new ArrayList<>();
        }
        listOfConvertedValues.add(element);
    }

    public static List<String> getListOfRates() {
        return listOfRates;
    }

    public static void setListOfRates(List<String> listOfRates) {
        History.listOfRates = listOfRates;
    }

    public static List<String> getListOfConvertedValues() {
        return listOfConvertedValues;
    }

    public static void setListOfConvertedValues(List<String> listOfConvertedValues) {
        History.listOfConvertedValues = listOfConvertedValues;
    }
}
