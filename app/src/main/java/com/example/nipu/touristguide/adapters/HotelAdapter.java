package com.example.nipu.touristguide.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nipu.touristguide.R;
import com.example.nipu.touristguide.activity.HotelDetailsActivity;
import com.example.nipu.touristguide.activity.Placedetailsactivity;
import com.example.nipu.touristguide.modelclass.DivInformation;
import com.example.nipu.touristguide.modelclass.Hotel;
import com.example.nipu.touristguide.service.MyService;

import java.util.ArrayList;
import java.util.Locale;



public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private Activity activity;
    private ArrayList<Hotel> hotels, search;


    public HotelAdapter(Activity activity, ArrayList<Hotel> hotels) {
        MyService.L("HotelAdapter" + hotels.size());
        this.activity = activity;
        this.hotels = hotels;

        search = new ArrayList<Hotel>();
        search.addAll(hotels);
    }

    public void dataChanged(ArrayList<Hotel> hotels) {//division wise hotel chnged
        this.hotels = hotels;
        search.clear();
        search.addAll(hotels);
        notifyDataSetChanged();
    }

    public void searchHotel(String searchText, String priType) {// searchText & priType 2i ta k aga lower kore rakhlam r ei 2i ta neya hoice compre jnno
        searchText = searchText.toLowerCase(Locale.getDefault());
        priType = priType.toLowerCase(Locale.getDefault()); //getDefault() chnge on depending on ur country liketoLowerCase(Locale.ENGLISH))
        hotels.clear();

        if (searchText.length() == 0)
        {
                if (priType.equalsIgnoreCase("All")) { /* String s2="javatpoint";
    String s3="JAVATPOINT";
    String s4="python";  s2.equalsIgnoreCase(s3) return korbe true but s2 or s3.equalsIgnoreCase(s4) return korbe false*/
                    hotels.addAll(search);
                } else { //best value ba lowest price hole
                    for (int i = 0; i < search.size(); i++) {
                        if (toStrng(search.get(i).getPrice_type()).equalsIgnoreCase(priType)) {
                            hotels.add(search.get(i));
                        }
                    }
                }
        }
        else
            {
            for (int i = 0; i < search.size(); i++) {
                    if (priType.equalsIgnoreCase("All")) {
                        if (toStrng(search.get(i).getTitle()).contains(searchText)) { //hotel title onujyi search
                            hotels.add(search.get(i));
                            /*String name="what do you know about me";
                            System.out.println(name.contains("do you know"));
                            System.out.println(name.contains("about"));
                            System.out.println(name.contains("hello")); o/p 1.true;2.true;3.false*/

                        }
                    }
                    else {
                        if (toStrng(search.get(i).getPrice_type()).equalsIgnoreCase(priType) && toStrng(search.get(i).getTitle()).contains(searchText)) {
                            hotels.add(search.get(i));
                        }
                    }

            }
        }
        notifyDataSetChanged();
    }

    public void filterPri(String pri) {
        pri = pri.toLowerCase(Locale.getDefault());
        hotels.clear();
        if (pri.equalsIgnoreCase("All")) {
            hotels.addAll(search);
        } else {
            for (int i = 0; i < search.size(); i++) {
                if (toStrng(search.get(i).getPrice_type()).equalsIgnoreCase(pri)) {
                    hotels.add(search.get(i));
                }
            }

        }
        notifyDataSetChanged();

    }

    private String toStrng(String value) {
        if (value != null) {
            return value.toLowerCase(Locale.getDefault());
        }
        return "";
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_hotel_layout, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        final Hotel hotel = hotels.get(position);
        holder.tvTitle.setText(hotel.getTitle() + " ");
        holder.tvDivi.setText(hotel.getDivision_name() + " ");
        holder.tvPri.setText(hotel.getPrice_type() + " ");
        // holder.Calories.setText("Calories:"+Integer.toString(calories.get(position).getCalories()));
        Glide.with(activity).load(hotel.getImage()).into(holder.img);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = hotel.getId();
                if (id > 0) {
                    Intent intent = new Intent(activity, HotelDetailsActivity.class);
                    intent.putExtra("id", id);
                    activity.startActivity(intent);
                } else {
                    //send msg
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTitle, tvDivi, tvPri;
        View mView;

        public HotelViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.hotel_image);
            tvTitle = (TextView) itemView.findViewById(R.id.hotel_title);
            tvDivi = (TextView) itemView.findViewById(R.id.hotel_divi);
            tvPri = (TextView) itemView.findViewById(R.id.hotel_pri);
            mView = itemView;
        }
    }

}
