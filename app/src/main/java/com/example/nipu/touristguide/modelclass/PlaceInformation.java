package com.example.nipu.touristguide.modelclass;

import com.google.gson.annotations.SerializedName;



public class PlaceInformation {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private String type;

    @SerializedName("division")
    private String division;

    @SerializedName("district")
    private String district;

    @SerializedName("image")
    private String image;

    @SerializedName("overview")
    private String overview;

    @SerializedName("visiting_time")
    private String visiting_time;

    @SerializedName("contact")
    private String contact;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVisiting_time() {
        return visiting_time;
    }

    public void setVisiting_time(String visiting_time) {
        this.visiting_time = visiting_time;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "PlaceInformation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", division='" + division + '\'' +
                ", district='" + district + '\'' +
                ", image='" + image + '\'' +
                ", overview='" + overview + '\'' +
                ", visiting_time='" + visiting_time + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
