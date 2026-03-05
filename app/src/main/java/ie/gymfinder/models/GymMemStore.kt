package ie.gymfinder.models

import ie.gymfinder.models.GymModel
import timber.log.Timber.Forest.i
var lastId = 0L
class GymMemStore : GymStore{
    internal fun getID(): Long {
        return lastId++
    }
    val gyms = ArrayList<GymModel>()

    override fun findAll(): List<GymModel> {
        return gyms
    }
    override fun create(gym: GymModel) {
        gym.id = getID()
        gyms.add(gym)
        logAll()
    }
    fun logAll() {
        gyms.forEach{ i("$it") }
    }
    override fun delete(gym: GymModel){
       gyms.remove(gym)
    }
    override fun update(gym: GymModel) {
        val foundGym: GymModel? = gyms.find { p -> p.id == gym.id }
        if (foundGym != null) {
            foundGym.title = gym.title
            foundGym.description = gym.description
            foundGym.counties = gym.counties
            logAll()
        }
    }

}