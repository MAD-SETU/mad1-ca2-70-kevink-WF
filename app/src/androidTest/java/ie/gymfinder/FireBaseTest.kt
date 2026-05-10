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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var gymStoreFirebase: GymFireStore
    private lateinit var testGym: GymModel
    @Before
    fun setUp() {
        gymStoreFirebase.fetchGyms(
            onResult = TODO()
        )
        testGym = GymModel(title = "Fuze", description = "Fuze gym is a good gym in waterford", counties = "Waterford", rating = 2f, id = generateRandomId())
    }

    @Test
    fun useAppContext() {
        gymStoreFirebase = GymFireStore(InstrumentationRegistry.getInstrumentation().targetContext)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ie.gymfinder", appContext.packageName)



    }
    @Test
    fun createGym(){

        gymStoreFirebase.create(testGym)
        assertNotNull("Found Gym",testGym)
    }
    @Test
    fun updateGym(){
        gymStoreFirebase.create(testGym)
        testGym.description = "updated"
        gymStoreFirebase.update(testGym)
        assertEquals("updated",testGym.description)
    }
    @Test
    fun removeGym(){

        gymStoreFirebase.delete(testGym)
        val foundGym = gymStoreFirebase.findAll().find { p -> p.id == testGym.id }
        assertNull("Gym removed from store", foundGym)
    }
}