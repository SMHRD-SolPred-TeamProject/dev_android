package com.example.solarpred;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView findUser;
    EditText loginId, loginPw;
    Button btnLogin;
    CheckBox autoLogin;
    Context context;
    Boolean chbool = true;

    RequestQueue queue;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        findUser = findViewById(R.id.findUser);
        loginId = findViewById(R.id.loginId);
        loginPw = findViewById(R.id.loginPw);
        btnLogin = findViewById(R.id.btnLogin);
        autoLogin = findViewById(R.id.autoLogin);

        Intent chIntent = getIntent();
        String ch = chIntent.getStringExtra("checkBox");
        chbool = Boolean.parseBoolean(ch);
        System.out.println(chbool); //false

        String msg = chIntent.getStringExtra("message");
        System.out.println("msg = " + msg);

        autoLogin.setChecked(chbool);

        queue = Volley.newRequestQueue(LoginActivity.this);

        //체크박스 체크유무
        boolean tf = PreferenceManager.getBoolean(context,"check");
        if(tf){
            loginId.setText(PreferenceManager.getString(context,"id"));
            loginPw.setText(PreferenceManager.getString(context,"pw"));
            autoLogin.setChecked(true);
        }
        if(autoLogin.isChecked() && chbool ==false) {
            Intent intent = new Intent(LoginActivity.this, GraphActivity.class);
            startActivity(intent);
            finish();
        }
        autoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox)view).isChecked()){
                    PreferenceManager.setString(context, "id", loginId.getText().toString()); //id 저장
                    PreferenceManager.setString(context, "pw", loginPw.getText().toString()); //pw 저장
                    PreferenceManager.setBoolean(context,"check",autoLogin.isChecked()); //현재 체크박스 상태
                }else{
                    PreferenceManager.setBoolean(context,"check", autoLogin.isChecked()); //현재 체크박스상태
                    PreferenceManager.clear(context); //로그인 정보 삭제
                }
            }
        });


        findUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://119.200.31.177:9090/solarpred/"));
                startActivity(browser);
            }
        });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PreferenceManager.setString(context,"id",loginId.getText().toString()); //키값 id
                    PreferenceManager.setString(context,"pw",loginPw.getText().toString()); //키갑 pw

                    String checkid = PreferenceManager.getString(context,"id"); //id 키값 불러오기
                    String checkPw = PreferenceManager.getString(context,"pw"); //id 키값 불러오기

                    if(TextUtils.isEmpty(checkid)||TextUtils.isEmpty(checkPw)){
                        Toast.makeText(LoginActivity.this, "아이디/암호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }



                    int method = Request.Method.POST;
                    String url = "http://119.200.31.177:9090/solarpred/api/login";

                    request = new StringRequest(method, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            List<Member> memberList = new ArrayList<>();
                            try {
                                JSONObject object = new JSONObject(response);

                                JSONArray mem = object.getJSONArray("Member");

                                for (int i = 0; i < mem.length(); i++) {
                                    JSONObject memObj = mem.getJSONObject(i);
                                    Member m = new Member();

                                    m.setMem_id(memObj.getString("mem_id"));
                                    m.setMem_pw(memObj.getString("mem_pw"));

                                    memberList.add(m);
                                }

                                if (memberList.get(0).getMem_id().equals(loginId.getText().toString()) && memberList.get(0).getMem_pw().equals(loginPw.getText().toString())) {
                                    Toast.makeText(LoginActivity.this,
                                            "로그인성공 " ,
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, GraphActivity.class);
                                    startActivity(intent);
                                    finish();

                                }else if(memberList.get(0).getMem_id().equals(loginId.getText().toString()) || memberList.get(0).getMem_pw().equals(loginPw.getText().toString())){
                                    Toast.makeText(LoginActivity.this, "아이디/패스워드를 확인해주세요", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "존재하지 않는 아이디입니다", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                    ) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //POST요청시 매개변수값을 Map객체에 담아 전달
                            Map<String, String> param = new HashMap<>();
                            param.put("mem_id", loginId.getText().toString());
                            param.put("mem_pw", loginPw.getText().toString());

                            return param;
                        }
                    };
                    queue.add(request);

                }
            });

    }
}