package com.sbtso.BhajanViewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
/**
 * Created by sandeepperkari on 7/31/16.
 */
public class AarathiTabsPagerAdapter extends FragmentPagerAdapter {

    public AarathiTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                Aarathi kakad = new Aarathi();
                    Bundle bdl = new Bundle();
                    bdl.putInt("aarathiIndex",0);
                kakad.setArguments(bdl);
                return kakad;
            case 1:
                Aarathi madhyana = new Aarathi();
                    Bundle bdl_1 = new Bundle();
                   bdl_1.putInt("aarathiIndex",1);
                madhyana.setArguments(bdl_1);
                return madhyana;
            case 2:
                Aarathi dhoop = new Aarathi();
                    Bundle bdl_2 = new Bundle();
                    bdl_2.putInt("aarathiIndex",2);
                dhoop.setArguments(bdl_2);
                return dhoop;
            case 3:
                Aarathi shej = new Aarathi();
                    Bundle bdl_3 = new Bundle();
                    bdl_3.putInt("aarathiIndex",3);
                shej.setArguments(bdl_3);
                return shej;
        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
}
