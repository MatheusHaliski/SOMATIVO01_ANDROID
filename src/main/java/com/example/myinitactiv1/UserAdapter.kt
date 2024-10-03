package com.example.myinitactiv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // ViewHolder que contém as referências aos views de cada item
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_user_name)
        val emailTextView: TextView = itemView.findViewById(R.id.tv_user_email)
        val birthdateTextView: TextView = itemView.findViewById(R.id.tv_user_birthdate)
        val passwordTextView: TextView = itemView.findViewById(R.id.tv_user_password)
    }

    // Infla o layout do item da lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    // Associa os dados ao ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.nameTextView.text = user.name
        holder.emailTextView.text = user.email
        holder.birthdateTextView.text = user.birthdate
        holder.passwordTextView.text = user.password


        // Ajusta as larguras para combinar com os cabeçalhos
        holder.nameTextView.layoutParams.width = 450
        holder.emailTextView.layoutParams.width = 450
        holder.birthdateTextView.layoutParams.width = 450
        holder.passwordTextView.layoutParams.width = 500
    }

    // Retorna a quantidade de itens
    override fun getItemCount(): Int {
        return userList.size
    }
}
