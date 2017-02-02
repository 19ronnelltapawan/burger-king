package com.project.error404.burgerking.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.error404.burgerking.fragments.LoginFragment;
import com.project.error404.burgerking.fragments.RegisterFragment;

/**
 * Created by rober on 1/26/2017.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        if (i==0)
            fragment = new LoginFragment();
        if (i==1)
            fragment = new RegisterFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
