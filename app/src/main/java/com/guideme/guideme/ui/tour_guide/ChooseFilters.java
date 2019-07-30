package com.guideme.guideme.ui.tour_guide;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guideme.guideme.R;

import java.util.ArrayList;

public class ChooseFilters extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static String[] cities = {"Alexandria", "Fayyoum", "Sharm El-Sheikh", "Hurghada", "Siwa", "Taba", "Luxor", "Sharqia",
            "Cairo", "Aswan", "Giza", "Marsa Matruh", "North Coast"};

    private static String[] labels = {"snorkelling", "nature", "landmark", "swimming", "sandboarding", "sandvolley", "stargazing",
            "recreation", "tennis", "squash", "bowling", "diving", "yacht", "ferry", "safari", "kitesurfing", "windsurfing", "golf",
            "bicycling"};

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
        setContentView(R.layout.layout_choose_filters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putStringArrayList("tags", selectedT);
                b.putStringArrayList("cities", selectedC);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_on, R.anim.slide_off);
                startActivity(new Intent(ChooseFilters.this, AvailableTourGuidesActivity.class).putExtras(b), options.toBundle());
            }
        });
        mCitiesResult = findViewById(R.id.cities_result);
        mTagsResult = findViewById(R.id.tags_result);

        mCitiesSpinner = findViewById(R.id.spinner_cities);
        mTagsSpinner = findViewById(R.id.spinner_tags);

        ArrayAdapter adapterCities = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCitiesSpinner.setAdapter(adapterCities);
        mCitiesSpinner.setOnItemSelectedListener(this);

        ArrayAdapter adapterTags = ArrayAdapter.createFromResource(this, R.array.tags, android.R.layout.simple_spinner_item);
        adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTagsSpinner.setAdapter(adapterTags);
        mTagsSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        for (String s : cities) {
            if (s.equals(text) && !selectedC.contains(text)) {
                selectedC.add(text);
            }

        }
            for (String s : labels) {
                if (s.equals(text) && !selectedT.contains(text)) {
                    selectedT.add(text);
                }
            }


        String dispC = "Selected Cities: \n\n";
        String dispT = "Selected Activities: \n\n";

        for (int i = 0; i < selectedC.size(); i++) {
            dispC += selectedC.get(i) + " \n\n";
        }

        for (int i = 0; i < selectedT.size(); i++) {
            dispT += selectedT.get(i) + " \n\n";
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
