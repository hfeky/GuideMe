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

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_explore, container, false);

        CardView topCover = view.findViewById(R.id.topCover);

        CardView cairoCity = view.findViewById(R.id.cairoCity);
        CardView alexandriaCity = view.findViewById(R.id.alexandriaCity);
        CardView gizaCity = view.findViewById(R.id.gizaCity);
        CardView luxorCity = view.findViewById(R.id.luxorCity);
        CardView sahelCity = view.findViewById(R.id.sahelCity);
        CardView sharmCity = view.findViewById(R.id.sharmCity);

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
                intent.putExtra("city", 2);
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
                intent.putExtra("city", 4);
                startActivity(intent);
            }
        });

        luxorCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("city", 5);
                startActivity(intent);
            }
        });

        sahelCity.setOnClickListener(new View.OnClickListener() {
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
        ArrayList<Chip> chips = new ArrayList<>();
        for(int i = 0 ;i<12;i++){
            Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.item_trip_tag, null, false);
            chip.setClickable(true);
            chips.add(chip);
        }
        chips.get(0).setText("Sea");
        chips.get(1).setText("Golf");
        chips.get(2).setText("Wind Surfing");
        chips.get(3).setText("History");
        chips.get(4).setText("Culture");
        chips.get(5).setText("Desert");
        chips.get(6).setText("Snorkling");
        chips.get(7).setText("Yacht");
        chips.get(8).setText("Recreation");
        chips.get(9).setText("Diving");
        chips.get(10).setText("Shopping");
        chips.get(11).setText("Landmark");
        ChipGroup chipGroup = view.findViewById(R.id.chip_group);
        for(int i = 0 ;i<chips.size();i++){
            chipGroup.addView(chips.get(i));
        }


        return view;
    }
}
