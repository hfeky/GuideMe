package com.guideme.guideme.ui.dashboard;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TourGuide;

import java.util.List;

public class TourGuideProfileActivity extends AppCompatActivity {

    private TextView guideName;
    private TextView guideRating;
    private ImageView guideAvatar;
    private TextView guidePerksCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tour_guide_profile);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TourGuide guide = getIntent().getExtras().getParcelable("guide");
        getSupportActionBar().setTitle(guide.getName() + "'s Profile");

        guideName = findViewById(R.id.guideName);
        guideRating = findViewById(R.id.guideRating);
        guideAvatar = findViewById(R.id.avatar);
        guidePerksCount = findViewById(R.id.guidePerksCount);
        guideName.setText(guide.getName());
        guideRating.setText(guide.getName() + " has a rating of " + guide.getRating() + " stars");

        Glide.with(this)
                .load(guide.getPhoto())
                .placeholder(R.drawable.placeholder_person)
                .apply(RequestOptions.circleCropTransform())
                .into(guideAvatar);

        List<String> perks = guide.getPerks();
        guidePerksCount.setText(String.valueOf(perks.size()));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new PerksAdapter(this, perks));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.contact) {
            //TODO: contact the guide
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_menu, menu);
        return true;
    }
}
