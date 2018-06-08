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
public class Three extends Fragment {

    String contact;

    public static Three newInstance(String contact) {
        Three fragmentThree = new Three();
        Bundle args = new Bundle();
        args.putString("contact", contact);
        fragmentThree.setArguments(args);
        return fragmentThree;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact = getArguments().getString("contact");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.three, container, false);
        TextView con = (TextView) view.findViewById(R.id.pls_contact);
        con.setText(contact + "");
        return view;
    }

}
