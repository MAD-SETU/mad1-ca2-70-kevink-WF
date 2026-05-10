package ie.gymfinder

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import ie.gymfinder.models.GymFireStore
import ie.gymfinder.models.GymModel
import ie.gymfinder.models.generateRandomId

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var gymStoreFirebase: GymFireStore
    private lateinit var testGym: GymModel

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        gymStoreFirebase = GymFireStore(appContext)
        testGym = GymModel(
            title = "Fuze",
            description = "Fuze gym is a good gym in waterford",
            counties = "Waterford",
            rating = 2f,
            id = generateRandomId()
        )
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ie.gymfinder", appContext.packageName)
    }

    @Test
    fun createGym() {
        gymStoreFirebase.create(testGym)
        val foundGym = gymStoreFirebase.findAll().find { it.id == testGym.id }
        assertNotNull("Gym should be found in store", foundGym)
        assertEquals(testGym.title, foundGym?.title)
    }

    @Test
    fun updateGym() {
        gymStoreFirebase.create(testGym)
        val newDescription = "updated description"
        testGym.description = newDescription
        gymStoreFirebase.update(testGym)
        
        val updatedGym = gymStoreFirebase.findAll().find { it.id == testGym.id }
        assertEquals(newDescription, updatedGym?.description)
    }

    @Test
    fun removeGym() {
        gymStoreFirebase.create(testGym)
        gymStoreFirebase.delete(testGym)
        val foundGym = gymStoreFirebase.findAll().find { it.id == testGym.id }
        assertNull("Gym should be removed from store", foundGym)
    }
}
