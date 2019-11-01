package com.android1.restopicc.util;

import com.android1.restopicc.models.Restaurant;

import java.util.ArrayList;

public interface OnRestaurantSelectedListener {
    public void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants, String source);}
