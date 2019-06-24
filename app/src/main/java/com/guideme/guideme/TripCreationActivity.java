package com.guideme.guideme;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

    public static final String CIRCULAR_REVEAL_X = "CIRCULAR_REVEAL_X";
    public static final String CIRCULAR_REVEAL_Y = "CIRCULAR_REVEAL_Y";

    private View rootLayout;
    private ImageView placeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_trip);

        rootLayout = findViewById(R.id.rootLayout);
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

        Intent intent = getIntent();

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(CIRCULAR_REVEAL_X) && intent.hasExtra(CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            final int revealX = intent.getIntExtra(CIRCULAR_REVEAL_X, 0);
            final int revealY = intent.getIntExtra(CIRCULAR_REVEAL_Y, 0);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }

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

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (!isShowing && Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
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

    private void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    rootLayout.setVisibility(View.VISIBLE);
                }
            });
            circularReveal.start();
        } else {
            Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setPlaceImage(Drawable drawable) {
        placeImage.setImageDrawable(drawable);
    }
}
