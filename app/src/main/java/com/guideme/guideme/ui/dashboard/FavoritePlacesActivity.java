package com.guideme.guideme.ui.dashboard;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TripPlace;

import java.util.ArrayList;

import io.paperdb.Paper;

public class FavoritePlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_favorite_places);

        TripPlace place = getIntent().getParcelableExtra("place");
        Paper.init(this);
        ArrayList<TripPlace> trips = Paper.book().read("trips");
        if(trips==null){
            trips = new ArrayList<>();
        }
//        if(place!=null)
//            trips.add(place);
//        Paper.book().write("trips", trips);
        //TODO TEST THE FUCK OUT OF THIS CODE
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayout noFavoritePlaces = findViewById(R.id.noFavoritePlaces);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FavouriteAdapter(this, trips));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
