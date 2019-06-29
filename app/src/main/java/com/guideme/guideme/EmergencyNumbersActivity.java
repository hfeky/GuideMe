package com.guideme.guideme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;

public class EmergencyNumbersActivity extends AppCompatActivity {

    private String pendingPhone;

    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_emergency_numbers);

        rootLayout = findViewById(R.id.rootLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        List<PhoneItem> phoneItems = new ArrayList<>();
        phoneItems.add(new PhoneItem("123", "Main Ambulance"));
        phoneItems.add(new PhoneItem("126", "Tourist Police"));
        phoneItems.add(new PhoneItem("128", "Traffic Police"));
        phoneItems.add(new PhoneItem("122", "Emergency Police"));
        phoneItems.add(new PhoneItem("180", "Fire Department"));
        phoneItems.add(new PhoneItem("121", "Electricity Emergency"));
        phoneItems.add(new PhoneItem("129", "Natural Gas Emergency"));
        phoneItems.add(new PhoneItem("150", "Clock"));
        phoneItems.add(new PhoneItem("144", "International Calls from landlines"));
        phoneItems.add(new PhoneItem("177", "Landline telephone bills inquiries"));
        phoneItems.add(new PhoneItem("16", "Landline telephone complaints"));
        phoneItems.add(new PhoneItem("+2 2265-5000", "+2 2265-3333", "Cairo Airport (Terminal 1)"));
        phoneItems.add(new PhoneItem("+2 2265-2029", "+2 2265-2222", "Cairo Airport (Terminal 2)"));
        phoneItems.add(new PhoneItem("+2 2575-3555", "Railway Information"));
        PhonesAdapter adapter = new PhonesAdapter(this, phoneItems);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void callPhone(String phoneNumber) {
        PermissionsHelper permissionsHelper = new PermissionsHelper(this);
        if (permissionsHelper.isPermissionGranted(CALL_PHONE)) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
        } else {
            pendingPhone = phoneNumber;
            permissionsHelper.requestPermission(CALL_PHONE, PermissionsHelper.CALL_PHONE_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PermissionsHelper.CALL_PHONE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone(pendingPhone);
                pendingPhone = null;
            } else {
                Snackbar.make(rootLayout, "Call permission is needed.", Snackbar.LENGTH_LONG)
                        .setAction("Try Again", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callPhone(pendingPhone);
                            }
                        })
                        .show();
            }
        }
    }
}
