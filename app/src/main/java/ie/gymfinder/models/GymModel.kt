package ie.gymfinder.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GymModel(var id: Long = 0,
                    var title: String = "", var description: String = "", var counties: String = "") : Parcelable