package ie.gymfinder.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.gymfinder.helpers.*
import org.checkerframework.checker.units.qual.g
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

val JSON_FILE = "gyms3.json"
val gson: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeHierarchyAdapter(Uri::class.java, UriParser())
    .create()

val listType: Type = object : TypeToken<ArrayList<GymModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class GymJsonStore(private val context: Context) : GymStore {
    // Use a mutable list so we can add remove and update gyms while the app is running
    var gyms = mutableListOf<GymModel>()

    // check if json file is there
    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<GymModel> {
        logAll()
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
            foundGym.rating = gym.rating
            serialize()
        }
    }

    override fun delete(gym: GymModel) {
        gyms.remove(gym)
        serialize()
    }
    override fun deleteAll() {
        gyms.clear()
        serialize()
    }

    override fun findById(id: Long): GymModel? {
        val foundGym: GymModel? = gyms.find { it.id == id }
        return foundGym
    }

    // saving to json file
    private fun serialize() {
        val jsonString = gson.toJson(gyms, listType)
        write(context, JSON_FILE, jsonString)
    }

    // reading from json file
    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        gyms = gson.fromJson(jsonString, listType) ?: mutableListOf()
    }

    private fun logAll() {
        gyms.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Uri {
        return if (json != null && json.isJsonPrimitive) {
            Uri.parse(json.asString)
        } else {
            Uri.EMPTY
        }
    }

    override fun serialize(src: Uri?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
