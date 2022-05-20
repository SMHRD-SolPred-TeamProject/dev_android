package com.example.solarpred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView logoImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logoImg = findViewById(R.id.LogoImg);
        startLoading();


    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
                logoImg.setVisibility(View.INVISIBLE);
                logoImg.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        },2500);
    }

    public static class DataPage {
        int color;
        String title;

        public DataPage(int color, String title){
            this.color = color;
            this.title = title;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}