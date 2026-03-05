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

    override fun update(gym: GymModel) {
        val foundGym: GymModel? = gyms.find { p -> p.id == gym.id }
        if (foundGym != null) {
            foundGym.title = gym.title
            foundGym.description = gym.description
            logAll()
        }
    }
}