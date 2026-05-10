package ie.gymfinder.views.gym

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel
import ie.gymfinder.models.Location
import ie.gymfinder.views.editLocation.EditLocationView
import timber.log.Timber
import android.Manifest
import android.annotation.SuppressLint
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.Task

class GymPresenter(val view: GymView) {
    var gym = GymModel()
    var app: MainApp = view.application as MainApp


    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(view)
    }

    var edit = false
    private var position: Int = 0

    init {
        if (view.intent.hasExtra("gym_edit")) {
            edit = true
            gym = view.intent.extras?.getParcelable("gym_edit")!!
            view.showGym(gym)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun getGyms() = app.gyms.findAll()
    
    fun doAddOrSave(title: String, description: String, counties: String,rating: Float) {
        if (title.isEmpty()) {
            view.showInvalidTitle()
            return
        }
        gym.title = title
        gym.description = description
        gym.counties = counties
        gym.rating = rating
        if (edit) {
            app.gyms.update(gym)
        } else {
            app.gyms.create(gym)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }
    fun doCancel() {
        view.finish()
    }
    fun doDelete() {
        view.setResult(99)
        app.gyms.delete(gym)
        view.finish()
    }
    fun doSelectImage() {
        val request = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
        imageIntentLauncher.launch(request)
    }

    fun doSetLocation() {
        // Maybe store in this in object and match them in counties?
        val location = if (gym.counties == "Waterford") {
            Location(52.26060093399676, -7.106542926332248, 15f)
        } else {
            Location(52.83379701025627, -6.920591105076157, 15f)
        }
        if (gym.zoom != 0f) {
            location.lat =  gym.lat
            location.lng = gym.lng
            location.zoom = gym.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location
            )
            .putExtra("gym",gym)
        mapIntentLauncher.launch(launcherIntent)
    }


    fun cacheGym (title: String, description: String,counties: String,rating: Float) {
        gym.title = title
        gym.description = description
        gym.counties = counties
        gym.rating = rating
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = view.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try{
                view.contentResolver
                    .takePersistableUriPermission(it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION )
                gym.image = it.toString() // The returned Uri
                Timber.i("IMG :: ${gym.image}")
                view.updateImage(gym.image.toUri())
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            gym.lat = location.lat
                            gym.lng = location.lng
                            gym.zoom = location.zoom
                        }
                    }
                }
            }
    }
}