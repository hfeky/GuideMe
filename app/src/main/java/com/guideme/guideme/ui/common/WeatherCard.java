package com.guideme.guideme.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.guideme.guideme.R;

public class WeatherCard extends CardView {

    private Context context;

    private TextView greeting, username;
    private ImageView weatherIcon;
    private TextView weatherToday;

    private TextView day1, day2, day3, day4, day5, day6;
    private TextView weather1, weather2, weather3, weather4, weather5, weather6;

    public WeatherCard(Context context) {
        this(context, null);
    }

    public WeatherCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.weather_card, this, true);
        setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15.0f, getResources().getDisplayMetrics()));

        greeting = findViewById(R.id.greeting);
        username = findViewById(R.id.username);
        weatherIcon = findViewById(R.id.weatherIcon);
        weatherToday = findViewById(R.id.weatherToday);

        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        day5 = findViewById(R.id.day5);
        day6 = findViewById(R.id.day6);

        weather1 = findViewById(R.id.weather1);
        weather2 = findViewById(R.id.weather2);
        weather3 = findViewById(R.id.weather3);
        weather4 = findViewById(R.id.weather4);
        weather5 = findViewById(R.id.weather5);
        weather6 = findViewById(R.id.weather6);
    }
}
