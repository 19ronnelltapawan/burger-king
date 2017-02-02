package com.project.error404.burgerking.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.project.error404.burgerking.R;
import com.project.error404.burgerking.activities.MasterActivity;
import com.project.error404.burgerking.classes.DatabaseHelper;
import com.project.error404.burgerking.classes.myClass;
import com.rengwuxian.materialedittext.MaterialEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    Button register;
    MaterialEditText fname,lname,email,pass, confirmpass;

    boolean isSaved;

    DatabaseHelper myDB;
    myClass mC;
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        fname = (MaterialEditText) view.findViewById(R.id.editText3);
        lname = (MaterialEditText) view.findViewById(R.id.editText4);
        email = (MaterialEditText) view.findViewById(R.id.editText5);
        pass = (MaterialEditText) view.findViewById(R.id.editText6);
        confirmpass = (MaterialEditText) view.findViewById(R.id.editText7);
        register = (Button) view.findViewById(R.id.button);

        mC = new myClass();
        myDB = new DatabaseHelper(getActivity());
        myPrefs = getActivity().getSharedPreferences(mC.getPrefsName(), 0);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || email.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || confirmpass.getText().toString().isEmpty()) {
                    if (fname.getText().toString().isEmpty())
                        fname.setError("Please enter your first name");

                    if (lname.getText().toString().isEmpty())
                        lname.setError("Please enter your last name");

                    if (email.getText().toString().isEmpty())
                        email.setError("Please enter your email address");

                    if (pass.getText().toString().isEmpty())
                        pass.setError("Please enter your password");

                    if (confirmpass.getText().toString().isEmpty())
                        confirmpass.setError("Please enter your password again");
                }
                else {
                    if (!fname.getText().toString().matches("^[A-Z][a-zA-Z( )?]+$") ||
                            !lname.getText().toString().matches("^[A-Z][a-zA-Z( )?]+$") ||
                            !email.getText().toString().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$") ||
                            !confirmpass.getText().toString().equals(pass.getText().toString())) {
                        if (!fname.getText().toString().matches("^[A-Z][a-zA-Z( )?]+$"))
                            fname.setError("Please enter a valid name (first letter must be capital)");
                        if (!lname.getText().toString().matches("^[A-Z][a-zA-Z( )?]+$"))
                            lname.setError("Please enter a valid name (first letter must be capital)");
                        if (!email.getText().toString().matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$"))
                            email.setError("Please enter a valid email");
                        if (!confirmpass.getText().toString().equals(pass.getText().toString()))
                            confirmpass.setError("Password is incorrect");
                    }
                    else {
                        isSaved = myDB.insertRecord(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), pass.getText().toString());
                        if (isSaved) {
                            editor = myPrefs.edit();
                            editor.putBoolean("isFromSplash", false);
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("email", email.getText().toString());
                            editor.commit();
                            startActivity(new Intent(getActivity().getApplicationContext(), MasterActivity.class));
                            getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        }
                        else
                            Toast.makeText(getActivity().getApplication(), "Email already taken",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
}
