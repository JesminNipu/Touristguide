package com.example.nipu.touristguide.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.nipu.touristguide.firstablayout.BookHotelsFragment;
import com.example.nipu.touristguide.firstablayout.HomeFragment;
import com.example.nipu.touristguide.firstablayout.MapFragment;
import com.example.nipu.touristguide.firstablayout.WeatherFragment;

/**
 * Created by NIPU on 1/14/2018.
 */

public class TabsPager extends FragmentStatePagerAdapter {

    String titles[] = new String[]{"Home","Book Hotels","Map","Weather"};

    public TabsPager(FragmentManager fm) {
        super(fm);
    }
    Integer tabnumber = 4;
    @Override
    public CharSequence getPageTitle(int position) {//new add korsi

        return titles[position]; //return super.getPageTitle(position); eta chilo
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                HomeFragment hometab = new HomeFragment();
                return  hometab;
            case 1:
                BookHotelsFragment bookhotelstab = new BookHotelsFragment();
                return bookhotelstab;
            case 2:
                MapFragment maptab = new MapFragment();
                return maptab;
            case 3:
                WeatherFragment weathertab = new WeatherFragment();
                return weathertab;

        }

        return null;
    }

    @Override
    public int getCount() {
        return tabnumber;
    }
}
