package com.example.solarpred;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends Fragment {
    public Fragment3() {
    }
    RequestQueue queue;
    StringRequest request;
    TextView tvSolution, tvReal;
    ImageView imgSolution;
    tHandler handler = new tHandler();
    int num = 0;
    int realTotal = 0;
    int realTotalAOD = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment3, container, false);

        tvReal = v.findViewById(R.id.tvReal);
        tvSolution = v.findViewById(R.id.tvSolution);
        imgSolution = v.findViewById(R.id.imgSolution);

        tThread threads = new tThread();
        threads.start();

        return v;
    }
    class tThread extends Thread {
        @Override
        public void run() {
            while (true) {
                if (getActivity() != null) {
                    queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    int method = Request.Method.GET;
                    String url = "http://13.125.104.63:8080/api/dash";
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
                                    List<DashBoard> DashBoardList = new ArrayList<>();

                                    try {
                                        JSONObject object = new JSONObject(response);

                                        JSONArray dash = object.getJSONArray("dash");
                                        JSONObject rObj = dash.getJSONObject(1);
                                        DashBoard d = new DashBoard();
                                        JSONObject tObj = dash.getJSONObject(0);
                                        d.setR_aod(Integer.parseInt(tObj.getString("aod")));
                                        DashBoardList.add(d);

                                        realTotal = Integer.parseInt(rObj.getString("realTotal"));

                                        //Handler??? ?????? ?????? -> Message ??????
                                        Message msg = handler.obtainMessage();
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("aod", DashBoardList.get(0).getR_aod());
                                        bundle.putInt("realTotal", realTotal);
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
                                            "?????? ??????>> " + error.toString(),
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
            realTotalAOD = bundle.getInt("realTotal");
            if(realTotalAOD <= 3){
                tvSolution.setText("?????????????????? ????????? ????????? ???????????? ????????????");
                imgSolution.setImageResource(R.drawable.ic_battery4);
            }else if(realTotalAOD <= 6){
                imgSolution.setImageResource(R.drawable.ic_rice);
                tvSolution.setText("???????????? ??????????????? 5?????? ????????? ??? ?????? ???????????? ???????????????");
            }else if (realTotalAOD <= 9){
                imgSolution.setImageResource(R.drawable.ic_ac);
                tvSolution.setText("???????????? ????????????!\uD83D\uDC27 ???????????? 5?????? ????????? ??? ?????? ???????????? ??????????????????");
            }else if (realTotalAOD <= 12 ){
                imgSolution.setImageResource(R.drawable.ic_dish);
                tvSolution.setText("???????????? ?????????????????? 5?????? ????????? ??? ?????? ???????????? ???????????????");
            }else if (realTotalAOD <= 15){
                imgSolution.setImageResource(R.drawable.ic_induction);
                tvSolution.setText("????????? ???????????? 5?????? ????????? ??? ?????? ???????????? ???????????????");
            }else if(realTotalAOD <= 77) {
                imgSolution.setImageResource(R.drawable.ic_oneperson);
                tvSolution.setText("1??? ????????? ?????? ???????????? ???????????????");
            }else if(realTotalAOD <= 100){
                imgSolution.setImageResource(R.drawable.ic_car);
                tvSolution.setText("???????????? ????????? ???????????? ???????????????");
            }else if(realTotalAOD <= 150){
                imgSolution.setImageResource(R.drawable.ic_twoperson);
                tvSolution.setText("2??? ????????? ??? ??? ???????????? ???????????????");
            }else if(realTotalAOD <= 300){
                imgSolution.setImageResource(R.drawable.ic_family);
                tvSolution.setText("4??? ????????? ??? ??? ???????????? ???????????????");
            }else{
                imgSolution.setImageResource(R.drawable.ic_family);
                tvSolution.setText("4??? ????????? ?????? ???????????? ???????????????");
            }

            tvReal.setText(String.valueOf(realTotalAOD)+"kW");
        }

    }// tHandler

}