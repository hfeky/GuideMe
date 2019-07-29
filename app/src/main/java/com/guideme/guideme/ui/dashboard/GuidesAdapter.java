package com.guideme.guideme.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TourGuide;

import java.util.List;

public class GuidesAdapter extends RecyclerView.Adapter<GuidesAdapter.ViewHolder> {

    private GuidesYouMightLikeActivity context;
    private List<TourGuide> guides;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView guideAvatar;
        public TextView guideName, guideRating;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            guideAvatar = view.findViewById(R.id.guideAvatar);
            guideName = view.findViewById(R.id.guideName);
            guideRating = view.findViewById(R.id.guideRating);
        }
    }

    public GuidesAdapter(GuidesYouMightLikeActivity context, List<TourGuide> guides) {
        this.context = context;
        this.guides = guides;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tourguide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TourGuide guide = guides.get(position);
        holder.guideAvatar.setImageResource(guide.getAvatar());
        holder.guideName.setText(guide.getName());
        holder.guideRating.setText(guide.getRating()+"");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GuideProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("guide", guide);
                bundle.putParcelable("avatar", guide);
                intent.putExtra("guide",bundle);
                intent.putExtra("avatar",bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return guides.size();
    }
}
