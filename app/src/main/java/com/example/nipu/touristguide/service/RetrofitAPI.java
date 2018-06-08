package com.example.nipu.touristguide.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NIPU on 5/3/2018.
 */

public interface RetrofitAPI {

    @GET("/data/2.5/forecast?")
    Call<ResponseBody> getWeather(@Query("q") String cityName, @Query("mode") String mode,
                                  @Query("units") String units, @Query("appid") String appid); //f to c unit
}
