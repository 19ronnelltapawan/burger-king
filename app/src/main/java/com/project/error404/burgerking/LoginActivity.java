package com.project.error404.burgerking;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;
    ViewPager viewPager;

    DatabaseHelper myDB = new DatabaseHelper(this);
    myClass mc = new myClass();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);

        email = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onClickLogin(View view) {
        String storedPassword = myDB.getLoginInfo(email.getText().toString());
        if (pass.getText().toString().equals(storedPassword)) {
            SharedPreferences myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("email", email.getText().toString());
            editor.commit();
            startActivity(new Intent(getApplicationContext(), MasterActivity.class));
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"Email address or password do not match", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
