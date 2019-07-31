package com.guideme.guideme.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.Trip;
import com.guideme.guideme.data.models.TripPlace;
import com.guideme.guideme.ui.common.AutoHideFAB;
import com.guideme.guideme.ui.home.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayout noHistory = view.findViewById(R.id.noHistory);

        AutoHideFAB fab = ((MainActivity) getContext()).getAddFab();
        fab.setupWithRecyclerView(recyclerView);

        List<Trip> trips = new ArrayList<>();
        ArrayList<TripPlace> places = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        //trips.add(new Trip("123","123","Cairo","Giza","This is a description","data:image/jpeg;base64,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",tags,places));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TripHistoryAdapter(getContext(), trips));

        return view;
    }
}
