package com.porfirio.videogamedb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.porfirio.videogamedb.data.db.model.GameEntity
import com.porfirio.videogamedb.databinding.GameElementBinding

class GameAdapter(): RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private  var games: List<GameEntity> = emptyList()

    class ViewHolder(private val binding: GameElementBinding):RecyclerView.ViewHolder(binding.root){

        val ivIcon = binding.ivIcon

        fun bind(game: GameEntity){
            //Este código lo podemos simplificar
            /*binding.tvTitle.text = game.title
            binding.tvGenre.text = game.genre
            binding.tvDeveloper.text = game.developer*/

            binding.apply {
                tvTitle.text = game.title
                tvGenre.text = game.genre
                tvDeveloper.text = game.developer
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GameElementBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])

        holder.itemView.setOnClickListener{
            //Aqui va el click del elemento

        }

        holder.ivIcon.setOnClickListener{
            //Click para la vista del imageView con el icono
        }
    }

    fun updateList(list: List<GameEntity>){
        games = list
        notifyDataSetChanged()
        
    }
}