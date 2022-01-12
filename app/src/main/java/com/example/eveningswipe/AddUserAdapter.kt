package com.example.eveningswipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eveningswipe.databinding.ItemAdduserrecyclerBinding
import com.example.eveningswipe.httpRequests.HttpRequests
import java.util.logging.Logger

class AddUserAdapter(
    var user: List<AddUserDataRecycle>
) : RecyclerView.Adapter<AddUserAdapter.AddUserViewHolder>() {
    private val BASE_URL_AddUser = "http://192.168.178.30:8080/api/group/add"

    inner class AddUserViewHolder(val binding: ItemAdduserrecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddUserViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAdduserrecyclerBinding.inflate(layoutInflater, parent, false)
        return AddUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddUserViewHolder, position: Int) {

        holder.binding.apply {
            userName.text = user[position].name.toString()
            userName.setOnClickListener{
                //TODO: send to another activity / save chosen class etc
                addUserToGroup()
            }
        }

    }

    override fun getItemCount(): Int {
        return user.size
    }

    /**
     * method to add user to a group
     */
    private fun addUserToGroup() {
        val url = BASE_URL_AddUser
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI5IiwiaWF0IjoxNjQxMDQ2MTg5LCJleHAiOjE2NDExMzI1ODl9.Rdu8nYi_844wJLbsay0QGE3a19sbWUBMNCBbzdQ4cN0"
        val groupId = 2
        val userToAdd = "Anna"
        println("Hallo addUserToGroup !!!!!")
        HttpRequests.postAddUserToGroup(url, token, groupId, userToAdd)
    }
}