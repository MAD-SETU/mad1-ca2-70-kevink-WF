package ie.gymfinder.models

import ie.gymfinder.models.GymModel
import timber.log.Timber.Forest.i

class GymMemStore : GymStore{
    val gyms = ArrayList<GymModel>()

    override fun findAll(): List<GymModel> {
        return gyms
    }
    override fun create(gym: GymModel) {
        gyms.add(gym)
    }
    fun logAll() {
        gyms.forEach{ i("$it") }
    }
}