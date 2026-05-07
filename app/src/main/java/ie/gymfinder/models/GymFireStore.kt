package ie.gymfinder.models

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class GymFireStore(private val context: Context) : GymStore {

    private val db = FirebaseFirestore.getInstance()
    private val gyms = mutableListOf<GymModel>()

    init {
        fetchGyms {  }
    }

    override fun findAll(): List<GymModel> = gyms

    fun fetchGyms(onResult: (List<GymModel>) -> Unit) {

        db.collection("gyms")
            .get()
            .addOnSuccessListener { result ->

                gyms.clear()

                for (doc in result.documents) {
                    doc.toObject(GymModel::class.java)?.let { gym ->
                        gyms.add(gym)
                    }
                }

                Timber.i("Loaded gyms: ${gyms.size}")
                onResult(gyms)
            }
            .addOnFailureListener {
                Timber.e(it, "Failed to load gyms")
                onResult(emptyList())
            }
    }

    override fun create(gym: GymModel) {
        gym.id = generateRandomId()
        gyms.add(gym)
        db.collection("gyms")
            .document(gym.id.toString())
            .set(gym)
    }

    override fun update(gym: GymModel) {
        val foundGym: GymModel? = gyms.find { p -> p.id == gym.id }
        if (foundGym != null) {
            foundGym.title = gym.title
            foundGym.description = gym.description
            foundGym.counties = gym.counties
            foundGym.image = gym.image
            foundGym.lat = gym.lat
            foundGym.lng = gym.lng
            foundGym.zoom = gym.zoom
        }
        db.collection("gyms")
            .document(gym.id.toString())
            .set(gym)
    }

    override fun delete(gym: GymModel) {
        gyms.remove(gym)
        db.collection("gyms")
            .document(gym.id.toString())
            .delete()
    }
}