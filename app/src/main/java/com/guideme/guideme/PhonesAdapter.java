package com.guideme.guideme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhonesAdapter extends RecyclerView.Adapter<PhonesAdapter.ViewHolder> {

    private EmergencyNumbersActivity context;
    private List<PhoneItem> phoneItems;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView phone, title;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            phone = view.findViewById(R.id.phone);
            title = view.findViewById(R.id.title);
        }
    }

    public PhonesAdapter(EmergencyNumbersActivity context, List<PhoneItem> phoneItems) {
        this.context = context;
        this.phoneItems = phoneItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PhoneItem phoneItem = phoneItems.get(position);
        if (phoneItem.getSecondaryPhone() == null) {
            holder.phone.setText(phoneItem.getPrimaryPhone());
        } else {
            holder.phone.setText(phoneItem.getPrimaryPhone() + "\n" + phoneItem.getSecondaryPhone());
        }
        holder.title.setText(phoneItem.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.callPhone(phoneItem.getPrimaryPhone());
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneItems.size();
    }
}
