package com.example.eveningswipe

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

        //TODO: for testing, need to be replaced with groups of database
        var userlist= mutableListOf(
            AddUserDataRecycle("anna"),
            AddUserDataRecycle("alex"),
            AddUserDataRecycle("tom")
        )

        val adapter = AddUserAdapter(userlist)
        addUserReView!!.adapter = adapter
    }
}