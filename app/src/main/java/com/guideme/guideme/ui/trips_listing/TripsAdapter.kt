package com.guideme.guideme.ui.trips_listing

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.guideme.guideme.R
import com.guideme.guideme.data.models.Trip
import com.guideme.guideme.ui.trip_creation.TripCreationActivity
import kotlinx.android.synthetic.main.item_trip.view.*

class TripsAdapter(
    private val context: Context,
    private val trips: List<Trip>
) : RecyclerView.Adapter<TripsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip = trips[position]

        holder.itemView.tripName.text = trip.name
        holder.itemView.tripLocation.text = trip.location
        holder.itemView.tripDescription.text = trip.description

        Glide.with(context)
            .load(trip.photo)
            .placeholder(R.drawable.image_placeholder)
            .into(holder.itemView.tripPhoto)

        holder.itemView.tripTags.removeAllViews()
        for (tag in trip.tags) {
            val chip = LayoutInflater.from(context).inflate(R.layout.item_trip_tag, null, false) as Chip
            chip.isEnabled = false
            chip.text = tag
            holder.itemView.tripTags.addView(chip)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TripCreationActivity::class.java)
            intent.putExtra("trip", trip)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }
}
