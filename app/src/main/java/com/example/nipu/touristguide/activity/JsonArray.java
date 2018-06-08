package com.example.nipu.touristguide.activity;

import com.example.nipu.touristguide.modelclass.Weather;
import com.example.nipu.touristguide.service.MyServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vip.parvez.UNIX;

/**
 * Created by NIPU on 5/3/2018.
 */

public class JsonArray {
    public ArrayList<Weather> toWeather(JSONArray jsonArray) {
        ArrayList<Weather> weatherList = new ArrayList<Weather>();
        if (jsonArray != null) {

            Weather weather = new Weather();
            JSONObject weatherItem = null;
            for(int i=0;i<jsonArray.length();i++)
            {

                try {
                    weatherItem = jsonArray.getJSONObject(i); //0 no obj
                    JSONObject mainObject = weatherItem.getJSONObject("main");
                    JSONObject weatherObject = weatherItem.getJSONArray("weather").getJSONObject(0);
                    JSONObject cloudsObject = weatherItem.getJSONObject("clouds");
                    JSONObject windObject = weatherItem.getJSONObject("wind");
                    JSONObject rainObject = weatherItem.getJSONObject("rain");
                    JSONObject sysObject = weatherItem.getJSONObject("sys");

                    String text_date = weatherItem.getString("dt_txt");
                    int timestamp = Integer.valueOf(weatherItem.getString("dt"));
                    String date = UNIX.toGMT("", "dd-MM-YYYY", timestamp);
                    String time = UNIX.toGMT("", "hh:mm:ss a", timestamp);


                    String temp = mainObject.getString("temp");
                    String min_temp = mainObject.getString("temp_min");
                    String max_temp = mainObject.getString("temp_max");
                    String pressure = mainObject.getString("pressure");
                    String seaLevel = mainObject.getString("sea_level");
                    String humidity = mainObject.getString("humidity");

                    String main = weatherObject.getString("main");
                    String description = weatherObject.getString("description");
                    String icon = weatherObject.getString("icon");


                    String speed = windObject.getString("speed");
                    String deg = windObject.getString("deg");

                    //String rain = windObject.getString("3h");


                    weather.setDtTxt(text_date);
                    weather.setDate(date);
                    weather.setTime(time);
                    weather.setTemperature(temp);
                    weather.setMinTemperature(min_temp);
                    weather.setMaxTemperature(max_temp);

                    weather.setPressure(pressure);
                    weather.setSeaLevel(seaLevel);
                    weather.setHumidity(humidity);

                    weather.setWeatherDescription(description);
                    weather.setWeatherIcon(icon);
                    weather.setWeatherMain(main);

                    weather.setWindSpeed(speed);
                    weather.setWindDeg(deg);

                    weather.setRain(" ");

                    weatherList.add(weather);
                } catch (JSONException e) {
                    e.printStackTrace();
                    MyServices.log("ERROR" + e.getMessage());
                }
            }
        } else {
            MyServices.log("Weather Null");
            return null;
        }


        return weatherList;

}}
