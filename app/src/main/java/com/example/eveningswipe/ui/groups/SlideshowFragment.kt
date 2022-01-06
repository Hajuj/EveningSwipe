package com.example.eveningswipe.ui.groups

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.eveningswipe.AddGroup
import com.example.eveningswipe.GroupDataRecycle
import com.example.eveningswipe.GroupsAdapter
import com.example.eveningswipe.R


class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var newGroup: Button? = null
    private var grReView: RecyclerView? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        grReView = root.findViewById(R.id.groupRecyclerView) as RecyclerView

        //TODO: for testing, need to be replaced with groups of database
        var grouplist= mutableListOf(
            GroupDataRecycle("test1", 3),
            GroupDataRecycle("test2", 1),
            GroupDataRecycle("test3", 5)
        )

        val adapter = GroupsAdapter(grouplist)
        grReView!!.adapter = adapter

        newGroup = root.findViewById(R.id.newGroup) as Button
        newGroup!!.setOnClickListener(View.OnClickListener { createNewGroup() })
        return root

    }

    /**
     * send user to AddGroupActivity after clicking on plus button
     */
    private fun createNewGroup() {
        val intent = Intent(context, AddGroup::class.java)
        startActivity(intent)
    }


}