package com.example.eveningswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eveningswipe.httpRequests.HttpRequests

class AddUserActivity : AppCompatActivity() {

    private val BASE_URL_AddUser = "http://192.168.178.30:8080/api/group/add"
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

    /**
     * method to add user to a group
     */
    private fun addUserToGroup() {
        val url = BASE_URL_AddUser
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI5IiwiaWF0IjoxNjQxMDQ2MTg5LCJleHAiOjE2NDExMzI1ODl9.Rdu8nYi_844wJLbsay0QGE3a19sbWUBMNCBbzdQ4cN0"
        val name = "Test"
        val description = "Beschreibung der Gruppe"
        println("Hallo !!!!!")
        HttpRequests.postCreatedGroup2(url, token, name, description)
    }
}