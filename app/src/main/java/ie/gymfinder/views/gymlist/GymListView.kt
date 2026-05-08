package ie.gymfinder.views.gymlist


import ie.gymfinder.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.gymfinder.databinding.ActivityGymListBinding
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel

class GymListView : AppCompatActivity(), GymListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGymListBinding
    lateinit var presenter: GymListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGymListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = GymListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // Set up the spinner
        val counties = resources.getStringArray(R.array.Counties).toMutableList()
        counties.add(0, getString(R.string.all))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, counties)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countySpinner.adapter = adapter

        binding.countySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCounty = parent?.getItemAtPosition(position).toString()
                presenter.filterGyms(selectedCounty)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        presenter.loadGyms()
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
        presenter.doEditGym(gym, this.position)
    }

    fun getSelectedCounty(): String {
        return binding.countySpinner.selectedItem?.toString() ?: getString(R.string.all)
    }

    fun showGyms(gyms: List<GymModel>) {
        binding.recyclerView.adapter = GymAdapter(gyms, this)
    }

    fun onRefresh() {
        presenter.loadGyms()
    }

    fun onDelete(position: Int) {
        presenter.loadGyms()
    }
}
