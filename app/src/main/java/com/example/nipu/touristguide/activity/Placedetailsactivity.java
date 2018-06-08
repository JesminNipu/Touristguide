package com.example.nipu.touristguide.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nipu.touristguide.modelclass.PlaceInformation;
import com.example.nipu.touristguide.network.ApiClient;
import com.example.nipu.touristguide.firstablayout.One;
import com.example.nipu.touristguide.R;
import com.example.nipu.touristguide.service.ApiInterface;
import com.example.nipu.touristguide.service.MyService;
import com.example.nipu.touristguide.firstablayout.Three;
import com.example.nipu.touristguide.firstablayout.Two;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by NIPU on 1/21/2018.
 */

public class Placedetailsactivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private int id;
    PlaceInformation placeInformation;
    ApiInterface apiInterface;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placedetailsactivity);
        img = (ImageView) findViewById(R.id.pls_image);
        id = getIntent().getExtras().getInt("id", 0);

        progressDialog = MyService.progressDialog(Placedetailsactivity.this, "Loading...");
        progressDialog.show();

        Callbyplceid();


    }
    public void Callbyplceid()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<PlaceInformation>> call = apiInterface.getPlace(id);

        call.enqueue(new Callback<List<PlaceInformation>>() {

            @Override
            public void onResponse(Call<List<PlaceInformation>> call, retrofit2.Response<List<PlaceInformation>> response) {
                progressDialog.dismiss();
                List<PlaceInformation> placeInformationList = response.body();
                MyService.L("succ");
                if (placeInformationList.size() > 0) {
                    placeInformation = placeInformationList.get(0);
                    MyService.L(placeInformation.toString() + "pppp");
                    Glide.with(getApplicationContext()).load(placeInformation.getImage()).into(img);
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), placeInformation);
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    mViewPager.setAdapter(mSectionsPagerAdapter);

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_2);
                    tabLayout.setupWithViewPager(mViewPager);
                } else {
                    placeInformation = null;
                    MyService.L("fail");

                }

            }

            @Override
            public void onFailure(Call<List<PlaceInformation>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Internet Connection Failed!!!", Toast.LENGTH_LONG).show();
                MyService.L("nt cnt");

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

        int id = item.getItemId();


        if (id == R.id.action_back) { //for back icon
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_placedetailsactivity, container, false);

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        PlaceInformation placeInformation;
        ApiInterface apiInterface;
        int id;

        public SectionsPagerAdapter(FragmentManager fm, PlaceInformation placeInformation) {
            super(fm);
            this.id = id;
            this.placeInformation = placeInformation;
            MyService.L("adapter");
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    //One one1 = new One();
                    return One.newInstance(placeInformation.getOverview() + " ");
                case 1:
                    //Two two2 = new Two();
                    return Two.newInstance(placeInformation.getVisiting_time() + " ");
                case 2:
                    //Three three3 = new Three();
                    return Three.newInstance(placeInformation.getContact() + " ");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "OVERVIEW";
                case 1:
                    return "VISITING TIME";
                case 2:
                    return "CONTACT";


            }
            return null;
        }
    }
}
