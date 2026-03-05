package ie.gymfinder.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GymModel(var title: String = "", var description: String = "") : Parcelable