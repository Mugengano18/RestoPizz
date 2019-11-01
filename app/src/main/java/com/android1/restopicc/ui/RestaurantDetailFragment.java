package com.android1.restopicc.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android1.restopicc.Constants;
import com.android1.restopicc.R;
import com.android1.restopicc.models.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.restaurantImageView)
    ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView)
    TextView mNameLabel;
    @BindView(R.id.cuisineTextView) TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;

    private Restaurant mRestaurant;
    private String mSource;
    private ArrayList<Restaurant> mRestaurants;
    private int mPosition;

    public RestaurantDetailFragment() {
        // Required empty public constructor
    }

    public static RestaurantDetailFragment newInstance(ArrayList<Restaurant> restaurants, Integer position, String source){
        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(restaurants));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);

        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestaurants = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_RESTAURANTS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mRestaurant = mRestaurants.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveRestaurantButton.setVisibility(View.GONE);
        } else {
            // This line of code should already exist. Make sure it now resides in this conditional:
            mSaveRestaurantButton.setOnClickListener(this);
        }
        ButterKnife.bind(this, view);
        Picasso.get()
                .load(mRestaurant.getImageUrl())
                .into(mImageLabel);
        mNameLabel.setText(mRestaurant.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getCategories()));
        mRatingLabel.setText(Double.toString(mRestaurant.getRating()) + "/5");
        mPhoneLabel.setText(mRestaurant.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getAddress()));
        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        mSaveRestaurantButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        if (v == mWebsiteLabel){
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRestaurant.getWebsite()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mRestaurant.getLatitude()
                            + "," + mRestaurant.getLongitude()
                            + "?q=(" + mRestaurant.getName() + ")"));
            startActivity(mapIntent);
        } if (v == mSaveRestaurantButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                    .child(uid);
            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mRestaurant.setPushId(pushId);
            pushRef.setValue(mRestaurant);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}
