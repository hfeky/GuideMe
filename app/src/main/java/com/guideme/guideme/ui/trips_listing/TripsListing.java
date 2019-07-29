package com.guideme.guideme.ui.trips_listing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.guideme.guideme.R;
import com.guideme.guideme.data.OrderStatusAdapter;
import com.guideme.guideme.data.models.OrderStatusModel;

import java.util.ArrayList;

public class TripsListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_listing);
        orderStatusList();

    }

    private void orderStatusList() {
        ArrayList<OrderStatusModel> arrayOfStatus =OrderStatusModel.getStoreDetail();
        OrderStatusAdapter adapter = new OrderStatusAdapter(this, arrayOfStatus);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
