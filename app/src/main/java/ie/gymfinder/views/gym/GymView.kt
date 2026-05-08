package ie.gymfinder.views.gym

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.gymfinder.R
import ie.gymfinder.views.gym.GymPresenter
import ie.gymfinder.adapters.GymAdapter
import ie.gymfinder.adapters.GymListener
import ie.gymfinder.databinding.ActivityGymListBinding
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel

class GymView : AppCompatActivity(), GymListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGymListBinding
    lateinit var presenter: GymPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGymListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = GymPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadGyms()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddGym() }
            R.id.item_map -> { presenter.doShowGymsMap() }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onGymClick(gym: GymModel, position: Int) {
        this.position = position
        presenter.doEditPlacemark(gym, this.position)
    }
    private fun loadGyms() {
        binding.recyclerView.adapter = GymAdapter(presenter.getGyms(), this)
        onRefresh()
    }
    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getGyms().size)
    }
    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}