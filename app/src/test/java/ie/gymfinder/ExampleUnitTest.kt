package ie.gymfinder

import ie.gymfinder.models.GymFireStore
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
    private lateinit var gymStoreFirebase: GymFireStore
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
    // GymStore
    @Test
    fun createGym(){

        gymStore.create(testGym)
        assertNotNull("Found Gym",testGym)
    }
    @Test
    fun removeGym(){

        gymStore.delete(testGym)
        val foundGym = gymStore.findAll().find { p -> p.id == testGym.id }
        assertNull("Gym removed from store", foundGym)
    }
   @Test
   fun updateGym(){
       gymStore.create(testGym)
       testGym.description = "updated"
       gymStore.update(testGym)
       assertEquals("updated",testGym.description)
   }



    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}