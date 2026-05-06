package ie.gymfinder.main

import android.app.Application

import com.google.firebase.firestore.FirebaseFirestore
import ie.gymfinder.models.GymJsonStore
import ie.gymfinder.models.GymStore
import timber.log.Timber
import timber.log.Timber.Forest.i


class MainApp : Application() {

    lateinit var gyms: GymStore
    override fun onCreate() {
        super.onCreate()
//       gyms = GymMemStore()
        gyms = GymJsonStore(applicationContext)


        Timber.plant(Timber.DebugTree())

        i("GymFinder started")
    }
}
