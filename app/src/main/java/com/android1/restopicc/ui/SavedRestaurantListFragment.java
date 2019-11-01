package com.android1.restopicc.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android1.restopicc.Constants;
import com.android1.restopicc.R;
import com.android1.restopicc.adapters.FirebaseRestaurantListAdapter;
import com.android1.restopicc.adapters.FirebaseRestaurantViewHolder;
import com.android1.restopicc.models.Restaurant;
import com.android1.restopicc.util.OnStartDragListener;
import com.android1.restopicc.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedRestaurantListFragment extends Fragment implements OnStartDragListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private FirebaseRestaurantListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    public SavedRestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_restaurant_list, container, false);
        ButterKnife.bind(this, view);
        setUpFirebaseAdapter();
        return view;
    }

    private void setUpFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        FirebaseRecyclerOptions<Restaurant> options =
                new FirebaseRecyclerOptions.Builder<Restaurant>()
                        .setQuery(query, Restaurant.class)
                        .build();

        //  In line below, we change 4th parameter 'this' to 'getActivity()'
        //  because fragments do not have own context:
        mFirebaseAdapter = new FirebaseRestaurantListAdapter(options, query, this, getActivity());
        //In line below, we change 'this' to 'getActivity()' because fragments do not have own context:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mFirebaseAdapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }

    @Override
    public void onStartDrag(FirebaseRestaurantViewHolder viewHolder) {

    }
}
