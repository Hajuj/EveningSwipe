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

/**
 * swipe fragment
 * @param BASE_URL_ById url for http request
 * @param URL_GroupInfo url for htttp request
 * @param root layout view of the fragment
 * @param token current token of the user
 * @param groupIdList list with the group id of the user
 * @param groupNameList list of the group names
 */
class SwipeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        fun newInstance() = SwipeFragment()
    }

    val BASE_URL_ById = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/byid/"
    val URL_GroupInfo = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/group/info"
    private lateinit var root: View
    val token = HttpRequests.responseToken
    var groupIdList: List<Int>? = null
    var groupNameList: MutableList<String> = mutableListOf()

    /**
     * create content of fragment
     * fill the groupNameList for the spinner
     * create spinner
     */
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.swipe_fragment, container, false)

        // list with the ids  of all the groups where the user is member
        groupIdList = HttpRequests.responseUserInfo.groupId.distinct()

        //get the names of the groups an add them to another list
        groupNameList.add(getString(R.string.select_group))
        for (i in 0..groupIdList!!.size - 1) {
            val response =
                HttpRequests.getGroupInformation(URL_GroupInfo, token!!, groupIdList!![i])
            if (!response!!) {
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

    /**
     *  function what happens if a group is selected in the spinner
     *  @property pos the index of the selected item
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        if (groupNameList.get(pos) == groupNameList[0]) {
            //do nothing first element is just a hint to select group
        } else {
            if (token != null) {
                //check if group has a filter
                val response = HttpRequests.getFilterByGroupId(
                    BASE_URL_ById,
                    token,
                    groupIdList!!.get(pos - 1)
                )
                if (!response!!) {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.need_filter),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    SelectFilterActivity.setGroupId(groupIdList!!.get(pos - 1))
                    SelectFilterActivity.setGroupName(groupNameList.get(pos))
                    val intent = Intent(context, SelectFilterActivity::class.java)
                    context?.startActivity(intent)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
    }
}