package ie.gymfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ie.gymfinder.databinding.ActivityGymMapsBinding
import ie.gymfinder.databinding.ContentGymMapsBinding
import ie.gymfinder.main.MainApp
import com.google.android.gms.maps.model.Marker
class GymMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityGymMapsBinding
    private lateinit var contentBinding: ContentGymMapsBinding

    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGymMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        contentBinding = binding.include
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.gyms.findAll().forEach {
            val loc = LatLng(it.lat, it.lng,)
            val options = MarkerOptions().title(it.title).position(loc)
            val zoom = it.zoom
            map.addMarker(options)?.tag = it.id
        }
        map.setOnMarkerClickListener(this)
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        contentBinding.currentTitle.text = marker.title
        contentBinding.currentImage
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}
