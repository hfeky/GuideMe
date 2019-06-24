package com.guideme.guideme;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripCreationActivity extends AppCompatActivity {

    private ImageView placeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_trip);

        LinearLayout rootLayout = findViewById(R.id.rootLayout);
        placeImage = findViewById(R.id.placeImage);

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                int topInsetHeight = insets.getSystemWindowInsetTop();
                CollapsingToolbarLayout.LayoutParams layoutParams =
                        (CollapsingToolbarLayout.LayoutParams) placeImage.getLayoutParams();
                layoutParams.topMargin = -topInsetHeight;
                placeImage.setLayoutParams(layoutParams);
                return insets;
            }
        });

        final AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final EditText tripName = findViewById(R.id.tripName);
        FloatingActionButton editFab = findViewById(R.id.editFab);
        ViewPager viewPager = findViewById(R.id.viewPager);
        final FlexboxLayout pageIndicators = findViewById(R.id.pageIndicators);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private boolean isShowing = tripName.getVisibility() == View.VISIBLE;
            private int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShowing = true;
                    tripName.setVisibility(View.VISIBLE);
                } else if (isShowing) {
                    isShowing = false;
                    tripName.setVisibility(View.GONE);
                }
            }
        });

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PlaceAddFragment());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO: Implement trip presets
        }

        tripName.setVisibility(View.GONE);

        // TODO: Remove that test
        Fragment fragment = new PlaceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "The Great Pyramids");
        bundle.putString("location", "Giza, Egypt");
        bundle.putString("date", "31/07/2019");
        bundle.putString("description", "The great pyramids consist of 3 pyramids: Khofu, Khafraa, Mankaraa. The Sphinx is also located nearby there.");
        bundle.putString("note", "");
        bundle.putInt("image", R.drawable.frame_giza);
        fragment.setArguments(bundle);
        adapter.addFragment(0, fragment);

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

    public void setPlaceImage(Drawable drawable) {
        placeImage.setImageDrawable(drawable);
    }
}
