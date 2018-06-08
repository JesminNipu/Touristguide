package com.example.nipu.touristguide.firstablayout;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nipu.touristguide.R;
import com.example.nipu.touristguide.activity.JsonArray;
import com.example.nipu.touristguide.adapters.WeatherAdapter;
import com.example.nipu.touristguide.modelclass.Weather;
import com.example.nipu.touristguide.service.MyServices;
import com.example.nipu.touristguide.service.RetrofitAPI;
import com.example.nipu.touristguide.service.RetrofitClient;
import com.example.nipu.touristguide.service.V;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    RetrofitAPI retrofitAPI;
    Spinner spinner;
    ProgressDialog progressDialog;
    ImageView ivIcon;
    TextView tvTemperature, tvMinTemperature, tvMaxTemperature;
    TextView tvPressure, tvHumidity, tvRain, tvWindSpeed, tvWindDegree, tvWeatherStatus;
    RecyclerView recyclerView;
    WeatherAdapter weatherAdapter;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_weatherfragment, container, false);

        init(view);

        setSpinner(view);

        return view;
    }
    private void init(View view) {
        ivIcon = (ImageView) view.findViewById(R.id.weather_icon);
        tvTemperature = (TextView) view.findViewById(R.id.temperature);
        tvMaxTemperature = (TextView) view.findViewById(R.id.max_temp);
        tvMinTemperature = (TextView) view.findViewById(R.id.min_temp);
        tvHumidity = (TextView) view.findViewById(R.id.humidity);
        tvPressure = (TextView) view.findViewById(R.id.pressure);
        tvRain = (TextView) view.findViewById(R.id.rain);
        tvWindSpeed = (TextView) view.findViewById(R.id.wind_speed);
        tvWindDegree = (TextView) view.findViewById(R.id.wind_degree);
        tvWeatherStatus = (TextView) view.findViewById(R.id.weather_status);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_temp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setSpinner(View view) {
        spinner = (Spinner) view.findViewById(R.id.city_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.city_name, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cityName = spinner.getSelectedItem().toString();
                getWeather(cityName);
                MyServices.log(cityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MyServices.log("Dhaka,BD");
                getWeather("Dhaka,BD");
            }
        });
    }


    private void getWeather(String cityName) {
        if (MyServices.checkConnectivity(getContext())) {
            progressDialog = MyServices.progressDialog(getContext(), "Loading...");
            progressDialog.show();
            retrofitAPI = RetrofitClient.getClient(V.BASE_URL).create(RetrofitAPI.class);
            Call<ResponseBody> callback = retrofitAPI.getWeather(cityName, V.MODE, V.UNITS, V.APP_ID);
            callback.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        //get all json object data
                        if (response.body() != null) {
                            JSONObject data = new JSONObject(response.body().string());
                            //MyServices.log("ALL DATA: " + data.toString());

                            //status code
                            String code = data.getString("cod");
                            //MyServices.log("CODE: " + code);

                            if (Integer.valueOf(code) == 200) {

                                //message
                                String message = data.getString("message");
                                MyServices.log("MESSAGE: " + message);

                                //total report count
                                String cnt = data.getString("cnt");
                                MyServices.log("COUNT: " + cnt);

                                //city info
                                JSONObject city = data.getJSONObject("city");
                                MyServices.log("CITY: " + city);
                                MyServices.log("CITY Name: " + city.getString("name"));

                                //LAT LON
                                JSONObject coord = city.getJSONObject("coord");
                                MyServices.log("LAT LON: " + coord);
                                MyServices.log("LAT: " + coord.getString("lat"));
                                MyServices.log("LON: " + coord.getString("lon"));

                                //get reports
                                JSONArray forecastList = data.getJSONArray("list");//.getJSONObject(0);
                                MyServices.log("FORECASTLIST: " + forecastList);

                                JsonArray jsonArray = new JsonArray();
                                ArrayList<Weather> weatherList = jsonArray.toWeather(forecastList);
                                Weather currentWeather = weatherList.get(0);
                                MyServices.log("WEATHER: " + currentWeather.toString());

                                tvTemperature.setText(currentWeather.getTemperature() + V.DEGREE);
                                tvMinTemperature.setText(currentWeather.getMinTemperature() + V.DEGREE);
                                tvMaxTemperature.setText(currentWeather.getMaxTemperature() + V.DEGREE);
                                tvWeatherStatus.setText(currentWeather.getWeatherDescription() + "");
                                tvHumidity.setText(currentWeather.getHumidity() + "");
                                tvPressure.setText(currentWeather.getPressure() + "");
                                tvRain.setText(currentWeather.getRain() + "");
                                tvWindSpeed.setText(currentWeather.getWindSpeed() + "");
                                tvWindDegree.setText(currentWeather.getWindDeg() + "");
                                try {
                                    Picasso.with(getContext())
                                            .load(V.ICON_BASE_URL + currentWeather.getWeatherIcon() + V.ICON_FORMAT)
//                                    .resize(200, 200)
                                            .into(ivIcon);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (weatherList.size() > 0) {
                                    weatherAdapter = new WeatherAdapter(weatherList, getContext());
                                    recyclerView.setAdapter(weatherAdapter);
                                }
//                            MyServices.log("WEATHER 0: " + weatherList.get(0).toString());
//                            String value = "";
//                            for (Weather weather : weatherList) {
//                                value += "Date: " + weather.getDate() + "\n";
//                                value += "Date Txt: " + weather.getDtTxt() + "\n";
//                                value += "Time: " + weather.getTime() + "\n";
//                                value += "Temperature: " + weather.getTemperature() + "\n";
//                                value += "Icon: " + V.ICON_BASE_URL + weather.getWeatherIcon() + V.ICON_FORMAT + "\n";
//                                value += "=====================\n";
//                            }
                            } else {
                                MyServices.alertDialog(getContext(), "Connection. Please try again after sometime.");

                            }
                        } else {
                            MyServices.alertDialog(getContext(), "PLace not found.");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    MyServices.alertDialog(getContext(), "Network failed. Please try again after sometime.");
                }
            });
        } else {
            MyServices.alertDialog(getContext(), "Internet Connectivity failed. Please connect with your internet.");
        }
    }
}
