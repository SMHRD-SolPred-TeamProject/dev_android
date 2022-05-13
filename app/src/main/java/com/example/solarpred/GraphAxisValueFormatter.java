package com.example.solarpred;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class GraphAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
    private String[] sysTime;
    GraphAxisValueFormatter(String[] values){
        this.sysTime = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis){

        return sysTime[(int) value];
    }
}