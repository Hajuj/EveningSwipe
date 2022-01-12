package com.example.eveningswipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eveningswipe.databinding.ItemAdduserrecyclerBinding
import java.util.logging.Logger

class AddUserAdapter(
    var user: List<AddUserDataRecycle>
) : RecyclerView.Adapter<AddUserAdapter.AddUserViewHolder>() {

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
                Logger.getLogger(AddUserAdapter::class.java.name).warning("Hello AddUser !!!")
            }
        }

    }

    override fun getItemCount(): Int {
        return user.size
    }
}