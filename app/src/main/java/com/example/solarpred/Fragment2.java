package com.example.solarpred;

import static android.graphics.Color.WHITE;

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
    public Fragment2() { // Required empty public constructor
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment2, container, false);
        TextView tvDate = (TextView) v.findViewById(R.id.tvAOD);
        tvPercent = v.findViewById(R.id.tvPercent);
        progressAOD = v.findViewById(R.id.progressAOD);
        progressAOD.setIndeterminate(false);


        tvDate.setText("+1 Hour");

        ArrayList<Entry> entry_chart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<Entry> entry_chart2 = new ArrayList<>();

        lineChart = v.findViewById(R.id.Chart);
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
        xAxis.setSpaceMax(60f);
        xAxis.setSpaceMin(2f);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        // GraphAxisValueFormatter formatter = new GraphAxisValueFormatter(sysTime);
        // xAxis.setValueFormatter(formatter);
        xAxis.setValueFormatter(new MyFormatter());
        xAxis.setSpaceMin(1f);
        xAxis.setSpaceMax(1f);

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
        //Float aods = Float.parseFloat(String.valueOf(aod));
        addEntry(Predaod);
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

        // let the chart know it's data has changed
        lineChart.notifyDataSetChanged();

        lineChart.setVisibleXRangeMaximum(6);
        // this automatically refreshes the chart (calls invalidate())
        lineChart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);

    }

//    private void addEntry(){
//        LineData data = lineChart.getData();
//        if(data != null){
//            ILineDataSet set = data.getDataSetByIndex(0);
//
//            if(set == null){
//                set = createSet();
//                data.addDataSet(set);
//            }
//            data.addEntry(new Entry(set.getEntryCount(),(float) (Math.random()*40)+30f),0);
//            data.notifyDataChanged();
//
//            lineChart.notifyDataSetChanged();
//            lineChart.setVisibleXRangeMaximum(6);
//
//            lineChart.moveViewToX(data.getEntryCount());
//
//        }
//    }

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

    class tThread extends Thread{
        @Override
        public void run() {
            while(true) {
                queue = Volley.newRequestQueue(getActivity().getApplicationContext());

                int method = Request.Method.GET;
                String url = "http://119.200.31.177:9090/solarpred/api/pre";
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
                                    bundle.putDouble("predAOD",predList.get(0).getPred_aod());
                                    bundle.putDouble("total",PreTotal);
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
            //System.out.println("test~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+sysTime);
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

            //System.out.println("test~~~~~~~~~~~~~~~~~~~~~~~~~"+percent);

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


