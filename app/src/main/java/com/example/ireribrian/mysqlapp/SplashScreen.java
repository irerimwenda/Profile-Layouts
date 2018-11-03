package com.example.ireribrian.mysqlapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent loginIntent = new Intent(SplashScreen.this, Home.class);
                SplashScreen.this.startActivity(loginIntent);
                SplashScreen.this.finish();
            }
        }, 3500);
    }
}
