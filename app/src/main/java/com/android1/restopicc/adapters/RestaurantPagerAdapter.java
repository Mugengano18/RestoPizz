package com.android1.restopicc.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android1.restopicc.models.Restaurant;
import com.android1.restopicc.ui.RestaurantDetailFragment;

import java.util.ArrayList;

public class RestaurantPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Restaurant> mRestaurants;

    public RestaurantPagerAdapter(FragmentManager fm, ArrayList<Restaurant> restaurants){
        super(fm);
        mRestaurants = restaurants;
    }

    @Override
    public Fragment getItem(int position){
        return RestaurantDetailFragment.newInstance(mRestaurants.get(position));
    }

    @Override
    public int getCount(){
        return mRestaurants.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mRestaurants.get(position).getName();
    }
}
