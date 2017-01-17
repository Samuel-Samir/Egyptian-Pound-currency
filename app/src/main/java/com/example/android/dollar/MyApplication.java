package com.example.android.dollar;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by samuel on 12/14/2016.
 */

public class MyApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());



    }
}
