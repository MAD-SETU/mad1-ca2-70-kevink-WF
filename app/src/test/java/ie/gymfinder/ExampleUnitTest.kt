package ie.gymfinder

import org.junit.Test
import org.junit.Before
import org.junit.Assert.*
import ie.gymfinder.models.GymMemStore
import ie.gymfinder.models.GymJsonStore
import ie.gymfinder.models.GymModel

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class ExampleUnitTest {
    private lateinit var gymStore: GymMemStore
    private lateinit var testGym: GymModel

    @Before
    fun setUp() {
        gymStore = GymMemStore()
        testGym = GymModel(title = "Fuze", description = "Fuze gym is a good gym in waterford", counties = "Waterford")

    }

    @Test
    fun sortbycounties(
    ){
        gymStore.create(testGym)
        val sortedGyms = gymStore.findAll().sortedBy { it.counties }
        assertNotNull("Found Gym",testGym)
        assertEquals(sortedGyms[0].counties, "Waterford")
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}