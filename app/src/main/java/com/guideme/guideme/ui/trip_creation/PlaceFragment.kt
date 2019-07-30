package com.guideme.guideme.ui.trip_creation

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.guideme.guideme.R
import com.guideme.guideme.data.models.TripPlace
import com.guideme.guideme.ui.common.DateUtils
import kotlinx.android.synthetic.main.layout_place.*
import java.util.*

class PlaceFragment : Fragment() {

    private var context: TripCreationActivity? = null
    var placeImage: Drawable? = null
        private set

    var place: TripPlace? = null
        private set

    private var location: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_place, container, false)
        context = getContext() as TripCreationActivity?
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        if (arguments != null) {
            place = bundle!!.getParcelable("place")
            location = bundle.getString("location")

            placeTitle.text = place!!.name
            placeLocation.text = location

            place!!.date = DateUtils.forwardDaysFromTomorrow(place!!.order)
            placeDate.text = DateUtils.formatDate(place!!.date)

            placeDescription.text = place!!.description

            placeImage = resources.getDrawable(R.drawable.image_placeholder)
            val bitmap = (placeImage as BitmapDrawable).bitmap
            Palette.from(bitmap).generate { palette ->
                val lightVibrant = palette!!.getLightVibrantColor(Color.WHITE)
                fadingEdgeLight.setBackgroundColor(
                    Color.argb(
                        51,
                        Color.red(lightVibrant),
                        Color.green(lightVibrant),
                        Color.blue(lightVibrant)
                    )
                )
            }
        }

        shareButton.setOnClickListener { }

        favoriteButton.setOnClickListener { }
    }

    override fun onStart() {
        super.onStart()
        if (isVisible) {
            if (placeImage != null) {
                context!!.setPlaceImage(placeImage!!)
            } else {
                context!!.setPlaceImage(resources.getDrawable(R.drawable.image_placeholder))
            }
        }
    }

    fun setPlaceDate(date: Date) {
        placeDate.text = DateUtils.formatDate(date)
    }
}
