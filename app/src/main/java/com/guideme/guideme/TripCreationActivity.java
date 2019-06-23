package com.guideme.guideme;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_trip);

        LinearLayout activityLayout = findViewById(R.id.activityLayout);
        final Toolbar toolbar = findViewById(R.id.toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(activityLayout, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                int topInsetHeight = insets.getSystemWindowInsetTop();
                CollapsingToolbarLayout.LayoutParams layoutParams =
                        (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams.topMargin = topInsetHeight;
                toolbar.setLayoutParams(layoutParams);
                return insets.consumeSystemWindowInsets();
            }
        });

        final AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        final FlexboxLayout pageIndicators = findViewById(R.id.pageIndicators);
        FloatingActionButton editFab = findViewById(R.id.editFab);

        final ImageView placeImage = findViewById(R.id.placeImage);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PlaceFragment());
        adapter.addFragment(new PlaceAddFragment());

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
                if (adapter.getItem(position) instanceof PlaceAddFragment) {
                    appBarLayout.setExpanded(false);
                    placeImage.setImageDrawable(null);
                } else {
                    appBarLayout.setExpanded(true);
                    PlaceFragment fragment = (PlaceFragment) adapter.getItem(position);
                    placeImage.setImageDrawable(fragment.getPlaceImage());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pageIndicators.getChildAt(0).setSelected(true);

        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
