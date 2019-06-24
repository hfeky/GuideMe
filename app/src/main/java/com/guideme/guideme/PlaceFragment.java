package com.guideme.guideme;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

public class PlaceFragment extends Fragment {

    private TripCreationActivity context;
    private Drawable placeImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_place, container, false);
        context = (TripCreationActivity) getContext();

        final View fadingEdgeLight = view.findViewById(R.id.fadingEdgeLight);
        TextView placeTitle = view.findViewById(R.id.placeTitle);
        TextView placeLocation = view.findViewById(R.id.placeLocation);
        TextView placeDate = view.findViewById(R.id.placeDate);
        TextView placeDescription = view.findViewById(R.id.placeDescription);
        EditText placeNote = view.findViewById(R.id.placeNote);
        ImageButton shareButton = view.findViewById(R.id.shareButton);
        ImageButton favoriteButton = view.findViewById(R.id.favoriteButton);

        Bundle bundle = getArguments();
        if (getArguments() != null) {
            String title = bundle.getString("title", "Title unknown");
            String location = bundle.getString("location", "Location unknown");
            String date = bundle.getString("date", "No date set");
            String description = bundle.getString("description", "No description available");
            String note = bundle.getString("note", "");
            int image = bundle.getInt("image", R.drawable.image_placeholder);
            placeTitle.setText(title);
            placeLocation.setText(location);
            placeDate.setText(date);
            placeDescription.setText(description);
            placeNote.setText(note);
            placeImage = getResources().getDrawable(image);
            Bitmap bitmap = ((BitmapDrawable) placeImage).getBitmap();
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int lightVibrant = palette.getLightVibrantColor(Color.WHITE);
                    fadingEdgeLight.setBackgroundColor(Color.argb(51, Color.red(lightVibrant),
                            Color.green(lightVibrant), Color.blue(lightVibrant)));
                }
            });
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isVisible()) {
            context.setPlaceImage(placeImage);
        }
    }

    public Drawable getPlaceImage() {
        return placeImage;
    }
}
