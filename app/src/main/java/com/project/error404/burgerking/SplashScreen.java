package com.project.error404.burgerking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    myClass mc = new myClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_splash_screen);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) { }

            public void onFinish() {
                SharedPreferences myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
                if (myPrefs.getBoolean("isLoggedIn", false))
                    startActivity(new Intent(getApplicationContext(), MasterActivity.class));
                else
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}
