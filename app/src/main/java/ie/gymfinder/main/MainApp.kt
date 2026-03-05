package ie.gymfinder.main

import android.app.Application
import ie.gymfinder.models.GymModel
import ie.gymfinder.models.GymMemStore
import timber.log.Timber
import timber.log.Timber.Forest.i


class MainApp : Application() {

    val gyms = GymMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("GymFinder started")
    }
}
