package ie.gymfinder.models

import ie.gymfinder.models.GymModel

interface GymStore {
    fun findAll(): List<GymModel>
    fun create(gym: GymModel)

    fun update(gym: GymModel)

    fun delete(gym: GymModel)
}