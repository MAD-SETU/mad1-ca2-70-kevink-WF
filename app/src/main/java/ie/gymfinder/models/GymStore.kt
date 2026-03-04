package ie.gymfinder.models

import ie.gymfinder.activities.GymModel

interface GymStore {
    fun findAll(): List<GymModel>
    fun create(gym: GymModel)
}