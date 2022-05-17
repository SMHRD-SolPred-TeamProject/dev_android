package com.example.solarpred;

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
    public Fragment3() { // Required empty public constructor
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment3, container, false);

        tvReal = v.findViewById(R.id.tvReal);
        tvSolution = v.findViewById(R.id.tvSolution);
        imgSolution = v.findViewById(R.id.imgSolution);


        tThread threads = new tThread();
        //String real = tvReal.getText().toString();
        //num = Integer.parseInt(real);
        //System.out.println("test~~~~~~~~~"+real);

        threads.start();






        return v;
    }
    class tThread extends Thread {
        @Override
        public void run() {
            while (true) {
                queue = Volley.newRequestQueue(getActivity().getApplicationContext());

                int method = Request.Method.GET;
                String url = "http://119.200.31.177:9090/solarpred/api/dash";
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

                                    //Handler에 값을 전달 -> Message 객체
                                    Message msg = handler.obtainMessage();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("aod", DashBoardList.get(0).getR_aod());
                                    bundle.putInt("realTotal",realTotal);
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
            realTotalAOD = bundle.getInt("realTotal");
            if(realTotalAOD <= 3){
                tvSolution.setText("태양광패널이 열심히 전력을 생산하고 있습니다");
                    //imgSolution.setImageResource(R.drawable.ic_battery1);
                    //imgSolution.setImageResource(R.drawable.ic_battery2);
                    //imgSolution.setImageResource(R.drawable.ic_battery3);
                    imgSolution.setImageResource(R.drawable.ic_battery4);
            }else if(realTotalAOD <= 6){
                imgSolution.setImageResource(R.drawable.ic_rice);
                tvSolution.setText("따끈따끈 전기밥솥을 5시간 사용할 수 있는 전력량이 생산됐어요");
            }else if (realTotalAOD <= 9){
                imgSolution.setImageResource(R.drawable.ic_ac);
                tvSolution.setText("우리집을 남극으로!\uD83D\uDC27 에어컨을 5시간 사용할 수 있는 전력량이 생산됐어요ㅤ");
            }else if (realTotalAOD <= 12 ){
                imgSolution.setImageResource(R.drawable.ic_dish);
                tvSolution.setText("뽀득뽀득 식기세척기를 5시간 사용할 수 있는 전력량이 생산됐어요");
            }else if (realTotalAOD <= 15){
                imgSolution.setImageResource(R.drawable.ic_induction);
                tvSolution.setText("편리한 인덕션을 5시간 사용할 수 있는 전력량이 생산됐어요");
            }else if(realTotalAOD <= 77) {
                imgSolution.setImageResource(R.drawable.ic_oneperson);
                tvSolution.setText("1인 가구의 한달 전력량이 생산됐어요");
            }else if(realTotalAOD <= 100){
                imgSolution.setImageResource(R.drawable.ic_car);
                tvSolution.setText("전기차를 완충할 전력량이 생산됐어요");
            }else if(realTotalAOD <= 150){
                imgSolution.setImageResource(R.drawable.ic_twoperson);
                tvSolution.setText("2인 가구의 한 달 전력량이 생산됐어요");
            }else if(realTotalAOD <= 300){
                imgSolution.setImageResource(R.drawable.ic_family);
                tvSolution.setText("4인 가구의 한 달 전력량이 생산됐어요");
            }else{
                imgSolution.setImageResource(R.drawable.ic_family);
                tvSolution.setText("4인 가구의 세달 전력량이 생산됐어요");
            }

            tvReal.setText(String.valueOf(realTotalAOD)+"KWh");
        }

    }// tHandler


}


