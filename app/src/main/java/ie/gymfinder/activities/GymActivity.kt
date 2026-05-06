package ie.gymfinder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.gymfinder.R
import ie.gymfinder.databinding.ActivityMainBinding
import ie.gymfinder.helpers.showImagePicker
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel
import ie.gymfinder.models.Location
import timber.log.Timber
import timber.log.Timber.Forest.i


class GymActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>

    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    var location = Location()

    var gym = GymModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        //  DeleteGym is hidden by default
        binding.DeleteGym.visibility = View.GONE
        registerImagePickerCallback()
        registerMapCallback()
        var edit = false
        if (intent.hasExtra("gym_edit")) {
            edit = true
            gym = intent.extras?.getParcelable("gym_edit")!!
            binding.GymTitle.setText(gym.title)
            binding.description.setText(gym.description)
            // Create an adapter to map the county list to the spinner layout
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Counties))
            val spinnerPosition = adapter.getPosition(gym.counties)
            binding.countySpinner.setSelection(spinnerPosition)
            binding.btnAdd.setText(R.string.save_gym)
            if (gym.image.isNotEmpty()) {
                Picasso.get()
                    .load(gym.image)
                    .into(binding.gymImage)
                binding.chooseImage.setText(R.string.change_gym_image)
            }
            //  DeleteGym is shown
            binding.DeleteGym.visibility = View.VISIBLE
            location.lat = gym.lat
            location.lng = gym.lng
            location.zoom = gym.zoom
        }
        binding.DeleteGym.setOnClickListener {
            app.gyms.delete(gym)
            setResult(RESULT_OK)
            finish()
        }


        binding.gymLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (gym.zoom != 0f) {
                location.lat =  gym.lat
                location.lng = gym.lng
                location.zoom = gym.zoom
            }

            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
                .putExtra("gym", gym)
            mapIntentLauncher.launch(launcherIntent)
        }

        binding.btnAdd.setOnClickListener {
            gym.title = binding.GymTitle.text.toString()
            gym.description = binding.description.text.toString()
            gym.counties = binding.countySpinner.selectedItem.toString()
            if (gym.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_gym_title, Snackbar.LENGTH_LONG)
                        .show()
            } else {
                if (edit) {
                    app.gyms.update(gym.copy())
                } else {
                    app.gyms.create(gym.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            //location = result.data!!.extras?.getParcelable("location",Location::class.java)!!
                            location = result.data!!.extras?.getParcelable("location")!!
                            gym.lat = location.lat
                            gym.lng = location.lng
                            gym.zoom = location.zoom

                            i("Location == $location")
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            gym.image = result.data!!.data.toString()
                            Picasso.get()
                                .load(gym.image)
                                .into(binding.gymImage)
                            binding.chooseImage.setText(R.string.change_gym_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gym, menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }

        return super.onOptionsItemSelected(item)
    }
}
