package com.project.error404.burgerking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    EditText id,fname,lname,email,pass,input;
    DatabaseHelper myDB = new DatabaseHelper(this);
    myClass mc = new myClass();
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        id = (EditText) findViewById(R.id.editText9);
        fname = (EditText) findViewById(R.id.editText10);
        lname = (EditText) findViewById(R.id.editText11);
        email = (EditText) findViewById(R.id.editText12);
        pass = (EditText) findViewById(R.id.editText13);

        SharedPreferences myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
        String current_email = myPrefs.getString("email", "");
        res = myDB.getCurrentData(current_email);

        id.setText(res.getString(0));
        fname.setText(res.getString(1));
        lname.setText(res.getString(2));
        email.setText(res.getString(3));
        pass.setText(res.getString(4));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MasterActivity.class));
        finish();
    }

    public void onClickUpdate(View view) {
        if (fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
            if (TextUtils.isEmpty(fname.getText().toString()))
                fname.setError("Please enter your first name");

            if (TextUtils.isEmpty(lname.getText().toString()))
                lname.setError("Please enter your last name");

            if (TextUtils.isEmpty(email.getText().toString()))
                email.setError("Please enter your email address");

            if (TextUtils.isEmpty(pass.getText().toString()))
                pass.setError("Please enter your password");
        }
        else {
            myDB.updateRecord(id.getText().toString(), fname.getText().toString(), lname.getText().toString(), email.getText().toString(), pass.getText().toString());
            Toast.makeText(getApplicationContext(), "Update successful!", Toast.LENGTH_LONG).show();
            SharedPreferences myPrefs = getSharedPreferences(mc.getPrefsName(), 0);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putString("email", email.getText().toString());
            editor.commit();
            startActivity(new Intent(getApplicationContext(), MasterActivity.class));
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}