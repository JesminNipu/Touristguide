package com.example.nipu.touristguide.modelclass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NIPU on 1/14/2018.
 */

public class Response {

    @SerializedName("division_name")
    String division_name;

    public String getDistName() {
        return this.division_name;
    }

    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }

    @Override
    public String toString() {
        return "Response{" +
                "division_name='" + division_name + '\'' +
                '}';
    }
}
