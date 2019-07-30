package com.guideme.guideme.ui.trip_creation;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guideme.guideme.R;
import com.guideme.guideme.data.DataManager;
import com.guideme.guideme.data.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DataManager dataManager = new DataManager();

        List<Restaurant> restaurants = new ArrayList<>();
        String cityId = getIntent().getStringExtra("city_id");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RestaurantsAdapter(this, restaurants));

        dataManager.getRestaurants(cityId, new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot result) {
                for (DocumentSnapshot document : result) {
                    String placeId = (String) document.get("place_id");
                    String name = (String) document.get("name");
                    String description = (String) document.get("description");
                    restaurants.add(new Restaurant(placeId, name, description));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
