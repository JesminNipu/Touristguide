package com.example.nipu.touristguide.service;

import com.example.nipu.touristguide.modelclass.DivInformation;
import com.example.nipu.touristguide.modelclass.Hotel;
import com.example.nipu.touristguide.modelclass.PlaceInformation;
import com.example.nipu.touristguide.modelclass.Response;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NIPU on 1/14/2018.
 */

public interface ApiInterface {

    @GET("/divisions.php")
    Call<ArrayList<Response>> getDivname();

    @GET("/places.php")
    Call<List<DivInformation>> getdivInfo(@Query("type") String type, @Query("division") String division);

    @GET("/place.php")
    Call<List<PlaceInformation>>getPlace(@Query("id") int id);

    @GET("/hotels.php")
    Call<ArrayList<Hotel>> getHotels(@Query("division") String division);

    @GET("/hotel.php")
    Call<List<Hotel>> getHotel(@Query("id") int id);

//    @GET("/data/2.5/forecast?")
//    Call<ResponseBody> getWeather(@Query("q") String cityName, @Query("mode") String mode,
//                                  @Query("units") String units, @Query("appid") String appid); //f to c unit
}

