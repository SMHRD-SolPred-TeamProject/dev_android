package com.example.solarpred;

import static android.graphics.Color.WHITE;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fragment2 extends Fragment {
    TextView tvDate;
    public Fragment2() {
    }

    private LineChart lineChart;
    private Thread thread;
    RequestQueue queue;
    StringRequest request;
    TextView tvMerge, tvMergeKWh,tvPercent;
    tHandler handler = new tHandler();
    ProgressBar progressAOD;
    int nowSec = 0;
    Double Predaod =0.0 ;
    Double PreTotal = 0.0;
    Double PreTotalAOD = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, container, false);
        TextView tvDate = (TextView) v.findViewById(R.id.tvAOD);
        tvPercent = v.findViewById(R.id.tvPercent);
        progressAOD = v.findViewById(R.id.progressAOD);
        progressAOD.setIndeterminate(false);


        tvDate.setText("+1 Hour");

        ArrayList<Entry> entry_chart1 = new ArrayList<>();
        ArrayList<Entry> entry_chart2 = new ArrayList<>();

        lineChart = v.findViewById(R.id.Chart);
        lineChart.getDescription().setEnabled(true);
        Description des = lineChart.getDescription();
        des.setEnabled(true);
        des.setText("Real-Time DATA");
        des.setTextSize(10f);
        des.setTextColor(Color.WHITE);

        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.setPinchZoom(false);

        //Legend
        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        l.setFormSize(10f);
        l.setTextSize(12f);
        l.setTextColor(Color.WHITE);
//X축
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(android.R.color.transparent);
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(true);
        xAxis.setSpaceMax(60f);
        xAxis.setSpaceMin(2f);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new MyFormatter());
        xAxis.setSpaceMin(1f);
        xAxis.setSpaceMax(1f);
      //  xAxis.setValueFormatter(new IndexAxisValueFormatter());

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
        addEntry(Predaod);
        lineChart.invalidate();

        feedMultiple();

        tvMerge = (TextView) v.findViewById(R.id.tvMerge);
        tvMergeKWh =(TextView) v.findViewById(R.id.tvMergeKWh);

        tThread threads = new tThread();
        threads.start();

        return v;

    }

    private void addEntry(double num) {

        LineData data = lineChart.getData();

        if (data == null) {
            data = new LineData();
            lineChart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        data.addEntry(new Entry((float) set.getEntryCount(), (float) num), 0);
        data.notifyDataChanged();

        lineChart.notifyDataSetChanged();
        lineChart.setVisibleXRangeMaximum(6);
        lineChart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);

    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Real-time Line Data");
        set.setFillAlpha(110);
        set.setFillColor(Color.parseColor("#d7e7fa"));
        set.setColor(Color.parseColor("#0B80C9"));
        set.setCircleColor(Color.parseColor("#FFA1B4DC"));
        set.setCircleHoleColor(Color.BLUE);
        set.setValueTextColor(Color.WHITE);
        set.setDrawValues(false);
        set.setLineWidth(2);
        set.setCircleRadius(6);
        set.setDrawCircleHole(false);
        set.setDrawCircles(false);
        set.setValueTextSize(9f);
        set.setDrawFilled(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setHighLightColor(Color.rgb(244, 117, 117));

        return set;
    }

    private void feedMultiple() {
        if (thread != null) thread.interrupt();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addEntry(Predaod);
            }
        };
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(runnable);
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
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

    class tThread extends Thread{
        @Override
        public void run() {
            while(true) {

                if (getActivity() != null) {
                    queue = Volley.newRequestQueue(getActivity().getApplicationContext());

                    int method = Request.Method.GET;
                    String url = "http://13.125.104.63:8080/api/pre";
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    request = new StringRequest(
                            method,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    List<Prediction> predList = new ArrayList<>();

                                    try {
                                        JSONObject object = new JSONObject(response);

                                        JSONArray pre = object.getJSONArray("pre");
                                        JSONObject pObj = pre.getJSONObject(1);
                                        Prediction p = new Prediction();
                                        JSONObject tObj = pre.getJSONObject(0);
                                        p.setPred_aod(Double.parseDouble(tObj.getString("predAOD")));
                                        predList.add(p);

                                        PreTotal = Double.parseDouble(pObj.getString("total"));


                                        //Handler에 값을 전달 -> Message 객체
                                        Message msg = handler.obtainMessage();
                                        Bundle bundle = new Bundle();
                                        bundle.putDouble("predAOD", predList.get(0).getPred_aod());
                                        bundle.putDouble("total", PreTotal);
                                        msg.setData(bundle);

                                        handler.sendMessage(msg);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "요청 실패>> " + error.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                    queue.add(request);
                }
            }
        }
    }// tThread end

    class tHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            Predaod = bundle.getDouble("predAOD");
            PreTotalAOD = bundle.getDouble("total");
            String preResult= String.format("%.1f",PreTotalAOD);
            tvMergeKWh.setText(preResult+"kW");

            Long sysTime = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
            String sysDate = formatter.format(sysTime);
            Calendar cal = Calendar.getInstance();
            try {
                Date date = formatter.parse(sysDate);
                cal.setTime(date);
                int hour = cal.get(Calendar.HOUR_OF_DAY)*3600;
                int minute = cal.get(Calendar.MINUTE)*60;
                int second = cal.get(Calendar.SECOND);
                nowSec = hour+minute+second;

            } catch (ParseException e) {
                e.printStackTrace();
            }

            int nowTime = nowSec-14400;
            double percent = (double) (nowTime*100)/43200;

            String percentS = String.format("%.1f",percent);
            Double percentResult = Double.parseDouble(percentS);

            if(percentResult > 100){
                tvPercent.setText("발전이 완료되었습니다");
            }else{
                tvPercent.setText("발전중입니다 ("+percentS+"%)");
            }
            progressAOD.setProgress(nowTime);
        }
    }// tHandler

}


