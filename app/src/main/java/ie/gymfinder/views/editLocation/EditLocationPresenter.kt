package ie.gymfinder.views.editLocation

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import ie.gymfinder.R
import ie.gymfinder.models.GymModel
import ie.gymfinder.views.editLocation.EditLocationView
import ie.gymfinder.models.Location

class EditLocationPresenter (val view: EditLocationView) {

    var location = Location()
    var gym = GymModel()
    init {
        //location = view.intent.extras?.getParcelable("location",Location::class.java)!!
        location = view.intent.extras?.getParcelable("location")!!
        gym = view.intent.extras?.getParcelable("gym")!!
    }

    fun initMap(map: GoogleMap) {
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Gym")
            .snippet("GPS : $loc")
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        map.setOnMarkerDragListener(view)
        map.setOnMarkerClickListener(view)
    }
    fun getInfoWindow(): View {
        val infoView = LayoutInflater.from(view)
            .inflate(R.layout.custom_info_window, null)

        val title: TextView = infoView.findViewById(R.id.infoWindowTitle)
        val desc: TextView = infoView.findViewById(R.id.infoWindowDesc)
        val image: ImageView = infoView.findViewById(R.id.infoWindowIv)

        title.text = gym.title
        desc.text = gym.description

        if (gym.image.isNotEmpty()) {
            try {
                Picasso.get()
                    .load(gym.image)
                    .into(image)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return infoView
    }
    fun doUpdateLocation(lat: Double, lng: Double, zoom: Float) {
        location.lat = lat
        location.lng = lng
        location.zoom = zoom
    }

    fun doOnBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        view.setResult(Activity.RESULT_OK, resultIntent)
        view.finish()
    }

    fun doUpdateMarker(marker: Marker) {
        val loc = LatLng(location.lat, location.lng)
        marker.snippet = "GPS : $loc"
    }
}