package com.guideme.guideme.ui.trip_creation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.Restaurant;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    private Context context;
    private List<Restaurant> restaurants;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView restaurantIcon;
        public TextView restaurant, restaurantDescription;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            restaurantIcon = view.findViewById(R.id.restaurantIcon);
            restaurant = view.findViewById(R.id.restaurantName);
            restaurantDescription = view.findViewById(R.id.restaurantDescription);
        }
    }

    public RestaurantsAdapter(Context context, List<Restaurant> restaurant) {
        this.context = context;
        this.restaurants = restaurant;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Restaurant restaurant = restaurants.get(position);
        holder.restaurant.setText(restaurant.getName());
        holder.restaurantDescription.setText(restaurant.getDescription());
//        holder.restaurantIcon.setImageResource(restaurant.get());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Play sound.
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
