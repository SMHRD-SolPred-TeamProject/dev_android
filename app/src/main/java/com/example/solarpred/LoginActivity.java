package com.example.solarpred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    TextView findUser;
    EditText loginId, loginPw;
    Button btnLogin;

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

       /* class CustomTask extends AsyncTask<String, Void, String> {
            String sendMsg, receiveMsg;
            @Override
            protected String doInBackground(String... strings) {
                try {
                    String str;
                    URL url = new URL("http://119.200.31.177:8080/jsp파일");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&type="+strings[2];
                    osw.write(sendMsg);
                    osw.flush();
                    if(conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();

                    } else {
                        Log.i("통신 결과", conn.getResponseCode()+"에러");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return receiveMsg;
            }
        }

        */

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
                String url = "서버연결url:포트번호/api/login";

                request = new StringRequest(method, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );

               String Id = loginId.getText().toString();
                String Pw = loginPw.getText().toString();
                String userId = "admin";
                String userPw = "1234";
               /* try {
                String result  = new CustomTask().execute(Id,Pw,"login").get();
                if(result.equals("true")) {
                    Toast.makeText(LoginActivity.this,"로그인",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, GraphActivity.class);
                    startActivity(intent);
                    finish();
                } else if(result.equals("false")) {
                    Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
                    loginId.setText("");
                    loginPw.setText("");
                } else if(result.equals("noId")) {
                    Toast.makeText(LoginActivity.this,"존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT).show();
                    loginId.setText("");
                    loginPw.setText("");
                }
            }catch (Exception e){

            }
            */
            if(Id.equals(userId)&&Pw.equals(userPw)){
                Intent intent = new Intent(LoginActivity.this,GraphActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }


            };
        });

    }

}