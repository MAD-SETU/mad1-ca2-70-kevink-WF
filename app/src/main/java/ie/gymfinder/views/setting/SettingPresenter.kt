package ie.gymfinder.views.setting

import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import ie.gymfinder.R
import ie.gymfinder.main.MainApp
import ie.gymfinder.views.gym.GymView
import ie.gymfinder.views.gymlist.GymListView

class SettingPresenter(val view: SettingView) {

    var app: MainApp = view.application as MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    fun toggleDarkMode() {
        // Gets current Theme
        val currentMode = AppCompatDelegate.getDefaultNightMode()
          // sets it to night mode
        val newMode =
            currentMode != AppCompatDelegate.MODE_NIGHT_YES

        if (newMode) {
           
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO

            )
        }
    }

        fun showDialog() {
            val builder: AlertDialog.Builder = AlertDialog.Builder(view)
            // Sets Message for Dialog
            val message = view.getString(R.string.dialogdelete_message) +
                    " "+
                    view.presenter.view.app.gyms.findAll().size  + " " +
                    view.getString(R.string.dialogdelete_gyms)
            builder
                // Title
                .setTitle(view.getString(R.string.dialogdelete_sure))
                .setMessage(message)
                // Yes Buttons Deletes all gyms And goes to  GymListView
                .setPositiveButton(view.getString(R.string.dialogdelete_yes)) { dialog, which ->
                    app.gyms.deleteAll()
                    view.startActivity(
                        Intent(view, GymListView::class.java)
                    )
                }
                    // No Button Just cancels
                .setNegativeButton(view.getString(R.string.dialogdelete_no)) { dialog, which ->
                  dialog.cancel()
                }


            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
