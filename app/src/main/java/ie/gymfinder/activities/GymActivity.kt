package ie.gymfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.gymfinder.databinding.ActivityMainBinding
import timber.log.Timber


class GymActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var gym = GymModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.Forest.plant(Timber.DebugTree())
        Timber.Forest.i("Gym Activity started..")


        binding.btnAdd.setOnClickListener() {
            gym.title = binding.GymTitle.text.toString()
            if (gym.title.isNotEmpty()) {
                Timber.Forest.i("add Button Pressed: ${gym.title}")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
