package com.example.nipu.touristguide.firstablayout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nipu.touristguide.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Two extends Fragment {

    String visitingTime;

    public static Two newInstance(String visitingTime) {
        Two fragmentSecond = new Two();
        Bundle args = new Bundle();
        args.putString("visiting_time", visitingTime);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visitingTime = getArguments().getString("visiting_time");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.two, container, false);

        TextView vt = (TextView) view.findViewById(R.id.pls_visiting_time);
        vt.setText(visitingTime+"");
        return view;
    }

}
