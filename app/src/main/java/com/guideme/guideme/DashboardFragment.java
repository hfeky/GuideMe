package com.guideme.guideme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dashboard, container, false);

        WeatherCard weatherCard = view.findViewById(R.id.weatherCard);
        CardView requestTourGuide = view.findViewById(R.id.requestTourGuide);
        CardView favoritePlaces = view.findViewById(R.id.favoritePlaces);
        CardView commonPhrases = view.findViewById(R.id.commonPhrases);
        CardView bookARide = view.findViewById(R.id.bookARide);
        CardView emergencyNumbers = view.findViewById(R.id.emergencyNumbers);

        NestedScrollView scrollView = view.findViewById(R.id.scrollView);
        final FloatingActionButton fab = ((MainActivity) getContext()).getAddFab();
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int dyConsumed = scrollY - oldScrollY;
                if (dyConsumed > 10 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dyConsumed < -10 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        requestTourGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        favoritePlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        commonPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bookARide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        emergencyNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
