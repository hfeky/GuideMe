package com.guideme.guideme.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.CommonPhrase;
import com.guideme.guideme.data.models.Trip;
import com.guideme.guideme.ui.common.DateUtils;
import com.guideme.guideme.ui.home.MainActivity;

import org.apache.log4j.chainsaw.Main;

import java.util.List;

public class TripHistoryAdapter extends RecyclerView.Adapter<TripHistoryAdapter.ViewHolder> {

    private Context context;
    private List<Trip> trips;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView tripPhoto;
        public TextView tripName, tripDate, tripDuration;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tripPhoto = view.findViewById(R.id.tripPhoto);
            tripName = view.findViewById(R.id.tripName);
            tripDate = view.findViewById(R.id.tripDate);
            tripDuration = view.findViewById(R.id.tripDuration);
        }
    }

    public TripHistoryAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.trips = trips;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Trip trip = trips.get(position);
        holder.tripName.setText(trip.getName());
        holder.tripDate.setText("12/12/2012");
        holder.tripDuration.setText("5 days");
        holder.tripPhoto.setImageResource(R.drawable.ic_places_fire_station);

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }
}