package com.example.nipu.touristguide.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SubMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nipu.touristguide.network.ApiClient;
import com.example.nipu.touristguide.service.ApiInterface;
import com.example.nipu.touristguide.modelclass.DivInformation;
import com.example.nipu.touristguide.service.MyService;
import com.example.nipu.touristguide.R;
import com.example.nipu.touristguide.adapters.RecyclerAdapter;
import com.example.nipu.touristguide.modelclass.Response;
import com.example.nipu.touristguide.adapters.TabsPager;
import com.example.nipu.touristguide.service.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.example.nipu.touristguide.firstablayout.HomeFragment.recyclerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static String INTERNET = Manifest.permission.INTERNET;
    private static String NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
    private static String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String[] PERMISSIONS = {INTERNET, NETWORK_STATE, COARSE_LOCATION, FINE_LOCATION};

    private DrawerLayout drawer;
    public static TextView textView;
    ArrayList<Object> items = new ArrayList<Object>();
    // private secpagerhome secpagerhomeAdapter; //tablayout 2 er adapter
    private AppBarLayout appBar;

    private RecyclerView.LayoutManager layoutManager;
    private List<DivInformation> divInformationList;//vaiya agaer project moto koiren na//oi project e may b tabcreatejamela ace
    private RecyclerAdapter adapter;

    ApiInterface apiInterface; //for network call
    Retrofit retrofit;
    ViewPager viewPager;
    TabLayout tabLayout;
    TabsPager TabPager;
    String titles[] = new String[]{"Home", "Hotels", "Nearby Services", "Weather"};
    Integer Numboftabs = 4;

    private static final int REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* for drawer*/
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setNavigationViewListner();//setnav view listener

        //set viewpager adapter for 1st tablayot
        //mViewPager = (ViewPager) findViewById(R.id.view_pager);
        // sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // mViewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs); //cast tablayout
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager); // cast viewpager
        //now use viewpager as custom viewpager.need to pass fragment as constructr parameter sp pass getSupportFragmentManager then set adapter of viewpager
        //at last need to add tabLayout.setupWithViewPager
        TabsPager TabPager = new TabsPager(getSupportFragmentManager());
        viewPager.setAdapter(TabPager);
        tabLayout.setupWithViewPager(viewPager);  //if not set these 2 statement not work
        //set viewpager adapter for second tablayot
        // mViewPager1 = (ViewPager) findViewById(R.id.view_pagerhome);
        //secpagerhomeAdapter = new secpagerhome(getSupportFragmentManager());
        //mViewPager1.setAdapter(secpagerhomeAdapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();
        final SubMenu subMenu = m.addSubMenu("ALL DIVISION NAME");



        /*1st Api call for  (divname) load in nav_drawer */

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //call api method for return type arraylist of diviname
        Call<ArrayList<Response>> call = apiInterface.getDivname(); //nav drawer er menu call er jnnk korecilalm

        call.enqueue(new Callback<ArrayList<Response>>() {
            @Override
            public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
                ArrayList<Response> ret = response.body();
                if (ret != null)
                    for (int i = 0; i < ret.size(); i++) {
                        subMenu.add(0, i, 0, ret.get(i).getDistName());
                    }
            }

            @Override
            public void onFailure(Call<ArrayList<Response>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Connection Failed!!!", Toast.LENGTH_LONG).show();
            }
        });


        navigationView.setNavigationItemSelectedListener(this);


        getAllData();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getPermission();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getPermission() {
        if(isAccepted()){
            requestPermissions(PERMISSIONS,REQUEST);
        }else{
            //msg for allowing permission
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private boolean isAccepted() {
        return checkSelfPermission(INTERNET)!= PackageManager.PERMISSION_GRANTED
                ||checkSelfPermission(NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED
                ||checkSelfPermission(COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                ||checkSelfPermission(FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==REQUEST){
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // startMainActivity();
            }else{
                Toast.makeText(this,"!!!Permission Denied!!!",Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void getAllData() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<DivInformation>> call = apiInterface.getdivInfo(null, "Dhaka");

        call.enqueue(new Callback<List<DivInformation>>() {
            @Override
            public void onResponse(Call<List<DivInformation>> call, retrofit2.Response<List<DivInformation>> response) {
                List<DivInformation> divInformationList = response.body();
                if (divInformationList != null && divInformationList.size() > 0) {
                    MyService.L(divInformationList.get(0).toString() + "");
                    adapter = new RecyclerAdapter(divInformationList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } else {
                    //no data send msg to user
                }

            }

            @Override
            public void onFailure(Call<List<DivInformation>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internet Connection Failed!!!", Toast.LENGTH_LONG).show();
                MyService.L(t.getMessage());
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } /*else if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Menu", Toast.LENGTH_SHORT).show();
            return true;
        }*///action search icon drawble a rakhbo
        return super.onOptionsItemSelected(item);
    }

    /*new */
    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    //All a data show korer jnno
    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();//nicher moto call ta hole e to holo naki?
        //item id ki 0 1 arokom hmm dimyesilen? hmm upore loop use korechi
        //eigula use hoy drwer off er jnno

        //division name ki ase app a mane nav a hmmm oita man IP dile asbe ip den
        String div = item.getTitleCondensed().toString();

        infoFatchJnnoMethod(div);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void infoFatchJnnoMethod(String div) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //now call api method

        Call<List<DivInformation>> call = apiInterface.getdivInfo(null, div);

        //now handle the result
        call.enqueue(new Callback<List<DivInformation>>() {
            @Override
            public void onResponse(Call<List<DivInformation>> call, retrofit2.Response<List<DivInformation>> response) {
                List<DivInformation> divInformationList = response.body();
                if (divInformationList.size() > 0) {
                    MyService.L(divInformationList.get(0).toString() + "");
                    adapter.dataChanged((ArrayList<DivInformation>) divInformationList); //lndmark select,park select etc select jnno
                } else {
                    //no data
                }

            }

            @Override
            public void onFailure(Call<List<DivInformation>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internet Connection Failed!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public RecyclerAdapter changeCat() {
        return adapter;
    }
}
