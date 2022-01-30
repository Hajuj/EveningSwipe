package com.example.eveningswipe.ui.filmswipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.eveningswipe.R
import android.content.Intent
import android.widget.*
import com.example.eveningswipe.httpRequests.HttpRequests
import android.widget.Toast


const val BASE_URL_ById = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/byid/"
const val BASE_URL_MovieDetails = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/details/"
const val BASE_URL_RateMovie = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/rate/"
const val URL_GroupInfo = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/group/info"

class SwipeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        fun newInstance() = SwipeFragment()
    }

    private lateinit var root: View
    val token = HttpRequests.responseToken
    var groupIdList: List<Int>? = null
    var groupNameList: MutableList<String> = mutableListOf("select group")

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.swipe_fragment, container, false)

        groupIdList = HttpRequests.responseUserInfo.groupId.distinct()

        for (i in 0..groupIdList!!.size - 1) {
            val response = HttpRequests.getGroupInformation(URL_GroupInfo, token!!, groupIdList!![i])
            if (!response!!){
                // error
            } else {
                groupNameList.add(HttpRequests.responseGroupInfo!!.name)
            }
        }

        //use spinner for selection
        val spinner: Spinner = root.findViewById(R.id.groups_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            groupNameList
        )
        // Set layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.setAdapter(adapter)

        spinner.onItemSelectedListener = this
        // Set cut corner background
        val layout = root.findViewById<View>(R.id.choose_group)
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)

        return root
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        if (groupNameList.get(pos) == groupNameList[0]) {
            //do nothing first element is just a hint to select group
        } else {
            //check if group has a filter
            //get movie list
            if (token != null) {
                val response = HttpRequests.getFilterByGroupId(BASE_URL_ById, token, groupIdList!!.get(pos - 1))
                if (!response!! ) {
                    Toast.makeText(
                        context,
                        "This group needs a filter. Please go back to the group settings and add a filter.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    //do nothing
                } else {
                    SelectFilterActivity.setGroupId(groupIdList!!.get(pos-1))
                    SelectFilterActivity.setGroupName(groupNameList.get(pos))
                    val intent = Intent(context, SelectFilterActivity::class.java)
                    context?.startActivity(intent)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}