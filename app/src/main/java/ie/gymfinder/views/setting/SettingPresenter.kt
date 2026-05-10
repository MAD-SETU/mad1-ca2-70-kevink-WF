package ie.gymfinder.views.setting

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import ie.gymfinder.main.MainApp

class SettingPresenter(val view: SettingView) {

    var app: MainApp = view.application as MainApp

    fun toggleDarkMode() {

        val currentMode = AppCompatDelegate.getDefaultNightMode()

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
            builder
                .setTitle("Are you sure?")
                .setPositiveButton("Yes") { dialog, which ->
                    view.app.gyms.deleteAll()
                }
                .setNegativeButton("No") { dialog, which ->
                  dialog.cancel()
                }
                .setItems(arrayOf("Your about to delete ${view.presenter.app.gyms.findAll().size} Gyms")) { dialog, which ->

                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
