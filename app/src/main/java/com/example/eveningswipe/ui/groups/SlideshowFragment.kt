package com.example.eveningswipe.ui.groups

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.eveningswipe.AddGroup
import com.example.eveningswipe.GroupDataRecycle
import com.example.eveningswipe.GroupsAdapter
import com.example.eveningswipe.R
import com.example.eveningswipe.httpRequests.HttpRequests
import java.util.logging.Logger


class SlideshowFragment : Fragment() {
    private val BASE_URL_groupInfo = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/group/info"
    private lateinit var slideshowViewModel: SlideshowViewModel
    private var newGroup: Button? = null
    private var grReView: RecyclerView? = null
    val token = HttpRequests.responseToken

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

        grReView = root.findViewById(R.id.groupRecyclerView) as RecyclerView

        showGroups()

        newGroup = root.findViewById(R.id.newGroup) as Button
        newGroup!!.setOnClickListener(View.OnClickListener { createNewGroup() })

        // Set cut corner background for API 23+
        root.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)

        return root

    }

    /**
     * method to fill group recyclerview
     */
    private fun showGroups(){
        val groupListCounter = HttpRequests.responseUserInfo.groupId
        //remove duplicates
        val groupList = groupListCounter.distinct()
        val groupName: String = ""
        val memberNumber: Int = 0
        val groupId: Int = 0
        val groups = mutableListOf(GroupDataRecycle(groupName, memberNumber, groupId))
        groups.remove(GroupDataRecycle("", 0, 0))
        for (i in 0..groupList.size-1) {
            if (token != null) {
                val response = HttpRequests.getGroupInformation(BASE_URL_groupInfo, token, groupList[i])

                if (!response!!){
                    Logger.getLogger(SlideshowFragment::class.java.name).warning("Group information could not be retrieved")
                } else {
                    groups.add(GroupDataRecycle(HttpRequests.responseGroupInfo!!.name, HttpRequests.responseGroupInfo!!.member.size,
                        groupList.get(i)
                    ))
                }
            }
        }

        val adapter = GroupsAdapter(groups)
        grReView!!.adapter = adapter
    }

    /**
     * send user to AddGroupActivity after clicking on plus button
     */
    private fun createNewGroup() {
        val intent = Intent(context, AddGroup::class.java)
        startActivity(intent)
    }


}


