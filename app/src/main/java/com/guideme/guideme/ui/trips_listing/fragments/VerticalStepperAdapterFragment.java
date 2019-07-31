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
import com.guideme.guideme.ui.tour_guide.TourGuidesFilterActivity;
import com.guideme.guideme.ui.trip_creation.HotelsActivity;
import com.guideme.guideme.ui.trip_creation.RestaurantsActivity;
import com.guideme.guideme.ui.trip_creation.TripOverviewActivity;

import java.util.ArrayList;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;
import moe.feng.common.stepperview.VerticalStepperView;

public class VerticalStepperAdapterFragment extends Fragment implements IStepperAdapter {

    private VerticalStepperView mVerticalStepperView;

    private ArrayList<String> arr = new ArrayList<>();

    private ArrayList<TripPlace> places;
    private String cityId;

    public VerticalStepperAdapterFragment(ArrayList<TripPlace> places, String cityId) {
        this.places = places;
        this.cityId = cityId;
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
        return arr.get(index);
    }

    @Override
    public @Nullable
    CharSequence getSummary(int index) {
        switch (index) {
            case 0:
                return Html.fromHtml("Done"
                        + (mVerticalStepperView.getCurrentStep() > index ? " <b></b>" : ""));
            case 1:
                return Html.fromHtml("Done"
                        + (mVerticalStepperView.getCurrentStep() > index ? " <b></b>" : ""));
            case 2:
                return Html.fromHtml("Done"
                        + (mVerticalStepperView.getCurrentStep() > index ? " <b></b>" : ""));
            case 3:
                return Html.fromHtml("Done"
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
            placesDetail += place.getName() + "\n";
        }
        contentView.setText(
                index == 0 ? placesDetail : (index == 1 ? "Would you like to visit any restaurant?" : (index == 2 ? "Would you like to stay at an hotel?" : "Book a Tour Guide to make your trip easier."))
        );
        Button nextButton = inflateView.findViewById(R.id.button_next);
        nextButton.setText(index == 0 ? "Next" : "Yes");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerticalStepperView.nextStep();
                if (index == 1) {
                    Intent intent = new Intent(getContext(), RestaurantsActivity.class);
                    intent.putExtra("city_id", cityId);
                    startActivity(intent);
                } else if (index == 2) {
                    Intent intent = new Intent(getContext(), HotelsActivity.class);
                    intent.putExtra("city_id", cityId);
                    startActivity(intent);
                } else if (index == 3) {
                    startActivity(new Intent(getContext(), TourGuidesFilterActivity.class));
                    ((TripOverviewActivity) context).enableDoneButton();
                }
            }
        });
        Button prevButton = inflateView.findViewById(R.id.button_prev);
        prevButton.setText(index == 0 ? "Back" : "No");
        inflateView.findViewById(R.id.button_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVerticalStepperView.nextStep();
                if (index == 0) {
                    getActivity().finish();
                } else if (index == 3) {
                    ((TripOverviewActivity) context).enableDoneButton();
                }
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
