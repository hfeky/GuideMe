package com.guideme.guideme.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.Trip;
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
        return view;
    }
}
