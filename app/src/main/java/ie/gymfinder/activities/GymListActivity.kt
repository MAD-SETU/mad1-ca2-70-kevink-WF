package ie.gymfinder.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.gymfinder.R
import ie.gymfinder.databinding.ActivityGymListBinding
import ie.gymfinder.databinding.CardGymBinding
import ie.gymfinder.main.MainApp

class GymListActivity : AppCompatActivity() {

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
        binding.recyclerView.adapter = GymAdapter(app.gyms)
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

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.gyms.size)
            }
        }
}

class GymAdapter constructor(private var gyms: List<GymModel>) :
    RecyclerView.Adapter<GymAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGymBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val gym = gyms[holder.adapterPosition]
        holder.bind(gym)
    }

    override fun getItemCount(): Int = gyms.size

    class MainHolder(private val binding: CardGymBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gym: GymModel) {
            binding.gymTitle.text = gym.title
            binding.description.text = gym.description
        }
    }
}
