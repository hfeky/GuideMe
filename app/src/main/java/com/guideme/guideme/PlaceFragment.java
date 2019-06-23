package com.guideme.guideme;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PlaceFragment extends Fragment {

    private Drawable placeImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_place, container, false);

        TextView placeTitle = view.findViewById(R.id.placeTitle);
        TextView placeLocation = view.findViewById(R.id.placeLocation);
        TextView placeDate = view.findViewById(R.id.placeDate);
        TextView placeDescription = view.findViewById(R.id.placeDescription);
        EditText placeNote = view.findViewById(R.id.placeNote);
        ImageButton shareButton = view.findViewById(R.id.shareButton);
        ImageButton favoriteButton = view.findViewById(R.id.favoriteButton);

        placeImage = getResources().getDrawable(R.drawable.image_placeholder);

        return view;
    }

    public Drawable getPlaceImage() {
        return placeImage;
    }
}
