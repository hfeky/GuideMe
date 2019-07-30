package com.guideme.guideme.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.CommonPhrase;

import java.util.List;

public class PerksAdapter extends RecyclerView.Adapter<PerksAdapter.ViewHolder> {

    private GuideProfileActivity context;
    private List<String> perks;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView perk;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            perk = view.findViewById(R.id.perk);
        }
    }

    public PerksAdapter(GuideProfileActivity context, List<String> perks) {
        this.context = context;
        this.perks = perks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perk, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String perk = perks.get(position);
        holder.perk.setText(perk);
    }

    @Override
    public int getItemCount() {
        return perks.size();
    }
}
