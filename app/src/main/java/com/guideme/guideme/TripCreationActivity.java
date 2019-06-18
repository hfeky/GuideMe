package com.guideme.guideme;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.flexbox.FlexboxLayout;

public class TripCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_trip);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ViewPager viewPager = findViewById(R.id.viewPager);
        final FlexboxLayout pageIndicators = findViewById(R.id.pageIndicators);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ActivityAddFragment());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // TODO: Implement trip presets
        }

        viewPager.setAdapter(adapter);

        for (int i = 0; i < adapter.getCount(); i++) {
            ImageView indicator = new ImageView(this);
            indicator.setImageResource(R.drawable.page_indicator);
            pageIndicators.addView(indicator);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int selectedPage;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicators.getChildAt(selectedPage).setSelected(false);
                pageIndicators.getChildAt(position).setSelected(true);
                selectedPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pageIndicators.getChildAt(0).setSelected(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
