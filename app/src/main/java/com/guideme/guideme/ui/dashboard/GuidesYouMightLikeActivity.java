package com.guideme.guideme.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TourGuide;

import java.util.ArrayList;
import java.util.List;

public class GuidesYouMightLikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides_you_might_like);

        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        List<TourGuide> guides = new ArrayList<>();
        List<String> places1 = new ArrayList<>();
        places1.add("Giza Necopolis");
        places1.add("Salah El Din Citadel");
        List<String> places2 = new ArrayList<>();
        places2.add("Ghaliet Ecolodge & Spa");
        places2.add("Fetnas Island");
        guides.add(new TourGuide("Ashraf Mansour", R.drawable.ic_tour_guide, 2.4, places1));
        guides.add(new TourGuide("Lord Ossama", R.drawable.ic_2029245, 5, places2));
        guides.add(new TourGuide("Ge Eiad", R.drawable.ic_tour_guide, 5, places1));
        guides.add(new TourGuide("Ge Passant", R.drawable.ic_tour_guide, 5, places1));
        guides.add(new TourGuide("Hamada ElPrince",R.drawable.ic_tour_guide , 4.2,places1));
        guides.add(new TourGuide("Hany Younnes",R.drawable.ic_tour_guide , 3.9,places2));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GuidesAdapter(this, guides));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
