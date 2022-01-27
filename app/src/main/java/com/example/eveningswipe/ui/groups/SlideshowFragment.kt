package com.example.eveningswipe.ui.groups

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
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
import com.example.eveningswipe.httpRequests.HttpRequests


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
        /*val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        grReView = root.findViewById(R.id.groupRecyclerView) as RecyclerView

        showGroups()

        newGroup = root.findViewById(R.id.newGroup) as Button
        newGroup!!.setOnClickListener(View.OnClickListener { createNewGroup() })

        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            root.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)
        }
        return root

    }

    /**
     * method to fill group recyclerview
     */
    private fun showGroups(){
        val groupListCounter = HttpRequests.responseUserInfo.groupId
        //remove duplicates
        val groupList = groupListCounter.distinct()
        var groupName: String = ""
        val memberNumber: Int = 0
        val groupId: Int = 0
        val groups = mutableListOf(GroupDataRecycle(groupName, memberNumber, groupId))
        for (i in 0..groupList.size-1) {
            if (token != null) {
                HttpRequests.getGroupInformation(BASE_URL_groupInfo, token, groupList[i])

                while(HttpRequests.responseGroupInfo == null) {
                    // waiting for initialization
                }

                //kann entfernt werden, sobald es keine doppelten Gruppennamen mehr gibt
                if (HttpRequests.responseGroupInfo!!.name == "androidx.appcompat.widget.AppCompatEditText{6bf6ff7 VFED..CL. ........ 15,90-1065,274 #7f0800d1 app:id/fullName}"){
                    // doppelter Gruppenname
                }else{
                    while(HttpRequests.responseGroupInfo!!.name == groupName) {
                        // waiting for initialization
                    }
                    groupName = HttpRequests.responseGroupInfo!!.name
                    groups.add(GroupDataRecycle(HttpRequests.responseGroupInfo!!.name, HttpRequests.responseGroupInfo!!.member.size,
                        groupList[i]
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


