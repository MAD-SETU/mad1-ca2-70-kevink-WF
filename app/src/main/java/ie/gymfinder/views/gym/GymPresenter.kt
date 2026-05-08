package ie.gymfinder.views.gym

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.gymfinder.activities.GymActivity
import ie.gymfinder.activities.GymMapsActivity
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel

class GymPresenter(val view: GymView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getGyms() = app.gyms.findAll()

    fun doAddGym(){
        val launcherIntent = Intent(view, GymActivity::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditPlacemark(gym: GymModel, pos: Int) {
        val launcherIntent = Intent(view, GymActivity::class.java)
        launcherIntent.putExtra("gym_edit", gym)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowGymsMap() {
        val launcherIntent = Intent(view, GymMapsActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}