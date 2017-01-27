package com.project.error404.burgerking;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText email,pass;
    TextView swipe;
    ImageView logo;
    Button login;
    myClass mc;
    DatabaseHelper myDB;
    SharedPreferences myPrefs;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = (EditText) view.findViewById(R.id.editText);
        pass = (EditText) view.findViewById(R.id.editText2);
        login = (Button) view.findViewById(R.id.button);
        swipe = (TextView) view.findViewById(R.id.textView);
        logo = (ImageView) view.findViewById(R.id.imageView);

        mc = new myClass();
        myDB = new DatabaseHelper(getActivity());
        myPrefs = getActivity().getSharedPreferences(mc.getPrefsName(), 0);

        if (myPrefs.getBoolean("isFromSplash", false)) {
            Animation fromTop = AnimationUtils.loadAnimation(getActivity(), R.anim.fromtop);
            logo.startAnimation(fromTop);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storedPassword = myDB.getLoginInfo(email.getText().toString());
                if (pass.getText().toString().equals(storedPassword)) {
                    SharedPreferences myPrefs = getActivity().getSharedPreferences(mc.getPrefsName(), 0);
                    SharedPreferences.Editor editor = myPrefs.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putBoolean("isFromSplash", false);
                    editor.putString("email", email.getText().toString());
                    editor.commit();
                    startActivity(new Intent(getActivity().getApplicationContext(), MasterActivity.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Email address or password do not match", Toast.LENGTH_LONG).show();
                    Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
                    swipe.startAnimation(bounce);
                }
            }
        });

        return view;
    }
}
