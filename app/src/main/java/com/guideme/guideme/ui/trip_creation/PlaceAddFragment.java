package com.guideme.guideme.ui.trip_creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.guideme.guideme.R;

public class PlaceAddFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_place, container, false);

        FrameLayout addPlaceButton = view.findViewById(R.id.addPlaceButton);

        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TripCreationActivity) getActivity()).showPlacePicker();
            }
        });

        return view;
    }
}
