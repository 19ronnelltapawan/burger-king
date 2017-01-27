package com.project.error404.burgerking;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by rober on 1/26/2017.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    /*@Override
    public Fragment getItem(int i) {
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count",i+1);
        fragment.setArguments(bundle);
        return fragment;
    }*/

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
