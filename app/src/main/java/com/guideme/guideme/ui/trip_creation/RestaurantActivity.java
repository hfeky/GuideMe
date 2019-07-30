package com.guideme.guideme.ui.trip_creation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guideme.guideme.R;
import com.guideme.guideme.data.DataManager;
import com.guideme.guideme.data.models.CommonPhrase;
import com.guideme.guideme.data.models.Restaurant;
import com.guideme.guideme.ui.dashboard.CommonPhrasesAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        DataManager dataManager = new DataManager();

        List<Restaurant> restaurants = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RestaurantsAdapter(this, restaurants));

        dataManager.getRestaurants("city_id", new OnSuccessListener<QuerySnapshot>() {
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
