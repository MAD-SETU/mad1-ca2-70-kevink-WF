package ie.gymfinder.views.gym

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import ie.gymfinder.R
import ie.gymfinder.databinding.ActivityMainBinding
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel
import timber.log.Timber.Forest.i

class GymView : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    lateinit var presenter: GymPresenter
    var gym = GymModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        setupSpinner()
        presenter = GymPresenter(this)
        val layoutManager = LinearLayoutManager(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheGym(
                binding.GymTitle.text.toString(),
                binding.description.text.toString(),
                binding.countySpinner.selectedItem.toString(),
                binding.ratingBar.rating
            )
            presenter.doSelectImage()
        }
        binding.gymLocation.setOnClickListener {
            presenter.cacheGym(
                binding.GymTitle.text.toString(),
                binding.description.text.toString(),
                binding.countySpinner.selectedItem.toString(),
                binding.ratingBar.rating

            )
            presenter.doSetLocation()
        }

        binding.btnAdd.setOnClickListener {
            presenter.doAddOrSave(
                binding.GymTitle.text.toString(),
                binding.description.text.toString(),
                binding.countySpinner.selectedItem.toString(),
                 binding.ratingBar.rating,

            )
        }
        binding.DeleteGym.setOnClickListener {
            presenter.doDelete()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showGym(gym: GymModel) {
        binding.GymTitle.setText(gym.title)
        binding.description.setText(gym.description)
        binding.ratingBar.rating = gym.rating
        binding.btnAdd.setText(R.string.save_gym)

        val adapter = binding.countySpinner.adapter as ArrayAdapter<String>
        val spinnerPosition = adapter.getPosition(gym.counties)
        binding.countySpinner.setSelection(spinnerPosition)

        if (gym.image.isNotEmpty()) {
            Picasso.get()
                .load(gym.image)
                .into(binding.gymImage)
            binding.chooseImage.setText(R.string.change_gym_image)
        }
    }
    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.gymImage)
        binding.chooseImage.setText(R.string.change_gym_image)
    }

    fun showInvalidTitle() {
        Toast.makeText(this, R.string.enter_gym_title, Toast.LENGTH_LONG).show()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.Counties)
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countySpinner.adapter = adapter
    }
}