package com.guideme.guideme.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.EmergencyNumber;
import com.guideme.guideme.data.models.TripPlace;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private FavoritePlacesActivity context;
    private List<TripPlace> favoritePlaces;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView placeTitle, placeLocation, placeDescription;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            placeTitle = view.findViewById(R.id.placeTitle);
            placeLocation = view.findViewById(R.id.placeLocation);
            placeDescription = view.findViewById(R.id.placeDescription);
        }
    }

    public FavouriteAdapter(FavoritePlacesActivity context, List<TripPlace> favoritePlaces) {
        this.context = context;
        this.favoritePlaces = favoritePlaces;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TripPlace place = favoritePlaces.get(position);
        holder.placeTitle.setText(favoritePlaces.get(position).getName());
        holder.placeLocation.setText("Egypt");
        holder.placeDescription.setText(favoritePlaces.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return favoritePlaces.size();
    }
}
