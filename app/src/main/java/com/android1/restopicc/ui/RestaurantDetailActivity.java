package com.android1.restopicc.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.android1.restopicc.Constants;
import com.android1.restopicc.R;
import com.android1.restopicc.adapters.RestaurantPagerAdapter;
import com.android1.restopicc.models.Restaurant;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailActivity extends AppCompatActivity {
    @BindView(R.id.pagerHeader)
    ViewPager mViewPager;
    private String mSource;
    private RestaurantPagerAdapter adapterViewPager;
    ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        ButterKnife.bind(this);
        mSource = getIntent().getStringExtra(Constants.KEY_SOURCE);
        // This line should already exist, we're just adding mSource as an additional parameter:
        adapterViewPager = new RestaurantPagerAdapter(getSupportFragmentManager(), mRestaurants, mSource);
        mRestaurants = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_RESTAURANTS));
        int startingPosition = getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, 0);

        adapterViewPager = new RestaurantPagerAdapter(getSupportFragmentManager(), mRestaurants, mSource);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}