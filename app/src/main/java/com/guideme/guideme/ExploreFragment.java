package com.guideme.guideme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

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

        CardView seaCategory = view.findViewById(R.id.seaCategory);
        CardView shoppingCategory = view.findViewById(R.id.shoppingCategory);
        CardView cultureCategory = view.findViewById(R.id.cultureCategory);
        CardView historyCategory = view.findViewById(R.id.historyCategory);
        CardView campingCategory = view.findViewById(R.id.campingCategory);
        CardView hikingCategory = view.findViewById(R.id.hikingCategory);

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

        seaCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("category", 5);
                startActivity(intent);
            }
        });

        shoppingCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("category", 6);
                startActivity(intent);
            }
        });

        cultureCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("category", 2);
                startActivity(intent);
            }
        });

        historyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("category", 4);
                startActivity(intent);
            }
        });

        campingCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("category", 1);
                startActivity(intent);
            }
        });

        hikingCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripsActivity.class);
                intent.putExtra("category", 3);
                startActivity(intent);
            }
        });

        return view;
    }
}
