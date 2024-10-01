package com.example.myinitactiv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val filmesHorarios: List<FilmeHorario>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeFilme: TextView = itemView.findViewById(R.id.nome)
        val horario: TextView = itemView.findViewById(R.id.horario)
        val idioma: TextView = itemView.findViewById(R.id.idioma)
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
    }

    override fun getItemCount(): Int {
        return filmesHorarios.size
    }
}
