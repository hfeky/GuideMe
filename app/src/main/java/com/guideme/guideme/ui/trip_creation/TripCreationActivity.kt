package com.guideme.guideme.ui.trip_creation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.libraries.places.api.model.Place
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.guideme.guideme.R
import com.guideme.guideme.data.models.Trip
import com.guideme.guideme.data.models.TripPlace
import com.guideme.guideme.ui.common.DateUtils
import com.guideme.guideme.ui.common.ViewPagerAdapter
import com.rtchagas.pingplacepicker.PingPlacePicker
import kotlinx.android.synthetic.main.layout_create_trip.*
import java.util.*
import kotlin.math.abs
import kotlin.math.max

class TripCreationActivity : AppCompatActivity() {

    private lateinit var trip: Trip

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_create_trip)

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout!!) { _, insets ->
            val topInsetHeight = insets.systemWindowInsetTop
            val layoutParams = placeImage.layoutParams as CollapsingToolbarLayout.LayoutParams
            layoutParams.topMargin = -topInsetHeight
            placeImage.layoutParams = layoutParams
            insets
        }

        val intent = intent

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
            intent.hasExtra(CIRCULAR_REVEAL_X) && intent.hasExtra(CIRCULAR_REVEAL_Y)
        ) {
            rootLayout!!.visibility = View.INVISIBLE

            val revealX = intent.getIntExtra(CIRCULAR_REVEAL_X, 0)
            val revealY = intent.getIntExtra(CIRCULAR_REVEAL_Y, 0)

            val viewTreeObserver = rootLayout!!.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        revealActivity(revealX, revealY)
                        rootLayout!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                tripName.visibility = View.VISIBLE
            } else {
                tripName.visibility = View.GONE
            }
        })

        adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        if (intent.hasExtra("trip")) {
            trip = intent.extras!!.getParcelable("trip")

            trip.places.sortWith(Comparator { place1, place2 ->
                place1.order - place2.order
            })

            for (place in trip.places) {
                addPlace(place, true)
            }
        } else {
            trip = Trip("", "cairo", "", "", "", null, arrayListOf(), arrayListOf())

            appBarLayout.setExpanded(false)
            placeImage!!.setImageDrawable(null)
        }

        addPlace(null, false)
        if (adapter.count > 1) {
            setDateButton.isEnabled = true
            overviewButton.isEnabled = true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            private var selectedPage: Int = 0

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                setDateButton.isEnabled = position != adapter.count - 1
                pageIndicators.getChildAt(selectedPage).isSelected = false
                pageIndicators.getChildAt(position).isSelected = true
                selectedPage = position
                if ((adapter.getItem(position) as PlaceFragment).tripPlace == null) {
                    appBarLayout.setExpanded(false)
                    placeImage!!.setImageBitmap(null)
                } else {
                    appBarLayout.setExpanded(true)
                    val fragment = adapter.getItem(position) as PlaceFragment
                    placeImage!!.setImageBitmap(fragment.image)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        pageIndicators.getChildAt(0).isSelected = true

        editFab.setOnClickListener {

        }

        setDateButton.setOnClickListener {
            val placeFragment =
                (viewPager.adapter as ViewPagerAdapter).getItem(viewPager.currentItem) as PlaceFragment

            val datePickerDialog = DatePickerDialog(
                this@TripCreationActivity,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    placeFragment.setPlaceDate(calendar.time)
                },
                DateUtils.getYear(placeFragment.tripPlace!!.date),
                DateUtils.getMonth(placeFragment.tripPlace!!.date),
                DateUtils.getDayOfMonth(placeFragment.tripPlace!!.date)
            )

            datePickerDialog.show()
        }

        overviewButton.setOnClickListener {
            val intent = Intent(this, TripOverviewActivity::class.java)
            val places = arrayListOf<TripPlace>()

            for (fragment in adapter.fragments) {
                val place = (fragment as PlaceFragment).tripPlace
                place?.let {
                    places.add(it)
                }
            }

            intent.putExtra("places", places)
            intent.putExtra("city_id", trip.cityId)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun revealActivity(x: Int, y: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val finalRadius = (max(rootLayout!!.width, rootLayout!!.height) * 1.1).toFloat()
            val circularReveal =
                ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0f, finalRadius)
            circularReveal.duration = 400
            circularReveal.interpolator = AccelerateInterpolator()
            circularReveal.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    rootLayout!!.visibility = View.VISIBLE
                }
            })
            circularReveal.start()
        } else {
            Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun setPlaceImage(bitmap: Bitmap) {
        placeImage.setImageBitmap(bitmap)
    }

    fun showPlacePicker() {
        val builder = PingPlacePicker.IntentBuilder()
        builder.setAndroidApiKey(getString(R.string.google_maps_key))
            .setMapsApiKey(getString(R.string.google_maps_key))

        try {
            val placeIntent = builder.build(this)
            startActivityForResult(placeIntent, REQUEST_PLACE_PICKER)
        } catch (ex: Exception) {
            Toast.makeText(
                this,
                "Google Play Services is not installed on your device.",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun addPlace(tripPlace: TripPlace?, withBundle: Boolean) {
        if (tripPlace != null) {
            if (withBundle) {
                val fragment = PlaceFragment()
                val bundle = Bundle()
                bundle.putParcelable("tripPlace", tripPlace)
                bundle.putString("location", trip.location)
                fragment.arguments = bundle
                adapter.addFragment(fragment)
                adapter.notifyDataSetChanged()
            } else {
                val fragment = adapter.fragments[adapter.count - 1] as PlaceFragment
                fragment.setPlace(tripPlace)

                val addFragment = PlaceFragment()
                val bundle = Bundle()
                bundle.putString("location", trip.location)
                addFragment.arguments = bundle
                adapter.addFragment(addFragment)
                adapter.notifyDataSetChanged()
            }
        } else {
            val addFragment = PlaceFragment()
            val bundle = Bundle()
            bundle.putString("location", trip.location)
            addFragment.arguments = bundle
            adapter.addFragment(addFragment)
            adapter.notifyDataSetChanged()
        }

        val indicator = ImageView(this)
        indicator.setImageResource(R.drawable.page_indicator)
        pageIndicators.addView(indicator)

        viewPager.offscreenPageLimit = adapter.count
    }

    private fun addPlace(place: Place) {
        val fragment = adapter.fragments[adapter.count - 1] as PlaceFragment
        fragment.setPlace(place, adapter.count)

        val addFragment = PlaceFragment()
        val bundle = Bundle()
        bundle.putString("location", trip.location)
        addFragment.arguments = bundle
        adapter.addFragment(addFragment)
        adapter.notifyDataSetChanged()

        val indicator = ImageView(this)
        indicator.setImageResource(R.drawable.page_indicator)
        pageIndicators.addView(indicator)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PLACE_PICKER && resultCode == RESULT_OK) {
            val selectedPlace = PingPlacePicker.getPlace(data!!)
            if (selectedPlace != null) {
                setDateButton.isEnabled = true
                appBarLayout.setExpanded(true)
                addPlace(selectedPlace)
            }
        }
    }

    companion object {

        const val CIRCULAR_REVEAL_X = "CIRCULAR_REVEAL_X"
        const val CIRCULAR_REVEAL_Y = "CIRCULAR_REVEAL_Y"

        private const val REQUEST_PLACE_PICKER = 1000
    }
}
