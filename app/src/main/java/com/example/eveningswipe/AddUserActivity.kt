package com.example.eveningswipe

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
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
        searchedUser!!.visibility = View.INVISIBLE;
        searchFinished = findViewById<View>(R.id.searchFinished) as Button
        searchFinished!!.visibility = View.INVISIBLE;

        // Set cut corner background for API 23+
        var layout = findViewById(R.id.add_user_layout) as View
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
        }
    }

    /**
     * method to search for a user and check user input
     */
    private fun searchUser(){
        searchInput = searchUser!!.text.toString()
        if(searchInput.isNullOrBlank()){
            Toast.makeText(this, "Please enter an user name", Toast.LENGTH_SHORT)
                    .show()
        } else {
            searchedUser!!.visibility = View.VISIBLE;
            searchFinished!!.visibility = View.INVISIBLE;
            val token = HttpRequests.responseToken?.token
            if (token != null) {
                val response = HttpRequests.getAllUser(BASE_URL_FindUser, token, searchInput!!)
                if(!response!!){
                    Toast.makeText(this, "Wrong username or user does not exist", Toast.LENGTH_SHORT)
                            .show()
                    searchedUser!!.visibility = View.INVISIBLE;
                }else{
                    searchedUser!!.visibility = View.VISIBLE;
                    searchedUser!!.text = searchInput + " ADD"
                }
            }
        }
    }


    /**
     * method to add a user to a group
     */
    private fun addUserToGroup() {
        val token = HttpRequests.responseToken!!.token
        val groupId = GroupProfile.getValue()?.toInt()
        val userToAdd = searchInput

        if (userToAdd != null) {
            if (groupId != null) {
                val response = HttpRequests.postAddUserToGroup(BASE_URL_AddUser, token, groupId, userToAdd)
                if(!response!!){
                    Toast.makeText(this, "The user $userToAdd is already part of this group", Toast.LENGTH_SHORT)
                            .show()
                    searchedUser!!.visibility = View.INVISIBLE;
                } else {
                    searchedUser!!.visibility = View.INVISIBLE;
                    searchFinished!!.text = "User added successfully"
                    searchFinished!!.visibility = View.VISIBLE;
                }
            }
        }
    }
}