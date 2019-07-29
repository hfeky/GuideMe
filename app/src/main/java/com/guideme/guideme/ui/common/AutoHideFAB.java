package com.guideme.guideme.ui.common;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AutoHideFAB extends FloatingActionButton {

    private Handler handler = new Handler();
    private int EXTRA_PADDING_BOTTOM;

    public AutoHideFAB(Context context) {
        this(context, null);
    }

    public AutoHideFAB(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoHideFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EXTRA_PADDING_BOTTOM = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                72.0f, getResources().getDisplayMetrics());
    }

    public void setupWithNestedScrollView(final NestedScrollView nestedScrollView) {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int dyConsumed = scrollY - oldScrollY;
                if (dyConsumed > 10) {
                    Rect scrollBounds = new Rect();
                    nestedScrollView.getHitRect(scrollBounds);
                    if (nestedScrollView.getLocalVisibleRect(scrollBounds) && getVisibility() == VISIBLE) {
                        hide();
                    }
                    updateAutoShowHandler();
                } else if (dyConsumed < -10 && getVisibility() != VISIBLE) {
                    show();
                }
            }
        });
        View childView = nestedScrollView.getChildAt(0);
        childView.setPadding(childView.getPaddingLeft(), childView.getPaddingTop(),
                childView.getPaddingRight(), childView.getPaddingBottom() + EXTRA_PADDING_BOTTOM);
    }

    public void setupWithRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 10) {
                    Rect scrollBounds = new Rect();
                    recyclerView.getHitRect(scrollBounds);
                    if (recyclerView.getLocalVisibleRect(scrollBounds) && getVisibility() == VISIBLE) {
                        hide();
                    }
                    updateAutoShowHandler();
                } else if (dy < -10 && getVisibility() != VISIBLE) {
                    show();
                }
            }
        });
        recyclerView.setPadding(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop(),
                recyclerView.getPaddingRight(), recyclerView.getPaddingBottom() + EXTRA_PADDING_BOTTOM);
    }

    private void updateAutoShowHandler() {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }, 1000);
    }
}
