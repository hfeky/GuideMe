package com.guideme.guideme.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guideme.guideme.R;
import com.guideme.guideme.data.DataManager;
import com.guideme.guideme.data.models.TourGuide;
import com.guideme.guideme.ui.BookTour.ChooseFilters;

import java.util.ArrayList;
import java.util.List;

public class GuidesYouMightLikeActivity extends AppCompatActivity {

    private DataManager dataManager = new DataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides_you_might_like);

        Bundle b = this.getIntent().getExtras();
        ArrayList<String> selectedT = b.getStringArrayList("tags");
        ArrayList<String> selectedC = b.getStringArrayList("city");

        System.out.println(selectedT);
        System.out.println(selectedC);

        Toolbar toolbar = findViewById(R.id.toolbar);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final List<TourGuide> guides = new ArrayList<>();
        final List<String> trips = new ArrayList<>();
        trips.add("Giza Necopolis");
        trips.add("Salah El Din Citadel");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GuidesAdapter(this, guides));

        dataManager.getTourGuides(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot result) {
                for (QueryDocumentSnapshot document : result) {
                    String name = (String) document.get("name");
                    String photo = (String) document.get("photo");
                    double rating = (Double) document.get("rating");
                    String origin = (String) document.get("origin");
                    List<String> perks = (List<String>) document.get("perks");
                    guides.add(new TourGuide(name, photo, rating, origin, perks));
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


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(this, ChooseFilters.class));
        overridePendingTransition(
                0,
                R.anim.slide_off
        );
    }
}
