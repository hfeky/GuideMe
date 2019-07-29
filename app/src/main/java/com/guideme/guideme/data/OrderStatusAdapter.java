package com.guideme.guideme.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.OrderStatusModel;

import java.util.ArrayList;

public class OrderStatusAdapter extends ArrayAdapter<OrderStatusModel> {

    Context context;
    ArrayList<OrderStatusModel> order_status;
    boolean isOn = false;

    public OrderStatusAdapter(Context context, ArrayList<OrderStatusModel> order_status) {
        super(context, 0, order_status);
        this.context = context;
        this.order_status = order_status;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view if(convertView==null)convertView=LayoutInflater.from(getContext()).inflate(R.layout.raw,parent,false);


        // Get the data item for this position
        OrderStatusModel order_status_data = getItem(position);

        // Lookup view for data population
        ImageView iv_upper_line =
                convertView.findViewById(R.id.iv_upper_line);
        ImageView iv_lower_line =
                convertView.findViewById(R.id.iv_lower_line);
        final ImageView iv_circle =  convertView.findViewById(R.id.iv_circle);
        TextView tv_status = convertView.findViewById(R.id.tv_status);
        TextView tv_orderstatus_time =
                convertView.findViewById(R.id.tv_orderstatus_time);
        LinearLayout ly_orderstatus_time =
                convertView.findViewById(R.id.ly_orderstatus_time);
        LinearLayout ly_status = convertView.findViewById(R.id.ly_status);

        // Populate the data into the template view using the data object

        tv_status.setText(order_status_data.getTv_status());
        tv_orderstatus_time.setText(order_status_data.getTv_orderstatus_time());

        if (position == 0) {
            iv_upper_line.setVisibility(View.INVISIBLE);
        } else if (position == order_status.size() - 1) {

            iv_lower_line.setVisibility(View.INVISIBLE);
            ly_orderstatus_time.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                iv_circle.setBackgroundResource(R.drawable.bulleye);

                Toast.makeText(context, "You Clicked at" +
                        position,Toast.LENGTH_SHORT).show();
            }

        });
        // Return the completed view to render on screen
        return convertView;
    }
}