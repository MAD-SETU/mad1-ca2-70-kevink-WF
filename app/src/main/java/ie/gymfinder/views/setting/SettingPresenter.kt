package ie.gymfinder.views.setting

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
}