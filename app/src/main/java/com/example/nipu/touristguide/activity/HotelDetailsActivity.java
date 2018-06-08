package com.example.nipu.touristguide.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nipu.touristguide.R;
import com.example.nipu.touristguide.adapters.RecyclerAdapter;
import com.example.nipu.touristguide.modelclass.DivInformation;
import com.example.nipu.touristguide.modelclass.Hotel;
import com.example.nipu.touristguide.network.ApiClient;
import com.example.nipu.touristguide.service.ApiInterface;
import com.example.nipu.touristguide.service.MyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.example.nipu.touristguide.firstablayout.HomeFragment.recyclerView;
import static com.example.nipu.touristguide.network.ApiClient.Base_URL;

public class HotelDetailsActivity extends AppCompatActivity {

    private int id;
    private ImageView imgHotel;
    TextView tvTitle, tvDetails, tvContact, tvAddress;
    ApiInterface apiInterface;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        id = getIntent().getExtras().getInt("id", 0);

        ini();
        Callbyhotelid();


    }

    public void ini() {
        imgHotel = (ImageView) findViewById(R.id.hotel_des_img);
        tvTitle = (TextView) findViewById(R.id.hotel_des_title);
        tvDetails = (TextView) findViewById(R.id.hotel_des_description);
        tvContact = (TextView) findViewById(R.id.hotel_des_contact);
        tvAddress = (TextView) findViewById(R.id.hotel_des_address);
    }

    public void Callbyhotelid() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Hotel>> call = apiInterface.getHotel(id);

        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, retrofit2.Response<List<Hotel>> response) {
                List<Hotel> hotel = response.body();

                if (hotel.size() > 0) {
                    Hotel h = hotel.get(0);
                    tvTitle.setText(h.getTitle() + " ");
                    tvDetails.setText(h.getDescription() + " ");

                    if (h.getContact() == null) {
                        tvContact.setVisibility(View.GONE);
                    } else {
                        tvContact.setText("Contact Us:\n" + h.getContact());
                    }
                    if (h.getAddress() == null) {
                        tvAddress.setVisibility(View.GONE);
                    } else {
                        tvAddress.setText("Address:\n" + h.getAddress());
                    }

                    Glide.with(HotelDetailsActivity.this).load( ApiClient.Base_URL + "/tourim/images/"+h.getImage()).into(imgHotel);
                    Log.d("JESMIN:: Hotel", ApiClient.Base_URL + "/tourim/images/"+h.getImage());//run koroto
                    Log.d("JESMIN:: Hotel",h.toString());//run koroto
                } else {
                    //shoe msg
                    Log.d("JESMIN:: Hotel","DDD");//run koroto
                }

            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "For hotels Internet Connection Failed!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_placedetailsactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) { //for back icon
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
