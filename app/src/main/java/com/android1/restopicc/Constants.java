package com.android1.restopicc;

public class Constants {
    public static final String YELP_TOKEN = BuildConfig.YELP_API_KEY;
    public static final String YELP_BASE_URL = "https://api.yelp.com/v3/businesses/search";
    public static final String YELP_LOCATION_QUERY_PARAMETER = "location";

    //Data persistence _ Shared Preferences
    public static final String PREFERENCES_LOCATION_KEY = "location";

    //Child node name for saving the values for searched Locations in Firebase
    public static final String FIREBASE_CHILD_SEARCHED_LOCATION = "searchedLocation";

    //Restaurant node key
    public static final String FIREBASE_CHILD_RESTAURANTS = "restaurants";
    public static final String FIREBASE_QUERY_INDEX = "index";

    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_RESTAURANTS = "restaurants";
    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";
}
