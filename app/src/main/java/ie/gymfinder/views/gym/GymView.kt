package ie.gymfinder.views.gym

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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

        presenter = GymPresenter(this)
        setupSpinner()
        val layoutManager = LinearLayoutManager(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheGym(
                binding.GymTitle.text.toString(),
                binding.description.text.toString(),
                binding.countySpinner.selectedItem.toString()
            )
            presenter.doSelectImage()
        }
        binding.gymLocation.setOnClickListener {
            presenter.cacheGym(
                binding.GymTitle.text.toString(),
                binding.description.text.toString(),
                binding.countySpinner.selectedItem.toString()
            )
            presenter.doSetLocation()
        }

        binding.btnAdd.setOnClickListener {
            presenter.doAddOrSave(
                binding.GymTitle.text.toString(),
                binding.description.text.toString(),
                binding.countySpinner.selectedItem.toString()
            )
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
        binding.btnAdd.setText(R.string.save_gym)
        Picasso.get()
            .load(gym.image)
            .into(binding.gymImage)
        if (gym.image.isEmpty()) {
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