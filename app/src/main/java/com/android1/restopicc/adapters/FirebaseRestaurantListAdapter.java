package com.android1.restopicc.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android1.restopicc.R;
import com.android1.restopicc.models.Restaurant;
import com.android1.restopicc.ui.RestaurantDetailActivity;
import com.android1.restopicc.ui.SavedRestaurantListActivity;
import com.android1.restopicc.util.ItemTouchHelperAdapter;
import com.android1.restopicc.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseRestaurantListAdapter extends FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();


    public FirebaseRestaurantListAdapter(FirebaseRecyclerOptions<Restaurant> options,
                                         Query ref,
                                         SavedRestaurantListActivity onStartDragListener,
                                         Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
        mChildEventListener=mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mRestaurants.add(dataSnapshot.getValue(Restaurant.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseRestaurantViewHolder firebaseRestaurantViewHolder, int position, @NonNull Restaurant restaurant) {
        firebaseRestaurantViewHolder.bindRestaurant(restaurant);
        firebaseRestaurantViewHolder.mRestaurantImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseRestaurantViewHolder);
                }
                return false;
            }
        });
        firebaseRestaurantViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                intent.putExtra("position", firebaseRestaurantViewHolder.getAdapterPosition());
                intent.putExtra("restaurants", Parcels.wrap(mRestaurants));
                mContext.startActivity(intent);
            }
        });
    }
    @NonNull
    @Override
    public FirebaseRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item_drag, parent, false);
        return new FirebaseRestaurantViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mRestaurants, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position){
        mRestaurants.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Restaurant restaurant : mRestaurants) {
            int index = mRestaurants.indexOf(restaurant);
            DatabaseReference ref = getRef(index);
            restaurant.setIndex(Integer.toString(index));
            ref.setValue(restaurant);
        }
    }

    @Override
    public void stopListening() { super.stopListening(); mRef.removeEventListener(mChildEventListener); }
}
