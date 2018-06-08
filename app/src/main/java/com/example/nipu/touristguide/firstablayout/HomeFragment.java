package com.example.nipu.touristguide.firstablayout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nipu.touristguide.activity.MainActivity;
import com.example.nipu.touristguide.R;
import com.example.nipu.touristguide.adapters.RecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public static TextView textView;
    public static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView all, historical,nature,parks,museums;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.conversation);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter r = ((MainActivity) getActivity()).changeCat();
        recyclerView.setAdapter(r);
        all = (TextView) view.findViewById(R.id.pls_type_all);
        historical = (TextView) view.findViewById(R.id.pls_type_historical);
        nature=(TextView)view.findViewById(R.id.pls_type_nature);
        museums=(TextView)view.findViewById(R.id.pls_type_museums);
        parks=(TextView)view.findViewById(R.id.pls_type_parks);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerAdapter r = ((MainActivity) getActivity()).changeCat();
                r.filterCat("ALL");
                Toast.makeText(getContext(), "ALL", Toast.LENGTH_SHORT).show();
            }
        });
        historical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerAdapter r = ((MainActivity) getActivity()).changeCat();
                r.filterCat("Landmarks");
                Toast.makeText(getContext(), "Landmarks", Toast.LENGTH_SHORT).show();

            }
        });
        nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerAdapter r = ((MainActivity) getActivity()).changeCat();
                r.filterCat("Nature");
                Toast.makeText(getContext(), "Nature", Toast.LENGTH_SHORT).show();
            }
        });
       museums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerAdapter r = ((MainActivity) getActivity()).changeCat();
                r.filterCat("Museums");
                Toast.makeText(getContext(), "Museums", Toast.LENGTH_SHORT).show();
            }
        });
        parks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerAdapter r = ((MainActivity) getActivity()).changeCat();
                r.filterCat("Parks");
                Toast.makeText(getContext(), "Parks", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
