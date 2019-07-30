package com.guideme.guideme.ui.tour_guide;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guideme.guideme.R;
import com.guideme.guideme.data.DataManager;
import com.guideme.guideme.data.models.TourGuide;

import java.util.ArrayList;
import java.util.List;

public class AvailableTourGuidesActivity extends AppCompatActivity {

    private DataManager dataManager = new DataManager();
    ArrayList<String> selectedT;
    ArrayList<String> selectedC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_available_tour_guides);

        Toolbar toolbar = findViewById(R.id.toolbar);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle b = getIntent().getExtras();
        selectedC = b.getStringArrayList("cities");
        selectedT = b.getStringArrayList("tags");
        final List<TourGuide> guides = new ArrayList<>();
        final List<String> trips = new ArrayList<>();
        trips.add("Giza Necopolis");
        trips.add("Salah El Din Citadel");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TourGuidesAdapter(this, guides));

        dataManager.getTourGuides(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot result) {
                for (QueryDocumentSnapshot document : result) {
                    String name = (String) document.get("name");
                    String phoneNumber = (String) document.get("phone_number");
                    String photo = (String) document.get("photo");
                    double rating = (Double) document.get("rating");
                    String origin = (String) document.get("origin");
                    List<String> perks = (List<String>) document.get("perks");
                    List<String> activities = (List<String>) document.get("activities");
                    if ((selectedC.contains(origin) || selectedC.isEmpty()))
                        for (String activity : activities) {
                            if (selectedT.contains(activity) || selectedT.isEmpty()) {
                                guides.add(new TourGuide(name, phoneNumber, photo, rating, origin, perks, activities));
                                break;
                            }
                        }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
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
