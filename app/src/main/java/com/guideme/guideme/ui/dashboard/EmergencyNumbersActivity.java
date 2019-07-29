package com.guideme.guideme.ui.dashboard;

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
import com.guideme.guideme.R;
import com.guideme.guideme.data.models.EmergencyNumber;
import com.guideme.guideme.ui.common.PermissionsHelper;

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

        List<EmergencyNumber> emergencyNumbers = new ArrayList<>();
        emergencyNumbers.add(new EmergencyNumber("123", "Main Ambulance"));
        emergencyNumbers.add(new EmergencyNumber("126", "Tourist Police"));
        emergencyNumbers.add(new EmergencyNumber("128", "Traffic Police"));
        emergencyNumbers.add(new EmergencyNumber("122", "Emergency Police"));
        emergencyNumbers.add(new EmergencyNumber("180", "Fire Department"));
        emergencyNumbers.add(new EmergencyNumber("121", "Electricity Emergency"));
        emergencyNumbers.add(new EmergencyNumber("129", "Natural Gas Emergency"));
        emergencyNumbers.add(new EmergencyNumber("150", "Clock"));
        emergencyNumbers.add(new EmergencyNumber("144", "International Calls from landlines"));
        emergencyNumbers.add(new EmergencyNumber("177", "Landline telephone bills inquiries"));
        emergencyNumbers.add(new EmergencyNumber("16", "Landline telephone complaints"));
        emergencyNumbers.add(new EmergencyNumber("+2 2265-5000", "+2 2265-3333", "Cairo Airport (Terminal 1)"));
        emergencyNumbers.add(new EmergencyNumber("+2 2265-2029", "+2 2265-2222", "Cairo Airport (Terminal 2)"));
        emergencyNumbers.add(new EmergencyNumber("+2 2575-3555", "Railway Information"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new EmergencyNumbersAdapter(this, emergencyNumbers));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void callPhone(String phoneNumber) {
        if (PermissionsHelper.isPermissionGranted(this, CALL_PHONE)) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
        } else {
            pendingPhone = phoneNumber;
            PermissionsHelper.requestPermission(this, CALL_PHONE, PermissionsHelper.CALL_PHONE_REQUEST);
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
