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

val JSON_FILE = "gyms.json"
val gsonBuilder: GsonBuilder = GsonBuilder().setPrettyPrinting()
val listType = object : TypeToken<ArrayList<GymModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class GymJsonStore(private val context: Context) : GymStore {

    var gyms = mutableListOf<GymModel>()

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
            serialize()
        }
    }

    override fun delete(gym: GymModel) {
        gyms.remove(gym)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.create().toJson(gyms, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        gyms = gsonBuilder.create().fromJson(jsonString, listType)
    }

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

    private fun exists(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }
}
