package com.example.solarpred;

import static android.graphics.Color.WHITE;
import static android.graphics.Color.blue;
import static android.graphics.Color.valueOf;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
    public Fragment1() { // Required empty public constructor
    }

    private LineChart lineChart;
    private Thread thread;
    double sampleDate[] = new double[]{1.2f,2.4f,2.8f,3.6f,6.6f,8.8f};
    float[] fs;
    private WebView webvw;
    private WebSettings webSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment1, container, false);
        TextView tvDate = (TextView) v.findViewById(R.id.tvAOD);

        tvDate.setText("Today");

        ArrayList<Entry> entry_chart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> entry_chart2 = new ArrayList<>();

        lineChart = v.findViewById(R.id.Chart);
        webvw = v.findViewById(R.id.webvw);

        webvw.setWebViewClient(new WebViewClient());

        webSet = webvw.getSettings();
        webSet.setJavaScriptEnabled(true);

        webvw.loadUrl("http://119.200.31.177:9090/solarpred/");
/*
        lineChart.getDescription().setEnabled(true);
        Description des = lineChart.getDescription();
        des.setEnabled(true);
        des.setText("Real-Time DATA");
        des.setTextSize(10f);
        des.setTextColor(Color.WHITE);

        // touch gestures (false-비활성화)
        lineChart.setTouchEnabled(false);

// scaling and dragging (false-비활성화)
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);

//auto scale
        lineChart.setAutoScaleMinMaxEnabled(true);

// if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);


        //Legend
        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setTextSize(12f);
        l.setTextColor(Color.WHITE);
//X축
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(WHITE);
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(true);

      //  String sysTime[] = new String[]{"22", "14", "21", "56"};
        xAxis.setSpaceMax(60f);
        xAxis.setSpaceMin(2f);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
       // GraphAxisValueFormatter formatter = new GraphAxisValueFormatter(sysTime);
       // xAxis.setValueFormatter(formatter);
        xAxis.setValueFormatter(new MyFormatter());

//Y축
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(getResources().getColor(R.color.black));
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(getResources().getColor(R.color.black));

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        lineChart.setVisibleXRange(5,5);

        LineData data = new LineData();
        lineChart.setData(data);
    //    addEntry(sampleDate[0]);
        lineChart.invalidate();

        feedMultiple();


/*
        LineData chartData = new LineData(); // 차트에 담길 데이터

        entry_chart1.add(new Entry(1, 1)); //entry_chart1에 좌표 데이터를 담는다.
        entry_chart1.add(new Entry(2, 2));
        entry_chart1.add(new Entry(3, 3));
        entry_chart1.add(new Entry(4, 4));
        entry_chart1.add(new Entry(5, 2));
        entry_chart1.add(new Entry(6, 8));

        entry_chart2.add(new Entry(1, 2)); //entry_chart2에 좌표 데이터를 담는다.
        entry_chart2.add(new Entry(2, 3));
        entry_chart2.add(new Entry(3, 1));
        entry_chart2.add(new Entry(4, 4));
        entry_chart2.add(new Entry(5, 5));
        entry_chart2.add(new Entry(6, 7));


        LineDataSet lineDataSet1 = new LineDataSet(entry_chart1, "LineGraph1"); // 데이터가 담긴 Arraylist 를 LineDataSet 으로 변환한다.
        LineDataSet lineDataSet2 = new LineDataSet(entry_chart2, "LineGraph2");


        lineDataSet1.setColor(Color.RED); // 해당 LineDataSet의 색 설정 :: 각 Line 과 관련된 세팅은 여기서 설정한다.
        lineDataSet2.setColor(Color.BLACK);


        chartData.addDataSet(lineDataSet1); // 해당 LineDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.
        chartData.addDataSet(lineDataSet2);

        lineChart.setData(chartData); // 차트에 위의 DataSet을 넣는다.

        lineChart.invalidate(); // 차트 업데이트
        lineChart.setTouchEnabled(false); // 차트 터치 disable


        /*long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dayNow = new SimpleDateFormat("yy.MM.dd");
        String ndate = dayNow.format(date);
        tvDate.setText(ndate);
*/
        return v;
    }

//    private void addEntry(double num) {
//
//        LineData data = lineChart.getData();
//
//        if (data == null) {
//            data = new LineData();
//            lineChart.setData(data);
//        }
//
//        ILineDataSet set = data.getDataSetByIndex(0);
//
//        if (set == null) {
//            set = createSet();
//            data.addDataSet(set);
//        }
//
//        data.addEntry(new Entry((float) set.getEntryCount(), (float) num), 0);
//        data.notifyDataChanged();
//
//        // let the chart know it's data has changed
//        lineChart.notifyDataSetChanged();
//
//        lineChart.setVisibleXRangeMaximum(150);
//        // this automatically refreshes the chart (calls invalidate())
//        lineChart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);
//
//    }
/*
    private void addEntry(){
        LineData data = lineChart.getData();
        if(data != null){
            ILineDataSet set = data.getDataSetByIndex(0);

            if(set == null){
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(),(float) (Math.random()*40)+30f),0);
            data.notifyDataChanged();

            lineChart.notifyDataSetChanged();
            lineChart.setVisibleXRangeMaximum(6);

            lineChart.moveViewToX(data.getEntryCount());

        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Real-time Line Data");
        set.setLineWidth(1f);
        set.setDrawValues(false);
        set.setValueTextColor(getResources().getColor(android.R.color.black));
        set.setColor(getResources().getColor(android.R.color.holo_blue_light));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawCircles(false);
        set.setHighLightColor(Color.rgb(190, 190, 190));

        return set;
    }

    private void feedMultiple() {
        if (thread != null) thread.interrupt();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addEntry();
            }
        };
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    getActivity().runOnUiThread(runnable);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (thread != null) thread.interrupt();
    }
*/
}





