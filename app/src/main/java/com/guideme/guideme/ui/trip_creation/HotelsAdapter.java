package com.guideme.guideme.ui.trip_creation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.Hotel;

import java.util.List;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.ViewHolder> {

    private Context context;
    private List<Hotel> hotels;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView hotelIcon;
        public TextView hotelName, hotelDescription;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            hotelIcon = view.findViewById(R.id.hotelIcon);
            hotelName = view.findViewById(R.id.hotelName);
            hotelDescription = view.findViewById(R.id.hotelDescription);
        }
    }

    public HotelsAdapter(Context context, List<Hotel> hotels) {
        this.context = context;
        this.hotels = hotels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Hotel hotel = hotels.get(position);
        holder.hotelName.setText(hotel.getName());
        holder.hotelDescription.setText(hotel.getDescription());
//        holder.hotelIcon.setImageResource(hotelName.get());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Play sound.
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }
}
