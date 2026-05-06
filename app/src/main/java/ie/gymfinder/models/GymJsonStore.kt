package ie.gymfinder.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.Random

val JSON_FILE = "gyms2.json"
val gsonBuilder: GsonBuilder = GsonBuilder().setPrettyPrinting()
val listType = object : TypeToken<ArrayList<GymModel>>() {}.type
// to make sure gym has unique id
fun generateRandomId(): Long {
    return Random().nextLong()
}

class GymJsonStore(private val context: Context) : GymStore {
    // Use a mutable list so we can add remove and update gyms while the app is running
    var gyms = mutableListOf<GymModel>()
   // check if json file is there
    init {
        if (exists(JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<GymModel> {
        return gyms
    }

    override fun create(gym: GymModel) {
        gym.id = generateRandomId()
        gyms.add(gym)
        serialize()
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
            serialize()
        }
    }

    override fun delete(gym: GymModel) {
        gyms.remove(gym)
        serialize()
    }
// saving to json file
    private fun serialize() {
        val jsonString = gsonBuilder.create().toJson(gyms, listType)
        write(JSON_FILE, jsonString)
    }
// reading from json file
    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        gyms = gsonBuilder.create().fromJson(jsonString, listType)
    }
// writing to file
    private fun write(fileName: String, data: String) {
        val file = File(context.filesDir, fileName)
        try {
            val outputStreamWriter = BufferedWriter(FileWriter(file))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
// reads and returns raw text
    private fun read(fileName: String): String {
        val file = File(context.filesDir, fileName)
        var str = ""
        try {
            val bufferedReader = BufferedReader(FileReader(file))
            str = bufferedReader.use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return str
    }
 // helper to check if it exists
    private fun exists(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }
}
