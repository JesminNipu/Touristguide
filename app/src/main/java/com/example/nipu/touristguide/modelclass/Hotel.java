package com.example.nipu.touristguide.modelclass;

import com.google.gson.annotations.SerializedName;



public class Hotel {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("address")
    private String address;

    @SerializedName("contact")
    private String contact;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    @SerializedName("division_name")
    private String division_name;

    @SerializedName("price_type")
    private String price_type;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDivision_name() {
        return division_name;
    }

    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", division_name='" + division_name + '\'' +
                ", price_type='" + price_type + '\'' +
                '}';
    }
}
