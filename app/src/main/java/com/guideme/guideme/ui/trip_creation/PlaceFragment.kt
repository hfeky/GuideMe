package com.guideme.guideme.ui.trip_creation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.guideme.guideme.R
import com.guideme.guideme.data.models.TripPlace
import com.guideme.guideme.ui.common.DateUtils
import io.paperdb.Paper
import kotlinx.android.synthetic.main.layout_add_place.*
import kotlinx.android.synthetic.main.layout_create_trip.*
import kotlinx.android.synthetic.main.layout_place.*
import kotlinx.android.synthetic.main.layout_trip_creation.*
import java.util.*
import kotlin.collections.ArrayList


class PlaceFragment : Fragment() {

    private lateinit var placesClient: PlacesClient

    private var context: TripCreationActivity? = null
    var image: Bitmap? = null
        private set

    var tripPlace: TripPlace? = null
        private set

    private var location: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_trip_creation, container, false)
        context = getContext() as TripCreationActivity?
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Places.initialize(context!!, resources.getString(R.string.google_maps_key))
        placesClient = Places.createClient(context!!)

        val bundle = arguments
        tripPlace = bundle!!.getParcelable("tripPlace")

        if (tripPlace != null) {
            addPlaceLayout.isGone = true
            placeLayout.isVisible = true

            tripPlace = bundle.getParcelable("tripPlace")
            location = bundle.getString("location") + ", Egypt"

            setPlace(tripPlace!!)

            checkIsFavorite()
        } else {
            addPlaceLayout.isVisible = true
            placeLayout.isGone = true
        }

        addPlaceButton.setOnClickListener {
            (activity as TripCreationActivity).showPlacePicker()
        }

        shareButton.setOnClickListener { }

        favoriteButton.setOnClickListener {
            var favoritedPlaces = Paper.book().read("favorite_places") as ArrayList<TripPlace>?
            var isFavorited = false

            if (favoritedPlaces != null) {
                for (i in 0 until favoritedPlaces.size) {
                    if (tripPlace!!.placeId == favoritedPlaces[i].placeId) {
                        isFavorited = true
                        favoritedPlaces.remove(favoritedPlaces[i])
                        break
                    }
                }
            }

            if (!isFavorited) {
                if (favoritedPlaces == null) {
                    favoritedPlaces = ArrayList()
                }
                if (tripPlace != null) {
                    favoritedPlaces.add(tripPlace!!)
                }
                favoriteButton.setImageResource(R.drawable.ic_heart_filled)
            } else {
                favoriteButton.setImageResource(R.drawable.ic_heart_bordered)
            }

            Paper.book().write("favorite_places", favoritedPlaces)
        }
    }

    override fun onStart() {
        super.onStart()
        if (isVisible) {
            if (image != null) {
                context!!.setPlaceImage(image!!)
            } else {
                image = BitmapFactory.decodeResource(context!!.getResources(), R.drawable.image_placeholder)
                context!!.setPlaceImage(image!!)
            }
        }
    }

    private fun checkIsFavorite() {
        Paper.init(context)
        var favoritedPlaces = Paper.book().read("favorite_places") as ArrayList<TripPlace>?
        var isFavorited = false
        if (favoritedPlaces != null) {
            for (i in 0 until favoritedPlaces.size) {
                if (tripPlace!!.placeId == favoritedPlaces[i].placeId) {
                    isFavorited = true
                    break
                }
            }
        }
        if (isFavorited) {
            favoriteButton.setImageResource(R.drawable.ic_heart_filled)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_heart_bordered)
        }
    }

    fun setPlace(tripPlace: TripPlace) {
        addPlaceLayout.isGone = true
        placeLayout.isVisible = true

        placeTitle.text = this.tripPlace!!.name
        placeLocation.text = location

        this.tripPlace!!.date = DateUtils.forwardDaysFromTomorrow(this.tripPlace!!.order)
        placeDate.text = DateUtils.formatDate(this.tripPlace!!.date)

        placeDescription.text = this.tripPlace!!.description

        val fetchRequest = FetchPlaceRequest.newInstance(
            tripPlace.placeId,
            listOf(Place.Field.ADDRESS, Place.Field.PHOTO_METADATAS)
        )
        placesClient.fetchPlace(fetchRequest).addOnSuccessListener { fetchPlaceResponse ->
            context!!.placeLocation.text = fetchPlaceResponse.place.address
            val photoMetadata = fetchPlaceResponse.place.photoMetadatas?.get(0)
            setPlaceImage(photoMetadata)
        }.addOnFailureListener { e ->
            Log.e(TAG, "Failed to fetch place: " + e.message)
        }
    }

    fun setPlace(place: Place, order: Int) {
        addPlaceLayout.isGone = true
        placeLayout.isVisible = true

        placeTitle.text = place.name
        placeLocation.text = place.address

        tripPlace = TripPlace(place.id!!, place.name!!, order = order)
        tripPlace!!.date = DateUtils.forwardDaysFromTomorrow(order)
        placeDate.text = DateUtils.formatDate(tripPlace!!.date)

        placeDescription.text = tripPlace!!.description

        val photoMetadata = place.photoMetadatas?.get(0)
        setPlaceImage(photoMetadata)
    }

    fun setPlaceDate(date: Date) {
        placeDate.text = DateUtils.formatDate(date)
    }

    fun setPlaceImage(photoMetadata: PhotoMetadata?) {
        if (photoMetadata != null) {
            val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                .setMaxWidth(600)
                .setMaxHeight(300)
                .build()
            placesClient.fetchPhoto(photoRequest).addOnSuccessListener { fetchPhotoResponse ->
                image = fetchPhotoResponse.bitmap
                context!!.placeImage.setImageBitmap(image)
                Palette.from(image!!).generate { palette ->
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
        }
    }

    companion object {
        private const val TAG = "PlaceFragment"
    }
}
