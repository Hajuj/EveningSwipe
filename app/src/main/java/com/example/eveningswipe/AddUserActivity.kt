package com.example.eveningswipe

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eveningswipe.httpRequests.HttpRequests

class AddUserActivity : AppCompatActivity() {
    private val BASE_URL_FindUser = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/users/find"
    private val BASE_URL_AddUser = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/group/add"
    private var addUser: TextView? = null
    private  var searchUser: EditText? = null
    private var searchButton: ImageButton? = null
    private var searchedUser: Button? = null
    private var searchFinished: Button? = null
    private var searchInput : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        addUser = findViewById<View>(R.id.text_addUser) as TextView
        searchUser = findViewById<View>(R.id.searchUser) as EditText
        searchButton = findViewById<View>(R.id.searchButton) as ImageButton
        searchButton!!.setOnClickListener(View.OnClickListener { searchUser() })
        searchedUser = findViewById<View>(R.id.searchedUser) as Button
        searchedUser!!.setOnClickListener(View.OnClickListener { addUserToGroup() })
        searchedUser!!.setVisibility(View.INVISIBLE);
        searchFinished = findViewById<View>(R.id.searchFinished) as Button
        searchFinished!!.setVisibility(View.INVISIBLE);

        // Set cut corner background for API 23+
        var layout = findViewById(R.id.add_user_layout) as View
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
        }
    }

    /**
     * method to search for a user
     */
    private fun searchUser(){
        searchedUser!!.setVisibility(View.VISIBLE);
        searchInput = searchUser!!.text.toString()
        searchedUser!!.text = searchInput + " ADD"
        println("search string "+ searchInput)
        val token = HttpRequests.responseToken?.token
        if (token != null) {
            HttpRequests.getAllUser(BASE_URL_FindUser, token, searchInput!!)
        }
    }

    /**
     * method to add a user to a group
     */
    private fun addUserToGroup(){
        val token = HttpRequests.responseToken!!.token
        val getID = GroupProfile()
        val groupId = GroupProfile.getValue()?.toInt()
        println("groupIDforUserAdding "+ GroupProfile.getValue()+ " getID " + getID)
        val userToAdd = searchInput
        if (userToAdd != null) {
            if (groupId != null) {
                HttpRequests.postAddUserToGroup(BASE_URL_AddUser, token, groupId, userToAdd)
                searchedUser!!.visibility = View.INVISIBLE;
                searchFinished!!.text = "User added successfully"
                searchFinished!!.visibility = View.VISIBLE;
            }
        }

    }

}