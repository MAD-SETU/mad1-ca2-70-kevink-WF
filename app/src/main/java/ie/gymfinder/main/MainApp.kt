package ie.gymfinder.main

import android.app.Application
import ie.gymfinder.activities.GymModel
import timber.log.Timber
import timber.log.Timber.Forest.i


class MainApp : Application() {

    val gyms = ArrayList<GymModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("GymFinder started")
//        gyms.add(GymModel("One", "About one..."))
//        gyms.add(GymModel("Two", "About two..."))
//        gyms.add(GymModel("Three", "About three..."))
    }
}
