package com.android1.restopicc.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRestaurantListActivity extends AppCompatActivity implements OnStartDragListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

    }


    @Override
    public void onStartDrag(FirebaseRestaurantViewHolder viewHolder) {

    }
}
