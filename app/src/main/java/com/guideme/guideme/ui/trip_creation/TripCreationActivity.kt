package com.guideme.guideme.ui.trip_creation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
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
                val fragment = PlaceFragment()
                val bundle = Bundle()
                bundle.putParcelable("place", place)
                bundle.putString("location", trip.location + ", Egypt")
                fragment.arguments = bundle
                addFragment(fragment = fragment)
            }
        } else {
            trip = Trip("", "cairo", "", "", "", null, arrayListOf(), arrayListOf())

            appBarLayout.setExpanded(false)
            placeImage!!.setImageDrawable(null)
        }

        if (adapter.count > 0) {
            setDateButton.isEnabled = true
            overviewButton.isEnabled = true
        }
        addFragment(fragment = PlaceAddFragment())
        viewPager.offscreenPageLimit = adapter.count

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            private var selectedPage: Int = 0

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                pageIndicators.getChildAt(selectedPage).isSelected = false
                pageIndicators.getChildAt(position).isSelected = true
                selectedPage = position
                if (adapter.getItem(position) is PlaceAddFragment) {
                    appBarLayout.setExpanded(false)
                    placeImage!!.setImageDrawable(null)
                } else {
                    appBarLayout.setExpanded(true)
                    val fragment = adapter.getItem(position) as PlaceFragment
                    placeImage!!.setImageDrawable(fragment.placeImage)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        pageIndicators.getChildAt(0).isSelected = true

        editFab.setOnClickListener {
            for (fragment in adapter.fragments) {
                if (fragment is PlaceFragment) {
                    Log.e("LOOOL", DateUtils.formatDate(fragment.place!!.date))
                }
            }
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
                DateUtils.getYear(placeFragment.place!!.date),
                DateUtils.getMonth(placeFragment.place!!.date),
                DateUtils.getDayOfMonth(placeFragment.place!!.date)
            )

            datePickerDialog.show()
        }

        overviewButton.setOnClickListener {
            val intent = Intent(this, TripOverviewActivity::class.java)
            val places = arrayListOf<TripPlace>()

            for (fragment in adapter.fragments) {
                if (fragment is PlaceFragment) {
                    places.add(fragment.place!!)
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

    fun setPlaceImage(drawable: Drawable) {
        placeImage.setImageDrawable(drawable)
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

    private fun addFragment(index: Int? = null, fragment: Fragment) {
        if (index != null) {
            for (i in index until adapter.count) {
                adapter.removeFragment(i)
                adapter.notifyDataSetChanged()
            }
            adapter.addFragment(fragment)
            adapter.addFragment(PlaceAddFragment())
        } else {
            adapter.addFragment(fragment)
        }
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
                val fragment = PlaceFragment()
                val bundle = Bundle()

                val place = TripPlace(selectedPlace.id!!, selectedPlace.name!!, null, adapter.count)
                trip.places.add(place)
                bundle.putParcelable("place", selectedPlace)
                if (trip.location.isEmpty()) {
                    bundle.putString("location", "Egypt")
                } else {
                    bundle.putString("location", trip.location + ", Egypt")
                }
                fragment.arguments = bundle
                addFragment(adapter.count - 1, fragment)
            }
        }
    }

    companion object {

        const val CIRCULAR_REVEAL_X = "CIRCULAR_REVEAL_X"
        const val CIRCULAR_REVEAL_Y = "CIRCULAR_REVEAL_Y"

        private const val REQUEST_PLACE_PICKER = 1000
    }
}
