package com.project.error404.burgerking.activities;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.project.error404.burgerking.R;
import com.project.error404.burgerking.classes.SwipeAdapter;
import com.project.error404.burgerking.classes.myClass;

public class LoginActivity extends AppCompatActivity {

    ImageView logo;

    Animation animateLogo;
    myClass mC;
    SharedPreferences myPrefs;
    SwipeAdapter swipeAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = (ImageView) findViewById(R.id.imageView);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);

        mC = new myClass();
        myPrefs = getSharedPreferences(mC.getPrefsName(), 0);

        if (myPrefs.getBoolean("isFromSplash", true)) {
            animateLogo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo);
            logo.startAnimation(animateLogo);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
