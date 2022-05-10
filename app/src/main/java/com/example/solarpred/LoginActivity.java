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
import android.preference.PreferenceManager;
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

    RequestQueue queue;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findUser = findViewById(R.id.findUser);
        loginId = findViewById(R.id.loginId);
        loginPw = findViewById(R.id.loginPw);
        btnLogin = findViewById(R.id.btnLogin);
        autoLogin = findViewById(R.id.autoLogin);

        queue = Volley.newRequestQueue(LoginActivity.this);



        findUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browser);
            }
        });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
                                            "로그인성공>> " + response,
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(LoginActivity.this, "로그인실패", Toast.LENGTH_SHORT).show();
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