package com.project.error404.burgerking.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.error404.burgerking.R;
import com.project.error404.burgerking.activities.LoginActivity;
import com.project.error404.burgerking.activities.MasterActivity;
import com.project.error404.burgerking.classes.myClass;

public class SplashScreen extends AppCompatActivity {

    myClass mC;
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        new CountDownTimer(1300, 1000) {
            public void onTick(long millisUntilFinished) { }

            public void onFinish() {
                mC = new myClass();
                myPrefs = getSharedPreferences(mC.getPrefsName(), 0);
                if (myPrefs.getBoolean("isLoggedIn", false))
                    startActivity(new Intent(getApplicationContext(), MasterActivity.class));
                else {
                    editor = myPrefs.edit();
                    editor.putBoolean("isFromSplash", true);
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                finishAfterTransition();
            }
        }.start();
    }
}
