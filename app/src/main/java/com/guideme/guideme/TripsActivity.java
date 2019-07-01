package com.guideme.guideme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public class TripsActivity extends AppCompatActivity {

    private PopupWindow filterPopup;
    private int selectedCity, selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trips);

        Toolbar toolbar = findViewById(R.id.toolbar);
        AutoHideFAB createFab = findViewById(R.id.createFab);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedCity = bundle.getInt("city", 0);
            selectedCategory = bundle.getInt("category", 0);
        }

        createFab.setupWithRecyclerView(recyclerView);
        createFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripsActivity.this, TripCreationActivity.class);
                int revealX = (int) (v.getX() + v.getWidth() / 2);
                int revealY = (int) (v.getY() + v.getHeight() / 2);
                intent.putExtra(TripCreationActivity.CIRCULAR_REVEAL_X, revealX);
                intent.putExtra(TripCreationActivity.CIRCULAR_REVEAL_Y, revealY);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trips, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_filter:
                showFilterPopup();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (filterPopup != null && filterPopup.isShowing()) {
            filterPopup.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    private void showFilterPopup() {
        View view = getLayoutInflater().inflate(R.layout.popup_filter, null);
        filterPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView closeIcon = view.findViewById(R.id.closeIcon);
        Button resetFilterButton = view.findViewById(R.id.resetFilterButton);
        Button applyFiltersButton = view.findViewById(R.id.applyFiltersButton);
        final Spinner citySpinner = view.findViewById(R.id.citySpinner);
        final Spinner categorySpinner = view.findViewById(R.id.categorySpinner);

        citySpinner.setSelection(selectedCity);
        categorySpinner.setSelection(selectedCategory);

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopup.dismiss();
            }
        });

        resetFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citySpinner.setSelection(0);
                categorySpinner.setSelection(0);
            }
        });

        applyFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopup.dismiss();
                selectedCity = citySpinner.getSelectedItemPosition();
                selectedCategory = categorySpinner.getSelectedItemPosition();
            }
        });

        filterPopup.showAsDropDown(view);

        View container = (View) filterPopup.getContentView().getParent();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) container.getLayoutParams();
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.3f;
        windowManager.updateViewLayout(container, layoutParams);
    }
}
