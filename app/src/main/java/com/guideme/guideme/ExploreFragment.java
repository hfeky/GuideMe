package com.guideme.guideme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
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

        topCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cairoCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        alexandriaCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gizaCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        luxorCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sahelCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sharmCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        seaCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        shoppingCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cultureCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        historyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        campingCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        hikingCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
