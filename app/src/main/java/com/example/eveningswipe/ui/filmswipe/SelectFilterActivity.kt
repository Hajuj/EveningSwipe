package com.example.eveningswipe.ui.filmswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.eveningswipe.R
import com.example.eveningswipe.httpRequests.GetFilterByGroupId2
import com.example.eveningswipe.httpRequests.HttpRequests

/**
 * variables that can be accessed from other activities
 * @param groupId id of the selected group
 * @param groupName name of the selected group
 * @param filterId id of th selected filter
 * @param filterIndex index of the selected filter in spinner list
 */
var groupId: Int? = null
var groupName: String? = null
var filterId: Int? = null
var filterIndex: Int? = null

/**
 * class to select the filter before swipe
 * @param BASE_URL_GetFilterById url for the http request
 * @param token the token of the current user, for identification
 * @param filterList a list with all filter of the group
 * @param filterNameList a list with just the names of all filter
 */
class SelectFilterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var BASE_URL_GetFilterById = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/byid"
    val token = HttpRequests.responseToken
    var filterList: Array<GetFilterByGroupId2>? = null
    val filterNameList: MutableList<String> = mutableListOf()
    val filterIdList = mutableListOf(0)

    /**
     * create context of activity
     * fill lists and create spinner for the selection
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_filter)

        // create a list with all filter (+ details of filter) of the group
        var response: Boolean? = null
        if (token != null) {
            response =
                groupId?.let { HttpRequests.getFilterByGroupId(BASE_URL_GetFilterById, token, it) }
        }
        if (!response!!) {
            //wait
        } else {
            filterList = HttpRequests.responseFilterByGroupId
        }

        // add all filter names in a list
        filterNameList.add(getString(R.string.select_filter))
        if (filterList != null) {
            for (i in 0..filterList!!.size - 1) {
                filterNameList.add(filterList!![i].name)
                filterIdList.add(filterList!![i].id)
            }
        }

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

        // Set cut corner background
        val layoutBg = findViewById(R.id.choose_filter) as View
        layoutBg.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }

    /**
     * functions that are called in the SwipeFragment, to transfer the variables
     * @property getGroupId is the Id from the selected group
     * @property getGroupName is the name from the selected group
     */
    companion object {
        fun setGroupId(getGroupId: Int) {
            groupId = getGroupId
        }

        fun setGroupName(getGroupName: String) {
            groupName = getGroupName
        }
    }

    /**
     *  function what happens if a filter is selected in the spinner
     *  @property position the index of the selected item
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (filterNameList.get(position) == filterNameList[0]) {
            //do nothing first element is just a hint to select group
        } else {
            //SwipeMoviesActivity.setGroupId(filterNameList!!.get(position-1))
            filterId = filterIdList.get(position)
            filterIndex = position - 1
            val intent = Intent(this, SwipeMoviesActivity::class.java)
            this.startActivity(intent)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
    }
}