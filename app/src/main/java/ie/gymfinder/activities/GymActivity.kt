package ie.gymfinder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.gymfinder.R
import ie.gymfinder.databinding.ActivityMainBinding
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel
import timber.log.Timber


class GymActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var gym = GymModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        binding.DeleteGym.visibility = View.GONE

        var edit = false
        if (intent.hasExtra("gym_edit")) {
            edit = true
            gym = intent.extras?.getParcelable("gym_edit")!!
            binding.GymTitle.setText(gym.title)
            binding.description.setText(gym.description)
            binding.btnAdd.setText(R.string.save_Gym)
            binding.DeleteGym.visibility = View.VISIBLE
        }
        binding.DeleteGym.setOnClickListener {
            app.gyms.delete(gym)
            setResult(RESULT_OK)
            finish()
        }



        binding.btnAdd.setOnClickListener() {
            gym.title = binding.GymTitle.text.toString()
            gym.description = binding.description.text.toString()
            if (gym.title.isNotEmpty()) {
                if (!edit)
                app.gyms.create(gym.copy())
                else {
                    app.gyms.update(gym.copy())
                }
                Timber.Forest.i("add Button Pressed: ${gym.title}")
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it, R.string.EnterTitle, Snackbar.LENGTH_LONG)
                    .show()
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
