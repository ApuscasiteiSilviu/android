package com.uaic.fii.android.project.megaconverter.Units;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class MeasureUnit implements Serializable {

    public final static int CURRENCY = 1;
    public final static int LENGTH = 2;
    public final static int PRESSURE = 3;
    public final static int TEMPERATURE = 4;
    public final static int WEIGTH = 5;

    public Map<String, Double> values;
    public int unitType;

    public MeasureUnit(int unitType) {
        this.unitType = unitType;
        values = MeasureUnitMapFactory.getMeasureUnitMap(unitType);
    }



}
