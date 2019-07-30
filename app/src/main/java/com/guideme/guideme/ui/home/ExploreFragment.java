package com.guideme.guideme.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.guideme.guideme.R;
import com.guideme.guideme.ui.common.AutoHideFAB;
import com.guideme.guideme.ui.trips_listing.TripsActivity;

public class ExploreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_explore, container, false);

        CardView topCover = view.findViewById(R.id.topCover);

        CardView cairoCity = view.findViewById(R.id.cairoCity);
        CardView alexandriaCity = view.findViewById(R.id.alexandriaCity);
        CardView gizaCity = view.findViewById(R.id.gizaCity);
        CardView luxorCity = view.findViewById(R.id.luxorCity);
        CardView northCoastCity = view.findViewById(R.id.northCoastCity);
        CardView sharmCity = view.findViewById(R.id.sharmCity);

        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);

        NestedScrollView scrollView = view.findViewById(R.id.scrollView);
        AutoHideFAB fab = ((MainActivity) getContext()).getAddFab();
        fab.setupWithNestedScrollView(scrollView);

        topCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TripsActivity.class));
            }
        });

        cairoCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("city", 3);
                startActivity(intent);
            }
        });

        alexandriaCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("city", 1);
                startActivity(intent);
            }
        });

        gizaCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("city", 6);
                startActivity(intent);
            }
        });

        luxorCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("city", 8);
                startActivity(intent);
            }
        });

        northCoastCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("city", 3);
                startActivity(intent);
            }
        });

        sharmCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("city", 6);
                startActivity(intent);
            }
        });

        String[] chips = getResources().getStringArray(R.array.tags);

        for (String tag : chips) {
            Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.item_trip_tag, null, false);
            chip.setText(tag);
            chip.setClickable(true);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), TripsActivity.class);
                    intent.putExtra("tag", chip.getText().toString());
                    startActivity(intent);
                }
            });
            chipGroup.addView(chip);
        }

        return view;
    }
}
