package com.guideme.guideme;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommonPhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_common_phrases);

        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        List<PhraseItem> phraseItems = new ArrayList<>();
        phraseItems.add(new PhraseItem("Hello", "صباح الفل", "Sabah al ful", R.drawable.phrase_hello));
        phraseItems.add(new PhraseItem("Bye", "مع السلامة", "Ma'a el salama", R.drawable.phrase_bye));
        phraseItems.add(new PhraseItem("Thank you", "شكرا", "Shukran", R.drawable.phrase_thanks));
        phraseItems.add(new PhraseItem("Okay", "تمام", "Tamaam", R.drawable.phrase_okay));
        phraseItems.add(new PhraseItem("No", "لا", "Laa", R.drawable.phrase_no));
        phraseItems.add(new PhraseItem("Right here please", "على جمب لو سمحت", "Ala gamb lw samaht", R.drawable.phrase_right_here));
        PhraseAdapter adapter = new PhraseAdapter(this, phraseItems);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
