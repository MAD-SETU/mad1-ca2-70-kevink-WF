package ie.gymfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.net.Uri
import com.squareup.picasso.Picasso
import ie.gymfinder.models.GymModel
import ie.gymfinder.databinding.CardGymBinding

interface GymListener {
    fun onGymClick(gym: GymModel)
}

class GymAdapter(private var gyms: List<GymModel>,
                 private var listener: GymListener) :
    RecyclerView.Adapter<GymAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGymBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val gym = gyms[holder.adapterPosition]
        holder.bind(gym,listener)
    }

    override fun getItemCount(): Int = gyms.size

    class MainHolder(private val binding: CardGymBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gym: GymModel, listener: GymListener) {
            binding.gymTitle.text = gym.title
            binding.description.text = gym.description
            if (gym.image.isNotEmpty()) {
                try {
                    Picasso.get().load(gym.image).resize(200, 200).into(binding.imageIcon)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            binding.root.setOnClickListener { listener.onGymClick(gym) }
        }
    }

}
