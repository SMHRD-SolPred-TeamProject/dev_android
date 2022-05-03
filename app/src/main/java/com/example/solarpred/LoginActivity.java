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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findUser = findViewById(R.id.findUser);
        loginId = findViewById(R.id.loginId);
        loginPw = findViewById(R.id.loginPw);
        btnLogin = findViewById(R.id.btnLogin);

        class CustomTask extends AsyncTask<String, Void, String> {
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
                String Id = loginId.getText().toString();
                String Pw = loginPw.getText().toString();
                try {
                String result  = new CustomTask().execute(Id,Pw,"login").get();
                if(result.equals("true")) {
                    Toast.makeText(LoginActivity.this,"로그인",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, GraphActivity.class);
                    startActivity(intent);
                    finish();
                } else if(result.equals("false")) {
                    Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 틀렸음",Toast.LENGTH_SHORT).show();
                    loginId.setText("");
                    loginPw.setText("");
                } else if(result.equals("noId")) {
                    Toast.makeText(LoginActivity.this,"존재하지 않는 아이디",Toast.LENGTH_SHORT).show();
                    loginId.setText("");
                    loginPw.setText("");
                }
            }catch (Exception e){

            }

            };
        });

    }

}