package com.guideme.guideme.ui.trip_creation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TripPlace;
import com.guideme.guideme.ui.trips_listing.fragments.VerticalStepperAdapterFragment;

import java.util.ArrayList;

public class TripOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trips_listing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Button doneButton = findViewById(R.id.doneButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ArrayList<TripPlace> places = getIntent().getExtras().getParcelableArrayList("places");
        Fragment mVerticalStepperAdapterDemoFragment = new VerticalStepperAdapterFragment(places);

        if (savedInstanceState == null) {
            replaceFragment(mVerticalStepperAdapterDemoFragment);
        }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
