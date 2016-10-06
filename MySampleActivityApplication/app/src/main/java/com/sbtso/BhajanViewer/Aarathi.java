package com.sbtso.BhajanViewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sandeepperkari on 7/31/16.
 */
public class Aarathi  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments().getInt("aarathiIndex") == 0){

                View kakad = inflater.inflate(R.layout.kakad_fragment, container, false);
                return kakad;
        } else if(getArguments().getInt("aarathiIndex") == 1){
            View madyana = inflater.inflate(R.layout.madhyana_fragment, container, false);
            return madyana;
        }
        else if(getArguments().getInt("aarathiIndex") == 2){
            View dhoop = inflater.inflate(R.layout.dhoop_fragment, container, false);
            return dhoop;
        }
        else if(getArguments().getInt("aarathiIndex") == 3){
            View shej = inflater.inflate(R.layout.shej_fragment, container, false);
            return shej;
        }

        return  null;
    }
}