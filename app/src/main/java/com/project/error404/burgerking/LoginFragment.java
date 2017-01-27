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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    Button login;
    EditText email,pass;
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
        email = (EditText) view.findViewById(R.id.editText);
        pass = (EditText) view.findViewById(R.id.editText2);
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
                storedPassword = myDB.getLoginInfo(email.getText().toString());
                if (pass.getText().toString().equals(storedPassword)) {
                    editor = myPrefs.edit();
                    editor.putBoolean("isFromSplash", false);
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("email", email.getText().toString());
                    editor.commit();
                    startActivity(new Intent(getActivity().getApplicationContext(), MasterActivity.class));
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
