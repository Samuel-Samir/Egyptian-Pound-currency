package com.example.android.dollar.Activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.android.dollar.R;
import com.example.android.dollar.Utilities;


public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Handler  mHandler=new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences getPrefs = PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext());
                        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                        Utilities.isFirst =isFirstStart ;

                        if (isFirstStart) {

                                Intent i = new Intent(SplashScreenActivity.this, IntroductionActivity.class);
                                startActivity(i);
                                SharedPreferences.Editor e = getPrefs.edit();
                                e.putBoolean("firstStart", false);
                                e.apply();

                        }
                        else {
                                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                                startActivity(i);

                        }
                        finish();
                    }
                });
                t.start();
            }
        }, SPLASH_TIME_OUT);

    }
}

