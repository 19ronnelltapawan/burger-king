package com.project.error404.burgerking.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.error404.burgerking.R;
import com.project.error404.burgerking.activities.MasterActivity;
import com.project.error404.burgerking.classes.DatabaseHelper;
import com.project.error404.burgerking.classes.myClass;
import com.rengwuxian.materialedittext.MaterialEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    Button login;
    MaterialEditText email,pass;
    TextView swipe;
    RelativeLayout myLayout;

    String storedPassword;

    Animation anim;
    myClass mC;
    DatabaseHelper myDB;
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = (MaterialEditText) view.findViewById(R.id.editText);
        pass = (MaterialEditText) view.findViewById(R.id.editText2);
        login = (Button) view.findViewById(R.id.button);
        swipe = (TextView) view.findViewById(R.id.textView);
        myLayout = (RelativeLayout) view.findViewById(R.id.myLayout);

        mC = new myClass();
        myDB = new DatabaseHelper(getActivity());
        myPrefs = getActivity().getSharedPreferences(mC.getPrefsName(), 0);

        if (myPrefs.getBoolean("isFromSplash", true)) {
            anim = AnimationUtils.loadAnimation(getActivity(), R.anim.leftright);
            myLayout.startAnimation(anim);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storedPassword = myDB.selectLoginInfo(email.getText().toString());
                if (pass.getText().toString().equals(storedPassword)) {
                    editor = myPrefs.edit();
                    editor.putBoolean("isFromSplash", false);
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("email", email.getText().toString());
                    editor.commit();
                    startActivity(new Intent(getActivity().getApplicationContext(), MasterActivity.class));
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Email address or password is incorrect", Toast.LENGTH_LONG).show();
                    anim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
                    swipe.startAnimation(anim);
                }
            }
        });

        return view;
    }
}
