package com.guideme.guideme.ui.trip_creation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.guideme.guideme.R;
import com.guideme.guideme.data.DataManager;
import com.guideme.guideme.data.models.TourGuide;
import com.guideme.guideme.data.models.Trip;
import com.guideme.guideme.data.models.TripPlace;
import com.guideme.guideme.ui.trips_listing.fragments.VerticalStepperAdapterFragment;
import com.guideme.guideme.ui.trips_listing.fragments.VerticalStepperDemoFragment;

import java.util.ArrayList;
import java.util.List;

public class TripOverviewActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trips_listing);

        ArrayList<TripPlace> places = getIntent().getExtras().getParcelableArrayList("places");
        Fragment mVerticalStepperAdapterDemoFragment = new VerticalStepperAdapterFragment(places);

        if (savedInstanceState == null) {
            replaceFragment(mVerticalStepperAdapterDemoFragment);
        }

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
