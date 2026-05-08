package ie.gymfinder.views.gym

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
//import ie.gymfinder.activities.GymActivity
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel
import ie.gymfinder.models.Location
import ie.gymfinder.views.editLocation.EditLocationView
import org.checkerframework.checker.units.qual.g
import timber.log.Timber

class GymPresenter(val view: GymView) {
    var gym = GymModel()
    var app: MainApp = view.application as MainApp
    private lateinit var locationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST = 1001

    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
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

    fun doAddOrSave(title: String, description: String, counties: String) {
        gym.title = title
        gym.description = description
        gym.counties = counties
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
        //   showImagePicker(imageIntentLauncher,view)
        val request = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
        imageIntentLauncher.launch(request)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
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


    fun cacheGym (title: String, description: String,counties: String) {
        gym.title = title
        gym.description = description
        gym.counties = counties

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