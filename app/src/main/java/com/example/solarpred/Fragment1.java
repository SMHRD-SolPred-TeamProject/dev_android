package com.example.solarpred;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    public Fragment1() { // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment1, container, false);
        TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText("TODAY");

        return v;
    }
}


