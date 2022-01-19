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
    private val BASE_URL_AddUser = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/group/add"

    inner class AddUserViewHolder(val binding: ItemAdduserrecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddUserViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAdduserrecyclerBinding.inflate(layoutInflater, parent, false)
        return AddUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddUserViewHolder, position: Int) {

        /**
         * method to add user to a group
         */
        holder.binding.apply {
            userName.text = user[position].name
            userName.setOnClickListener{
                //TODO: send to another activity / save chosen class etc
                val token = HttpRequests.responseToken.token
                val groupId = 431
                val userToAdd = user[position].name
                println("Hallo addUserToGroup !!!!! " + userToAdd)
                HttpRequests.postAddUserToGroup(BASE_URL_AddUser, token, groupId, userToAdd)
            }
        }

    }

    override fun getItemCount(): Int {
        return user.size
    }
}