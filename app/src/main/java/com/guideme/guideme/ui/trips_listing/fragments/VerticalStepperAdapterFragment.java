package com.guideme.guideme.ui.trips_listing.fragments;

import android.content.Context;
import android.content.Intent;
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

import com.guideme.guideme.R;
import com.guideme.guideme.data.models.TripPlace;
import com.guideme.guideme.ui.tour_guide.ChooseFilters;

import java.util.ArrayList;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;
import moe.feng.common.stepperview.VerticalStepperView;

public class VerticalStepperAdapterFragment extends Fragment implements IStepperAdapter {

    private VerticalStepperView mVerticalStepperView;

    private ArrayList<String> arr = new ArrayList<>();

    private ArrayList<TripPlace> places = new ArrayList<>();

    public VerticalStepperAdapterFragment(ArrayList<TripPlace> places) {
        this.places = places;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        arr.add("Places Overview");
        arr.add("Add restaurants?");
        arr.add("Stay at an hotel?");
        arr.add("Book a Tour Guide?");
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
        return index == 0 ? arr.get(0) : (index == 1 ? arr.get(1) : arr.get(2));
    }

    @Override
    public @Nullable
    CharSequence getSummary(int index) {
        switch (index) {
            case 0:
                return Html.fromHtml("Places reviewed"
                        + (mVerticalStepperView.getCurrentStep() > index ? " <b></b>" : "")
                );

            case 1:
                return Html.fromHtml("Restaurants updated"
                        + (mVerticalStepperView.getCurrentStep() > index ? " <b></b>" : ""));

            case 2:
                return Html.fromHtml("Hotels updated"
                        + (mVerticalStepperView.getCurrentStep() > index ? " <b></b>" : ""));

            case 3:
                return Html.fromHtml("Book a tour guide!"
                        + (mVerticalStepperView.getCurrentStep() > index ? " <b></b>" : ""));

            default:
                return null;
        }
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public View onCreateCustomView(final int index, Context context, VerticalStepperItemView parent) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.vertical_stepper_sample_item, parent, false);
        TextView contentView = inflateView.findViewById(R.id.item_content);
        String placesDetail = "";
        for (TripPlace place : places) {
            placesDetail += place.getName();
        }
        contentView.setText(
                index == 0 ? placesDetail : (index == 1 ? "Would you like to visit any other restaurant?" : (index == 2 ? "Would you like to add a Hotel ?" : "Book a Tour Guide Tour guide can mak your trip easier!"))
        );
        Button nextButton = inflateView.findViewById(R.id.button_next);
        nextButton.setText(index == 0 ? "Next" : "Yes");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mVerticalStepperView.nextStep();
                if (index == 3) {
                    startActivity(new Intent(getContext(), ChooseFilters.class));
                }
            }
        });
        Button prevButton = inflateView.findViewById(R.id.button_prev);
        prevButton.setText(index == 0 ? "Cancel" : "Back");
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
