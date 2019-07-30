package com.guideme.guideme.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TourGuide;

import java.util.ArrayList;
import java.util.List;

public class GuideProfileActivity extends AppCompatActivity {

    private TextView guideName;
    private TextView guideRating;
    private ImageView guideAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_profile);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getBundleExtra("guide");
        TourGuide guide = bundle.getParcelable("guide");
        getSupportActionBar().setTitle(guide.getName()+"'s Profile");


        bundle = getIntent().getBundleExtra("avatar");
//        ImageView avatar  = bundle.getParcelable("avatar");
//        avatar.getDrawable();

        guideName = findViewById(R.id.guideName);
        guideRating = findViewById(R.id.guideRating);
//        guideAvatar = findViewById(R.id.avatar);
        guideName.setText(guide.getName());
        guideRating.setText(guide.getName()+" has a rating of "+guide.getRating()+" stars");

        List<String> perks = new ArrayList<>();
        perks.add(" Multilingual");
        perks.add(" Ge");
        perks.add(" Very Ge");
        perks.add(" Super Ge");
        perks.add(" Mega Ge");
        perks.add(" Ultra Ge");

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new PerksAdapter(this, perks));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        else if(item.getItemId()==R.id.contact){
            //TODO: contact the guide
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_menu, menu);
        return true;
    }


}
