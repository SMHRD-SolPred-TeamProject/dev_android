package com.example.solarpred;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


public class GraphActivity extends AppCompatActivity {
    private ViewPager2 pager;
    private FragmentStateAdapter pagerAdapter;
    CheckBox autoLogin;
    RequestQueue queue;
    StringRequest request;
    TextView tvCity, tvWeather, tvTemp, tvWind;
    ImageView imgWeather;
    private DrawerLayout mDrawerLayout;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        autoLogin = findViewById(R.id.autoLogin);
        tvCity = findViewById(R.id.tvCity);
        tvWeather =findViewById(R.id.tvWeather);
        tvTemp = findViewById(R.id.tvTemp);
        tvWind = findViewById(R.id.tvWind);
        imgWeather = findViewById(R.id.imgWeather);

        pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);


        queue = Volley.newRequestQueue(GraphActivity.this);
        int method =Request.Method.GET;
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Gumi&appid=b33f6dac8f31aae2893ad33278ed55f5";

        request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String city ="";
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("name").equals("Gumi")){
                        city = "구미";
                    }else{
                        city = "한국";
                    };

                    JSONArray weatherJson = obj.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);
                    String weather = "";
                    if(weatherObj.getString("main").equals("Thunderstorm")){
                        weather = "번개";
                        imgWeather.setImageResource(R.drawable.ic_storm);
                    }else if(weatherObj.getString("main").equals("Drizzle")){
                        weather ="이슬비";
                        imgWeather.setImageResource(R.drawable.ic_drizzel);
                    }else if(weatherObj.getString("main").equals("Rain")){
                        weather = "비";
                        imgWeather.setImageResource(R.drawable.ic_rain);
                    }else if(weatherObj.getString("main").equals("Snow")){
                        weather = "눈";
                        imgWeather.setImageResource(R.drawable.ic_snow);
                    }else if(weatherObj.getString("main").equals("Atmosphere")){
                        weather = "안개";
                        imgWeather.setImageResource(R.drawable.ic_fog);
                    }else if(weatherObj.getString("main").equals("Clear")){
                        weather = "맑음";
                        imgWeather.setImageResource(R.drawable.ic_clear);
                    }else if(weatherObj.getString("main").equals("Clouds")){
                        weather = "구름";
                        imgWeather.setImageResource(R.drawable.ic_cloud);
                    }


                    JSONObject tempk = new JSONObject(obj.getString("main"));
                    double tempDo = (Math.round((tempk.getDouble("temp")-273.15)*100)/100.0);

                    JSONObject wind = new JSONObject(obj.getString("wind"));
                    double windSpeed = wind.getDouble("speed");

                    tvCity.setText(city);
                    tvWeather.setText(weather);
                    tvTemp.setText(tempDo+"°C");
                    tvWind.setText(windSpeed+"㎧");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GraphActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(request);


        pager = findViewById(R.id.pager);
        pagerAdapter = new ScreeSlidePagerAdapter(this);
        pager.setAdapter(pagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu); //뒤로가기 버튼 이미지 지정

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

//                if(id == R.id.darkMode){
//                    Toast.makeText(context, title + "다크모드", Toast.LENGTH_SHORT).show();
//                }
                if(id == R.id.internet){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://13.125.104.63:8080/"));
                    startActivity(intent);
                    Toast.makeText(context, title + ": 홈페이지로 이동", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.asTool){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://13.125.104.63:8080/api/boardList"));
                    startActivity(intent);
                    Toast.makeText(context, title + ": 게시판으로 이동", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.logout){
                    Toast.makeText(context, title + ": 로그아웃", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GraphActivity.this, LoginActivity.class);
                    intent.putExtra("checkBox","true");
                    intent.putExtra("message","성공");
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    } //페이저와 프래그먼트 이어주기
    class ScreeSlidePagerAdapter extends FragmentStateAdapter {
        private static final int NUM_PAGES = 3;

        public ScreeSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {//포지션마다 있을 fragment설정
            if (position == 0) return new Fragment1();
            else if (position == 1) return new Fragment2();
            else return new Fragment3();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES; //페이지 수 지정.
        }

    };




//}


