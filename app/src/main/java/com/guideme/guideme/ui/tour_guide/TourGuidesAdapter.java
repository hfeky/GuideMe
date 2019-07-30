package com.guideme.guideme.ui.tour_guide;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TourGuide;

import java.util.List;

public class TourGuidesAdapter extends RecyclerView.Adapter<TourGuidesAdapter.ViewHolder> {

    private AvailableTourGuidesActivity context;
    private List<TourGuide> guides;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView guideAvatar;
        public TextView guideName, guideRating, guideOrigin;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            guideAvatar = view.findViewById(R.id.guideAvatar);
            guideName = view.findViewById(R.id.guideName);
            guideRating = view.findViewById(R.id.guideRating);
            guideOrigin = view.findViewById(R.id.guideOrigin);
        }
    }

    public TourGuidesAdapter(AvailableTourGuidesActivity context, List<TourGuide> guides) {
        this.context = context;
        this.guides = guides;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TourGuide guide = guides.get(position);

        holder.guideName.setText(guide.getName());
        holder.guideRating.setText(String.valueOf(guide.getRating()));
        holder.guideOrigin.setText("City: " + guide.getOrigin());

        Glide.with(context)
                .load(guide.getPhoto())
                .placeholder(R.drawable.placeholder_person)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.guideAvatar);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TourGuideProfileActivity.class);
                intent.putExtra("guide", guide);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return guides.size();
    }
}
