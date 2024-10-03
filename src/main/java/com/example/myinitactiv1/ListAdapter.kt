package com.example.myinitactiv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ListAdapter(private val filmesHorarios: List<FilmeHorario>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeFilme: TextView = itemView.findViewById(R.id.nome)
        val horario: TextView = itemView.findViewById(R.id.horario)
        val idioma: TextView = itemView.findViewById(R.id.idioma)
        val posterImageView: ImageView = itemView.findViewById(R.id.PosterImageView) // Adicionado para o pôster
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_regionais, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filmeHorario = filmesHorarios[position]
        holder.nomeFilme.text = filmeHorario.nome
        holder.horario.text = filmeHorario.horario
        holder.idioma.text = filmeHorario.idioma

        // Carregar a imagem do pôster usando Picasso
        if (!filmeHorario.posterPath.isNullOrEmpty()) {
            Picasso.get()
                .load(filmeHorario.posterPath) // Assume que 'posterPath' é uma URL completa
                .into(holder.posterImageView)
        } else {
            // Opcionalmente, você pode definir uma imagem padrão se o pôster não estiver disponível
            holder.posterImageView.setImageResource(R.drawable.placeholder_image)
        }
    }

    override fun getItemCount(): Int {
        return filmesHorarios.size
    }
}
