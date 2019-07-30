package com.guideme.guideme.ui.tour_guide;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brouding.simpledialog.SimpleDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TourGuide;

import java.util.List;

public class TourGuideProfileActivity extends AppCompatActivity {

    private TextView guideName;
    private TextView guidePhone;
    private TextView guideRating;
    private ImageView guideAvatar;
    private TextView guidePerksCount;
    private Button bookButton;

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
        guidePhone = findViewById(R.id.guidePhone);
        guideRating = findViewById(R.id.guideRating);
        guideAvatar = findViewById(R.id.avatar);
        guidePerksCount = findViewById(R.id.guidePerksCount);
        bookButton = findViewById(R.id.bookButton);
        guideName.setText(guide.getName());
        guidePhone.setText(guide.getPhoneNumber());
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
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleDialog.Builder(TourGuideProfileActivity.this)
                        .setTitle("Select a Trip!")
                        // If the customView is long enough, SimpleDialog will put your layout in the ScrollView automatically
                        .setCustomView(R.layout.layout_booking_dialog)
                        .setBtnConfirmText("Confirm and request")
                        .setBtnConfirmTextSizeDp(16)
                        .setBtnConfirmTextColor("#1fd1ab")
                        .setBtnCancelText("Cancel", false)
                        .setBtnCancelTextColor("#555555")
                        .show();
            }
        });
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
