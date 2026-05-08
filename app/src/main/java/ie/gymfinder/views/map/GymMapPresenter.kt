package ie.gymfinder.views.map
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import ie.gymfinder.R
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel


class GymMapPresenter(val view: GymMapView) {
    var app: MainApp

    init {
        app = view.application as MainApp
    }
    private var selectedGym = GymModel()
    fun doPopulateMap(map: GoogleMap) {
        map.uiSettings.setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(view)
        app.gyms.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions()
                .title(it.title)
                .position(loc)

            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))

        }

    }

    fun doMarkerSelected(marker: Marker) {

        val tag = marker.tag as Long

        val gym = app.gyms.findById(tag)

        if (gym != null) {
            selectedGym = gym
            view.showGym(gym)
        }
    }
}