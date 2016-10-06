package com.sbtso.BhajanViewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sandeepperkari on 8/1/16.
 */
public class HomeTabsPagerAdapter extends FragmentPagerAdapter {

    public HomeTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new Bhajans();
            case 1:
                return new Schedules();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
}
