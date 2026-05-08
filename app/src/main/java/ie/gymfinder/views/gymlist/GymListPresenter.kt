package ie.gymfinder.views.gymlist

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.gymfinder.R
import ie.gymfinder.main.MainApp
import ie.gymfinder.models.GymModel
import ie.gymfinder.views.gym.GymView
import ie.gymfinder.views.map.GymMapView
import ie.gymfinder.views.setting.SettingView

class GymListPresenter(val view: GymListView ) {
    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }
    fun getGyms() = app.gyms.findAll()

    fun doAddGym() {
        val launcherIntent = Intent(view, GymView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditGym(gym: GymModel, pos: Int) {
        val launcherIntent = Intent(view, GymView::class.java)
        launcherIntent.putExtra("gym_edit", gym)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }
    fun doShowGymsMap() {
        val launcherIntent = Intent(view, GymMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun filterGyms(county: String) {
        val allGyms = app.gyms.findAll()
        val filteredList = if (county == view.getString(R.string.all)) {
            allGyms
        } else {
            allGyms.filter { it.counties == county }
        }
        view.showGyms(filteredList)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }

    fun loadGyms() {
        val selectedCounty = view.getSelectedCounty()
        filterGyms(selectedCounty)
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

    fun doShowSetting() {
        val launcherIntent = Intent(view, SettingView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }
}
