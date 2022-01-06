package com.example.eveningswipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eveningswipe.databinding.ItemGrouprecyclerBinding
import java.util.logging.Logger

class GroupsAdapter(
        var groups: List<GroupDataRecycle>
) : RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(val binding: ItemGrouprecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGrouprecyclerBinding.inflate(layoutInflater, parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {

        holder.binding.apply {
            groupName.text = groups[position].name
            groupName.setOnClickListener{
                //TODO: send to another activity / save chosen class etc
                Logger.getLogger(GroupsAdapter::class.java.name).warning("Hello..")
            }
            memberNumber.text = groups[position].memberNumber.toString()
        }

    }

    override fun getItemCount(): Int {
        return groups.size
    }
}