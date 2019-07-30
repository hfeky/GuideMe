package com.guideme.guideme.ui.tour_guide;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guideme.guideme.R;

import java.util.ArrayList;

public class TourGuidesFilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static String[] cities;
    private static String[] tags;

    private ArrayList<String> selectedC = new ArrayList<>();
    private ArrayList<String> selectedT = new ArrayList<>();

    private Spinner mCitiesSpinner;
    private Spinner mTagsSpinner;
    boolean flagC = true;
    boolean flagT = true;
    private TextView mCitiesResult;
    private TextView mTagsResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tour_guides_filter);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cities = getResources().getStringArray(R.array.cities);
        tags = getResources().getStringArray(R.array.tags);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putStringArrayList("tags", selectedT);
                b.putStringArrayList("cities", selectedC);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_on, R.anim.slide_off);
                startActivity(new Intent(TourGuidesFilterActivity.this, AvailableTourGuidesActivity.class).putExtras(b), options.toBundle());
            }
        });

        mCitiesResult = findViewById(R.id.cities_result);
        mTagsResult = findViewById(R.id.tags_result);

        mCitiesSpinner = findViewById(R.id.spinner_cities);
        mTagsSpinner = findViewById(R.id.spinner_tags);

        mCitiesSpinner.setOnItemSelectedListener(this);
        mTagsSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        for (String s : cities) {
            if (!s.equals("All cities")) {
                if (s.equals(text)) {
                    if (selectedC.contains(text)) {
                        selectedC.remove(text);
                    } else {
                        selectedC.add(text);
                    }
                }
            }
        }

        for (String s : tags) {
            if (!s.equals("All tags")) {
                if (s.equals(text)) {
                    if (selectedT.contains(text)) {
                        selectedT.remove(text);
                    } else {
                        selectedT.add(text);
                    }
                }
            }
        }

        String dispC = "";
        String dispT = "";

        for (int i = 0; i < selectedC.size(); i++) {
            dispC += selectedC.get(i);
            if (i != selectedC.size() - 1) {
                dispC += ", ";
            }
        }

        for (int i = 0; i < selectedT.size(); i++) {
            dispT += selectedT.get(i);
            if (i != selectedT.size() - 1) {
                dispT += ", ";
            }
        }

        mCitiesResult.setText(dispC);
        mTagsResult.setText(dispT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please select your preferences!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO: pass arrays to filters
    //TODO: could use better design
}
