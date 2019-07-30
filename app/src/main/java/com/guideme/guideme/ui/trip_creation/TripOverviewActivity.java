package com.guideme.guideme.ui.trip_creation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.guideme.guideme.R;
import com.guideme.guideme.ui.trips_listing.fragments.VerticalStepperAdapterFragment;
import com.guideme.guideme.ui.trips_listing.fragments.VerticalStepperDemoFragment;

public class TripOverviewActivity extends AppCompatActivity {

    private Fragment mVerticalStepperDemoFragment = new VerticalStepperDemoFragment(),
            mVerticalStepperAdapterDemoFragment = new VerticalStepperAdapterFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trips_listing);
        if (savedInstanceState == null) {
            replaceFragment(mVerticalStepperAdapterDemoFragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
