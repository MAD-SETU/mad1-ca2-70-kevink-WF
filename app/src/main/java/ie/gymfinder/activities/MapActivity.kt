package ie.gymfinder.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.gymfinder.R
import ie.gymfinder.databinding.ActivityMapBinding
import ie.gymfinder.models.Location
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import ie.gymfinder.models.GymModel
import android.widget.ImageView
import android.net.Uri
import com.squareup.picasso.Picasso

class MapActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.
    OnMarkerDragListener,
    OnMarkerClickListener,
    GoogleMap.InfoWindowAdapter
{
    private var location = Location()
    private var gym = GymModel()
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("location")) {
            location = intent.getParcelableExtra<Location>("location")!!
        }
        if (intent.hasExtra("gym")) {
            gym = intent.getParcelableExtra<GymModel>("gym")!!
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        onBackPressedDispatcher.addCallback(this) {
            val resultIntent = Intent()
            resultIntent.putExtra("location", location)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMarkerClickListener(this)
        map.setInfoWindowAdapter(this)
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title(gym.title)
            .snippet(gym.title)
            .draggable(true)
            .position(loc)

        map.addMarker(options)
        map.setOnMarkerDragListener(this)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom)
        )
    }
    override fun getInfoWindow(marker: Marker): View?{
        val view = LayoutInflater.from(this).inflate(R.layout.custom_info_window,null)
        val title : TextView = view.findViewById(R.id.infoWindowTitle)
        val desc : TextView = view.findViewById(R.id.infoWindowDesc)
        val image : ImageView = view.findViewById(R.id.infoWindowIv)

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
        return view

    }
    override fun getInfoContents(marker: Marker): View? {
        return null
    }
    override fun onMarkerDragStart(marker: Marker) {}

    override fun onMarkerDrag(marker: Marker) {}

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()
        return true
    }


    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }

}