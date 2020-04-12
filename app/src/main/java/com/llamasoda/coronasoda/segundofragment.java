package com.llamasoda.coronasoda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

//import android.support.v4.app.Fragment;

public class segundofragment extends Fragment {

    public static segundofragment newInstance() {
        return new segundofragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.segundomenudetabs , container, false);
    }

}
