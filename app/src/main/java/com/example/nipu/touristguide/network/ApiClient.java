package com.example.nipu.touristguide.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NIPU on 1/14/2018.
 */

public class ApiClient {
   public static final String Base_URL = "https://nipu.parvez.vip";//backup hisebe aita use koro...aita amar server theke astece
    //public static final String Base_URL = "http://192.168.0.104";
    // aita akta vul aita base url na http://10.130.120.82/ ata base url r atai change korte hoy

    public static Retrofit retrofit = null;

    public static Retrofit getApiClient() { //

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
