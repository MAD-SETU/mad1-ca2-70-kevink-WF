package ie.gymfinder.main

import android.app.Application

import ie.gymfinder.models.GymFireStore
import ie.gymfinder.models.GymStore
import timber.log.Timber
import timber.log.Timber.Forest.i
import com.google.firebase.database.DatabaseReference
import ie.gymfinder.models.GymJsonStore
import ie.gymfinder.models.GymMemStore
class MainApp : Application() {
    lateinit var db: DatabaseReference
    lateinit var gyms: GymStore
    lateinit var gyms2: GymStore

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
//         gyms = GymMemStore()
         gyms = GymJsonStore(applicationContext)
//        gyms = GymFireStore(applicationContext)

        i("GymFinder started")
    }
}
