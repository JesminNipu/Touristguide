package com.example.nipu.touristguide.firstablayout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nipu.touristguide.R;
import com.example.nipu.touristguide.adapters.HotelAdapter;
import com.example.nipu.touristguide.modelclass.Hotel;
import com.example.nipu.touristguide.modelclass.Response;
import com.example.nipu.touristguide.network.ApiClient;
import com.example.nipu.touristguide.service.ApiInterface;
import com.example.nipu.touristguide.service.MyService;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookHotelsFragment extends Fragment {
    EditText etSearch;
    Spinner spinnerDiv;
    RecyclerView hotelRecyclerView;
    HotelAdapter hotelAdapter;
    ApiInterface apiInterface;
    Retrofit retrofit;
    RadioGroup priGroup;
    String priType = "All";

    public BookHotelsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookhotelsfragment, container, false);

        etSearch = (EditText) view.findViewById(R.id.hotel_search);
        spinnerDiv = (Spinner) view.findViewById(R.id.hotel_div);
        hotelRecyclerView = (RecyclerView) view.findViewById(R.id.hotels);
        priGroup = (RadioGroup) view.findViewById(R.id.rg_hotel);

        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Hotel> loadingHotel = getLoadingHotel();

        hotelAdapter = new HotelAdapter(getActivity(), loadingHotel);
        hotelRecyclerView.setAdapter(hotelAdapter);
        createRecycleHotel("DHAKA"); //.......??

        setSpinner();
        priGro();
        search();

        return view;
    }

    private void search() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                hotelAdapter.searchHotel(searchText, priType);
            }
        });
    }

    private void priGro() {
        priGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);
                String priTitle = rb.getText().toString();
                priType = priTitle;
                //Toast.makeText(getContext(),priTitle,Toast.LENGTH_LONG).show();
                String searchText = etSearch.getText().toString().toLowerCase(Locale.getDefault());

                hotelAdapter.searchHotel(searchText, priType);

            }
        });
    }

    private ArrayList<Hotel> getLoadingHotel() { //...........??
        ArrayList<Hotel> tt = new ArrayList<Hotel>();
        Hotel h = new Hotel();
        h.setTitle("Data Loading...");
        h.setDivision_name("Data Loading...");
        h.setPrice_type("Data Loading...");
        tt.add(h);
        MyService.L("Data Loading...");
        return tt;
    }

    private void createRecycleHotel(String div) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //call api method for return type arraylist of diviname
        Call<ArrayList<Hotel>> call = apiInterface.getHotels(div); //nav drawer er menu call er jnnk korecilalm

        call.enqueue(new Callback<ArrayList<Hotel>>() {
            @Override
            public void onResponse(Call<ArrayList<Hotel>> call, retrofit2.Response<ArrayList<Hotel>> response) {
                ArrayList<Hotel> hotels = response.body();
                if (hotels != null && hotels.size() > 0) {
                    hotelAdapter.dataChanged(hotels);  //division wise change
                    //MyService.L("hotels" + hotels.size() + hotels.get(0).getTitle());
                    MyService.L("hotels dta chnged" + hotels.size() + hotels.get(0).getTitle());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Hotel>> call, Throwable t) {
                Toast.makeText(getContext(), "Connection Failed!!!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setSpinner() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //call api method for return type arraylist of diviname
        Call<ArrayList<Response>> call = apiInterface.getDivname(); //nav drawer er menu call er jnnk korecilalm

        call.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                ArrayList<CharSequence> divName = new ArrayList<CharSequence>();
                ArrayList<Response> div = response.body();
                if (div != null)
                    for (Response d : div) {
                        divName.add(d.getDistName().toUpperCase());
                    }
                ArrayAdapter<CharSequence> adapter;

                if (div != null && div.size() > 0) {
                    adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_dropdown_item, divName);
                } else {
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.hotels_div, android.R.layout.simple_spinner_item);
                }


                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDiv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {

                Toast.makeText(getContext(), "Connection Failed!!!", Toast.LENGTH_LONG).show();
            }
        });
        spinnerDiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                createRecycleHotel(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
