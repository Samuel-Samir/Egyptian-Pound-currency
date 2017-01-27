package com.example.android.dollar.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.example.android.dollar.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroductionActivity extends AppIntro {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int background = getResources().getColor(R.color.colorPrimary);
        int white = getResources().getColor(R.color.white);

        addSlide(AppIntroFragment.newInstance("محتوي البرنامج", "اسعار العملات المختلفة في البنوك وافضل سعر البيع والشراء،اسعار الذهب  والمحادثات ", R.drawable.s1, background));
        addSlide(AppIntroFragment.newInstance("البنوك", "اسعار العملات في مختلف البنوك ", R.drawable.s2, background));
        addSlide(AppIntroFragment.newInstance(" تفاصيل  البنك", "يظهر لك كل  معلومات  البنك  ويمكنك  الاتصال  او  حتي  ان  تزور  موقعة", R.drawable.s3, background));
        addSlide(AppIntroFragment.newInstance("أفضل سعر  يقدمه  البنك", "اقل  واعلي  سعر لي شراء  العملة", R.drawable.s4, background));
        addSlide(AppIntroFragment.newInstance("اسعار الذهب", "اسعار كل فئات  الذهب", R.drawable.s5, background));
        addSlide(AppIntroFragment.newInstance("المحادثة", "يمكنك  تبادل  الاسئلة  عبر  محادثة جماعية  ", R.drawable.s6, background));


        setBarColor(background);
        setSeparatorColor(white);

        showSkipButton(true);
        setProgressButtonEnabled(true);
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent i = new Intent(IntroductionActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(IntroductionActivity.this, MainActivity.class);
        startActivity(i);
        finish();

        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}




