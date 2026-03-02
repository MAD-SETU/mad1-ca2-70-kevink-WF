package ie.gymfinder

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import ie.gymfinder.databinding.ActivityMainBinding
import timber.log.Timber
import timber.log.Timber.Forest.i


class GymActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Gym Activity started..")


        binding.btnAdd.setOnClickListener() {
            val GymTitle = binding.GymTitle.text.toString()
            if (GymTitle.isNotEmpty()) {
                i("add Button Pressed: $GymTitle")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}