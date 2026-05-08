package ie.gymfinder.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.gymfinder.R
import ie.gymfinder.adapters.GymAdapter
import ie.gymfinder.adapters.GymListener
import ie.gymfinder.databinding.ActivityGymListBinding
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel

class GymListActivity : AppCompatActivity(), GymListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGymListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGymListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // Get countries from resources then adds to a mutableList
        val counties = resources.getStringArray(R.array.Counties).toMutableList()
        // adds all of the counties to the list needs getString since
        counties.add(0, getString(R.string.all))
        // Creates an Adapter using the countries list and this activity as the context
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, counties)
        // changes the layouts of the drop down elements
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Sets the adapter to the spinner
        binding.countySpinner.adapter = adapter
        // makes the listerner for the spinner
        binding.countySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
           // This is adapter is triggered the event
            // when a new item is selected
            // and calls the filterList function
            // with the selected item
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val selectedCounty = parent?.getItemAtPosition(position).toString()
                filterList(selectedCounty)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //  loadsGyms() is called when the activity is created
        loadGyms()
    }
  //  loadsGyms() is called when the activity is created
    private fun loadGyms() {
        val selectedCounty = binding.countySpinner.selectedItem?.toString() ?: getString(R.string.all)
        filterList(selectedCounty)
    }

    private fun filterList(county: String) {
        // gets all gyms
        val allGyms = app.gyms.findAll()
        // if country = all returns all of them
        val filteredList = if (county == getString(R.string.all)) {
            allGyms
        } else {
            // if country all of them
            allGyms.filter { it.counties == county }
        }
        binding.recyclerView.adapter = GymAdapter(filteredList, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, GymActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGymClick(gym: GymModel) {
        val launcherIntent = Intent(this, GymActivity::class.java)
        launcherIntent.putExtra("gym_edit", gym)
        launcherIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                loadGyms()
            }
        }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            // loadsGyms() is called when the activity is created
            if (it.resultCode == Activity.RESULT_OK) {
                loadGyms()
            }
        }
}
