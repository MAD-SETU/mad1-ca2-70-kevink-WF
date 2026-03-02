package ie.gymfinder.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.gymfinder.databinding.ActivityMainBinding
import ie.gymfinder.main.MainApp
import timber.log.Timber


class GymActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var gym = GymModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        binding.btnAdd.setOnClickListener() {
            gym.title = binding.GymTitle.text.toString()
            gym.description = binding.description.text.toString()
            if (gym.title.isNotEmpty()) {
                app.gyms.add(gym.copy())
                Timber.Forest.i("add Button Pressed: ${gym.title}")
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
