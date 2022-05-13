package com.example.solarpred;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class GraphYAxisValueFormatter implements IAxisValueFormatter {
    private String[] aod;

    // 생성자 초기화
    GraphYAxisValueFormatter(String[] values) {
        this.aod = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        return aod[(int) value];
    }
}
