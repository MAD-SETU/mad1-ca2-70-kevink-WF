package ie.gymfinder.views.gymlist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.gymfinder.main.MainApp

class GymListPresenter(val view: GymListView ) {
    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0


}