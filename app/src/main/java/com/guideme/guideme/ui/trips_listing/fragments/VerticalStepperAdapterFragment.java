package com.guideme.guideme.ui.trips_listing.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.guideme.guideme.R;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;
import moe.feng.common.stepperview.VerticalStepperView;

public class VerticalStepperAdapterFragment extends Fragment implements IStepperAdapter {

    private VerticalStepperView mVerticalStepperView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vertical_stepper_adapter, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mVerticalStepperView = view.findViewById(R.id.vertical_stepper_view);
        mVerticalStepperView.setStepperAdapter(this);
    }

    @Override
    public @NonNull
    CharSequence getTitle(int index) {
        return "Step " + index;
    }

    @Override
    public @Nullable
    CharSequence getSummary(int index) {
        switch (index) {
            case 0:
                return Html.fromHtml("Summarized if needed"
                        + (mVerticalStepperView.getCurrentStep() > index ? "; <b>isDone!</b>" : "")
                );
            case 2:
                return Html.fromHtml("Last step"
                        + (mVerticalStepperView.getCurrentStep() > index ? "; <b>isDone!</b>" : ""));
            default:
                return null;
        }
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public View onCreateCustomView(final int index, Context context, VerticalStepperItemView parent) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.vertical_stepper_sample_item, parent, false);
        TextView contentView = inflateView.findViewById(R.id.item_content);
        contentView.setText(
                index == 0 ? R.string.content_step_0 : (index == 1 ? R.string.content_step_1 : R.string.content_step_2)
        );
        Button nextButton = inflateView.findViewById(R.id.button_next);
        nextButton.setText(index == size() - 1 ? "Done" : getString(android.R.string.ok));
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mVerticalStepperView.nextStep();
            }
        });
        Button prevButton = inflateView.findViewById(R.id.button_prev);
        prevButton.setText(android.R.string.cancel);
        inflateView.findViewById(R.id.button_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerticalStepperView.prevStep();
            }
        });
        return inflateView;
    }

    @Override
    public void onShow(int index) {

    }

    @Override
    public void onHide(int index) {

    }

}