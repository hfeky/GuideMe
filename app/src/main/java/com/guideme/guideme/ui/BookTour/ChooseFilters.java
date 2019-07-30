package com.guideme.guideme.ui.BookTour;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guideme.guideme.R;
import com.guideme.guideme.ui.dashboard.GuidesYouMightLikeActivity;

import java.lang.reflect.Array;
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
        setContentView(R.layout.activity_choose_filters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putStringArrayList("tags", selectedT);
                b.putStringArrayList("city", selectedC);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_on, R.anim.slide_off);
                startActivity(new Intent(ChooseFilters.this, GuidesYouMightLikeActivity.class).putExtras(b), options.toBundle());
            }
        });
        mCitiesResult = findViewById(R.id.cities_result);
        mTagsResult = findViewById(R.id.tags_result);

        mCitiesSpinner = findViewById(R.id.spinner_cities);
        mTagsSpinner = findViewById(R.id.spinner_tags);

        ArrayAdapter adapterCities = ArrayAdapter.createFromResource(this, R.array.cities_for_filters, android.R.layout.simple_spinner_item);
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCitiesSpinner.setAdapter(adapterCities);
        mCitiesSpinner.setOnItemSelectedListener(this);


        ArrayAdapter adapterTags = ArrayAdapter.createFromResource(this, R.array.categories_for_filters, android.R.layout.simple_spinner_item);
        adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTagsSpinner.setAdapter(adapterTags);
        mTagsSpinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (flagC && text.equals("Snorkelling")) {
            flagC = false;
            return;
        } else if (flagT && text.equals("Alexandria")) {
            flagT = false;
            return;
        } else {
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


    //TODO: pass arrays to filters
    //TODO: could use better design


}
