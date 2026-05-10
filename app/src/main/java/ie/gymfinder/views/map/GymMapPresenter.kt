package ie.gymfinder.views.map
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

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