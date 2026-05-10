package ie.gymfinder.models

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import timber.log.Timber

class GymFireStore(private val context: Context) : GymStore {

    private val db = FirebaseFirestore.getInstance()
    private val gyms = mutableListOf<GymModel>()

    init {
        fetchGyms { }
    }

    override fun findAll(): List<GymModel> = gyms

    fun fetchGyms(onResult: (List<GymModel>) -> Unit) {

        // FAST cache load
        db.collection("gyms")
            .get(Source.CACHE)
            .addOnSuccessListener { result ->

                gyms.clear()

                for (doc in result.documents) {
                    doc.toObject(GymModel::class.java)?.let {
                        gyms.add(it)
                    }
                }

                Timber.i("Loaded cached gym")


                onResult(gyms)
            }


        db.collection("gyms")
            .get(Source.SERVER)
            .addOnSuccessListener { result ->

                gyms.clear()

                for (doc in result.documents) {
                    doc.toObject(GymModel::class.java)?.let {
                        gyms.add(it)
                    }
                }

                Timber.i("Loaded fresh gyms")
                onResult(gyms)
            }
            .addOnFailureListener {
                Timber.e(it, "Server fetch failed")
            }
    }
    override fun deleteAll(){
        db.collection("gyms").document().delete()
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
            foundGym.rating = gym.rating
        }
        db.collection("gyms")
            .document(gym.id.toString())
            .set(gym)
    }

    override fun delete(gym: GymModel) {
        Timber.i("Deleting gym id: ${gym.id}")
        gyms.removeIf { it.id == gym.id }
        db.collection("gyms")
            .document(gym.id.toString())
            .delete()
    }

    override fun findById(id: Long): GymModel? {
        val foundGym: GymModel? = gyms.find { it.id == id }
        return foundGym
    }
}