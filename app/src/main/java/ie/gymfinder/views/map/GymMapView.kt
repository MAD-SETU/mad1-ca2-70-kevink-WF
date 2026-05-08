package ie.gymfinder.views.map



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import ie.gymfinder.databinding.ActivityGymMapsBinding
import ie.gymfinder.databinding.ContentGymsMapsBinding
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel
import timber.log.Timber.Forest.i

class GymMapView : AppCompatActivity() , GoogleMap.OnMarkerClickListener{

    private lateinit var binding: ActivityGymMapsBinding
    private lateinit var contentBinding: ContentGymsMapsBinding
    lateinit var app: MainApp
    lateinit var presenter: GymMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        app = application as MainApp

        app.gyms.findAll().forEach {
            i("Gym: ${it.title} lat=${it.lat} lng=${it.lng} zoom=${it.zoom}")
        }
        binding = ActivityGymMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = GymMapPresenter(this)

        contentBinding = ContentGymsMapsBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{
            presenter.doPopulateMap(it)
        }
    }
    fun showGym(gym: GymModel) {
        contentBinding.currentTitle.text = gym.title
        contentBinding.currentDescription.text = gym.description
        Picasso.get()
            .load(gym.image)
            .into(contentBinding.currentImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
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