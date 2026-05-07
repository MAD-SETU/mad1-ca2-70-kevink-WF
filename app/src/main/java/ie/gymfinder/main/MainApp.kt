package ie.gymfinder.main

import android.app.Application

import ie.gymfinder.models.GymJsonStore
import ie.gymfinder.models.GymStore
import timber.log.Timber
import timber.log.Timber.Forest.i
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class MainApp : Application() {
    lateinit var db: DatabaseReference
    lateinit var gyms: GymStore
    lateinit var gyms2: GymStore

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        // gyms = GymMemStore()
        gyms = GymJsonStore(applicationContext)
//        gyms = GymFirebase(applicationContext)

        i("GymFinder started")
    }
}
