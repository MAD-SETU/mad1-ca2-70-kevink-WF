package ie.gymfinder.views.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ie.gymfinder.R
import ie.gymfinder.databinding.ActivitySettingBinding
import ie.gymfinder.main.MainApp

class SettingView : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    lateinit var presenter: SettingPresenter
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        presenter = SettingPresenter(this)

        binding.toolbarAdd.title = R.string.Setting.toString()
        setSupportActionBar(binding.toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnDarkMode.setOnClickListener {
            presenter.toggleDarkMode()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
