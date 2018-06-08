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
public class One extends Fragment {
    String overView;

    public static One newInstance(String overview) {
        One fragmentFirst = new One();
        Bundle args = new Bundle();
        args.putString("overview", overview);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overView = getArguments().getString("overview");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //get details
        View view = inflater.inflate(R.layout.one, container, false);
        TextView ov = (TextView) view.findViewById(R.id.pls_overview);
        ov.setText(overView + "");
        return view;
    }

}
