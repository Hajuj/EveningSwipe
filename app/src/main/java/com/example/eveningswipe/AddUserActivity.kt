package com.example.eveningswipe

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eveningswipe.httpRequests.HttpRequests

class AddUserActivity : AppCompatActivity() {

    private var addUser: TextView? = null
    private  var searchUser: EditText? = null
    private var addUserReView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        addUser = findViewById<View>(R.id.text_addUser) as TextView
        searchUser = findViewById<View>(R.id.searchUser) as EditText
        addUserReView = findViewById<RecyclerView>(R.id.addUserRecyclerView)

        // Set cut corner background for API 23+
        var layout = findViewById(R.id.add_user_layout) as View
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
        }

        //TODO: for testing, need to be replaced with groups of database
        var userList= mutableListOf(
            AddUserDataRecycle(HttpRequests.responseUserInfo.userName),
            AddUserDataRecycle("alex"),
            AddUserDataRecycle("tom")
        )

        val adapter = AddUserAdapter(userList)
        addUserReView!!.adapter = adapter
    }
}