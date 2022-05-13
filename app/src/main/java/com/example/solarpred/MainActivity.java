package com.example.solarpred;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HIData;
import com.highsoft.highcharts.common.hichartsclasses.HILabels;
import com.highsoft.highcharts.common.hichartsclasses.HIMarker;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISpline;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.highsoft.highcharts.core.HIFunction;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HIChartView chartView = findViewById(R.id.hc);
        chartView.plugins = new ArrayList<>(Arrays.asList("series-label"));

        HIOptions options = new HIOptions();

        HITitle title = new HITitle();
        title.setText("Monthly Average Temperature");
        options.setTitle(title);

        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Source: WorldClimate.com");
        options.setSubtitle(subtitle);

        HIXAxis xAxis = new HIXAxis();
        String[] categoriesList = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        xAxis.setCategories(new ArrayList<>(Arrays.asList(categoriesList)));
        options.setXAxis(new ArrayList<HIXAxis>(){{add(xAxis);}});

        HIYAxis yAxis = new HIYAxis();
        yAxis.setTitle(new HITitle());
        yAxis.getTitle().setText("Temperature");
        yAxis.setLabels(new HILabels());
        yAxis.getLabels().setFormatter(new HIFunction(
                f -> f.getProperty("value") + "°",
                new String[] {"value"}
        ));
        options.setYAxis(new ArrayList<HIYAxis>(){{add(yAxis);}});

        HITooltip tooltip = new HITooltip();
        tooltip.setShared(true);
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setSpline(new HISpline());
        plotOptions.getSpline().setMarker(new HIMarker());
        plotOptions.getSpline().getMarker().setRadius(4);
        plotOptions.getSpline().getMarker().setLineColor(String.valueOf(HIColor.initWithHexValue("666666")));
        plotOptions.getSpline().getMarker().setLineWidth(1);
        options.setPlotOptions(plotOptions);

        HISpline series1 = new HISpline();
        series1.setName("Tokyo");
        series1.setMarker(new HIMarker());
        series1.getMarker().setSymbol("square");
        HIData data1 = new HIData();
        data1.setY(26.5);
        data1.setMarker(new HIMarker());
        data1.getMarker().setSymbol("url(https://www.highcharts.com/samples/graphics/sun.png)");

        Object[] series1_data = new Object[] { 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, data1, 23.3, 18.3, 13.9, 9.6 };
        series1.setData(new ArrayList<>(Arrays.asList(series1_data)));



        HISpline series2 = new HISpline();
        series2.setName("London");
        series2.setMarker(new HIMarker());
        series2.getMarker().setSymbol("diamond");
        HIData data2 = new HIData();
        data2.setY(3.9);
        data2.setMarker(new HIMarker());
        data2.getMarker().setSymbol("url(https://www.highcharts.com/samples/graphics/snow.png)");

        Object[] series2_data = new Object[] { data2, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8 };
        series2.setData(new ArrayList<>(Arrays.asList(series2_data)));

        options.setSeries(new ArrayList<>(Arrays.asList(series1, series2)));

        chartView.setOptions(options);
    }
}
