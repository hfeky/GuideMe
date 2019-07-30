package com.guideme.guideme.ui.trips_listing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnSuccessListener
import com.guideme.guideme.R
import com.guideme.guideme.data.DataManager
import com.guideme.guideme.data.models.City
import com.guideme.guideme.data.models.Trip
import com.guideme.guideme.data.models.TripPlace
import com.guideme.guideme.ui.trip_creation.TripCreationActivity
import kotlinx.android.synthetic.main.layout_trips.*
import kotlinx.android.synthetic.main.popup_filter.view.*

class TripsActivity : AppCompatActivity() {

    private val dataManager = DataManager()

    private val cities = ArrayList<City>()

    private var filterPopup: PopupWindow? = null
    private var selectedCity: Int = 0
    private var selectedTag: String = "All tags"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_trips)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            selectedCity = bundle.getInt("city", 0)
            selectedTag = bundle.getString("tag", "all tags")
        }

        createFab.setupWithRecyclerView(recyclerView)
        createFab.setOnClickListener { v ->
            val intent = Intent(this@TripsActivity, TripCreationActivity::class.java)
            val revealX = (v.x + v.width / 2).toInt()
            val revealY = (v.y + v.height / 2).toInt()
            intent.putExtra(TripCreationActivity.CIRCULAR_REVEAL_X, revealX)
            intent.putExtra(TripCreationActivity.CIRCULAR_REVEAL_Y, revealY)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        loadCities()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_trips, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.item_filter -> showFilterPopup()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (filterPopup != null && filterPopup!!.isShowing) {
            filterPopup!!.dismiss()
        } else {
            super.onBackPressed()
        }
    }

    private fun showFilterPopup() {
        val view = layoutInflater.inflate(R.layout.popup_filter, null)
        filterPopup = PopupWindow(
            view, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val tags = arrayListOf<String>()
        tags.addAll(resources.getStringArray(R.array.tags))

        view.citySpinner.setSelection(selectedCity)
        view.tagSpinner.setSelection(tags.indexOf(selectedTag))

        view.closeIcon.setOnClickListener { filterPopup!!.dismiss() }

        view.resetFilterButton.setOnClickListener {
            view.citySpinner.setSelection(0)
            view.tagSpinner.setSelection(0)
        }

        view.applyFiltersButton.setOnClickListener {
            filterPopup!!.dismiss()
            selectedCity = view.citySpinner.selectedItemPosition
            selectedTag = view.tagSpinner.selectedItem.toString()
        }

        filterPopup!!.animationStyle = R.style.PopupAnimation
        filterPopup!!.showAtLocation(view, Gravity.TOP, 0, 0)

        val container = filterPopup!!.contentView.parent as View
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = container.layoutParams as WindowManager.LayoutParams
        layoutParams.flags = layoutParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.3f
        windowManager.updateViewLayout(container, layoutParams)
    }

    private fun loadCities() {
        dataManager.getCities(OnSuccessListener { result ->
            for (document in result) {
                cities.add(City(document.id, document["name"] as String, arrayListOf()))
            }
            loadTrips()
        })
    }

    private fun loadTrips() {
        for (city in cities) {
            dataManager.getTrips(city.cityId, OnSuccessListener { result ->
                for (document in result) {
                    city.trips.add(
                        Trip(
                            document.id,
                            document.get("city_id") as String,
                            document.get("name") as String,
                            city.name,
                            document.get("description") as String,
                            document.get("photo") as String,
                            document.get("tags") as List<String>,
                            arrayListOf()
                        )
                    )
                }
                loadPlaces(city)
            })
        }
    }

    private fun loadPlaces(city: City) {
        for (trip in city.trips) {
            dataManager.getTripPlaces(city.cityId, trip.tripId, OnSuccessListener { result ->
                for (document in result) {
                    trip.places.add(
                        TripPlace(
                            document.id,
                            document.get("name") as String,
                            document.get("description") as String?,
                            (document.get("order") as Long).toInt()
                        )
                    )
                }
                setupRecyclerView()
            })
        }
    }

    private fun setupRecyclerView() {
        val trips = arrayListOf<Trip>()
        for (city in cities) {
            trips.addAll(city.trips)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TripsAdapter(this, trips)
    }
}
