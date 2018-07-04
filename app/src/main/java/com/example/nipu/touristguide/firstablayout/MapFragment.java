package com.example.nipu.touristguide.firstablayout;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nipu.touristguide.R;
import com.example.nipu.touristguide.service.MyService;
import com.example.nipu.touristguide.service.MyServices;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements View.OnClickListener {

    private ImageView hospital, police, bank, restaurant;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mapfragment, container, false);

        hospital = (ImageView) rootView.findViewById(R.id.hospital);
        police = (ImageView) rootView.findViewById(R.id.police);
        restaurant = (ImageView) rootView.findViewById(R.id.restaurant);
        bank = (ImageView) rootView.findViewById(R.id.bank);

        hospital.setOnClickListener(this);
        police.setOnClickListener(this);
        restaurant.setOnClickListener(this);
        bank.setOnClickListener(this);
        return rootView;

    }
    //getlocation

    private void getLocation(final String placeName) {
        SmartLocation.with(getActivity()).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Uri hospital_uri;
                        //Toast.makeText(getContext(),location.getLatitude()+"ll",Toast.LENGTH_LONG).show();
                        Log.d("JAN===",location.getLatitude()+"ll");
                        if (location != null) {
                            hospital_uri = getUri(location, placeName);
                        } else {
                            hospital_uri = Uri.parse("http://maps.google.com/maps?z=zoom&q=" + "hospitals");
                        }
                        Intent hospital = new Intent(Intent.ACTION_VIEW, hospital_uri);
                        hospital.setPackage("com.google.android.apps.maps");
                        startActivity(hospital);
                    }
                });
    }

    // Check if GPS is available
    private boolean isGPS() {
        return SmartLocation.with(getContext()).location().state().isGpsAvailable();
    }

    // Check if Network is available
    private boolean isNetwork() {
        return SmartLocation.with(getContext()).location().state().isNetworkAvailable();
    }

    @Override
    public void onDestroy() {
        SmartLocation.with(getContext()).location().stop();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if ((isGPS() && isNetwork())) {
            switch (v.getId()) {
                case R.id.hospital:
                    getLocation("hospitals");
                    break;
                case R.id.police:
                    getLocation("police stations");
                    break;
                case R.id.restaurant:
                    getLocation("restaurants");
                    break;
                case R.id.bank:
                    getLocation("banks");
                    break;
            }
        } else if(!isGPS()){
            // show a message to enable gps and also internet
//            Toast.makeText(getContext(), "Please Enable GPS and Internet!!", Toast.LENGTH_SHORT).show();
//            MyServices.alertDialog(getContext(), "Please Enable GPS and Internet");
           // MyServices.alertDialog(getContext(),"Please Enable GPS and Internet");
            AlertDialog.Builder dialog = null;
            dialog.setPositiveButton("GPS Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getContext().startActivity(gpsIntent);
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();

        }
        // network change
        else if(!isNetwork()){
            // show a message to enable gps and also internet
//            Toast.makeText(getContext(), "Please Enable GPS and Internet!!", Toast.LENGTH_SHORT).show();
//            MyServices.alertDialog(getContext(), "Please Enable GPS and Internet");
            // MyServices.alertDialog(getContext(),"Please Enable GPS and Internet");
            AlertDialog.Builder dialogN = null;
            dialogN.setPositiveButton("Network Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent gpsIntent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                    getContext().startActivity(gpsIntent);
                }
            });
            dialogN.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogN.show();

        }
    }

    private Uri getUri(Location loc, String locName) {
        return Uri.parse("geo:" + loc.getLatitude() + "," + loc.getLongitude() + ",0?q=" + locName + "&z=zoom");
    }
}