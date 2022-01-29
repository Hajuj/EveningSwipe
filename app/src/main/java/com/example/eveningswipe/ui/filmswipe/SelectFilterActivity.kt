package com.example.eveningswipe.ui.filmswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.eveningswipe.GroupID
import com.example.eveningswipe.R
import com.example.eveningswipe.httpRequests.GetFilterByGroupId2
import com.example.eveningswipe.httpRequests.HttpRequests

var BASE_URL_GetFilterById = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/byid"
var groupId: Int? = null
class SelectFilterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_filter)

        println("groupId: " + groupId)
        val token = HttpRequests.responseToken
        var filterList: Array<GetFilterByGroupId2>? = null
        var filterNameList = mutableListOf<Int>()
        var response: Boolean? = null
        if(token != null){
            response = groupId?.let { HttpRequests.getFilterByGroupId(BASE_URL_ById, token, it) }
        }

        if(!response!!){
            //wait
        }else{
            filterList = HttpRequests.responseFilterByGroupId
        }

        if (filterList != null) {
            for(i in 0..filterList.size){
                filterNameList.add(filterList[i].id)
            }
        }
        println("filter list: " + filterNameList)
        //first you need to select the group
        //use spinner for selection
        val spinner: Spinner = findViewById(R.id.groups_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            filterNameList
        )
        // Set layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.setAdapter(adapter)

        spinner.onItemSelectedListener = this
    }

    companion object{
        fun setGroupId(getGroupId: Int){
            groupId = getGroupId
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}