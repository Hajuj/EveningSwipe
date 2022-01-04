package com.example.eveningswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.example.eveningswipe.httpRequests.CreateGroup
import com.example.eveningswipe.httpRequests.FilterByGroupId
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.ui.filmswipe.MovieById

const val BASE_URL = "http://192.168.178.21:8080/api/filter/byid/"
var createGroup = ArrayList<CreateGroup>()

class AddGroup : AppCompatActivity() {

    private var saveGroup: Button? = null
    private var name: EditText? = null
    private  var description: EditText? = null

    private var creatingGroup: CreateGroup = CreateGroup(1, name?.text.toString(), description?.text.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        name = findViewById<View>(R.id.email) as EditText
        description = findViewById<View>(R.id.password) as EditText
        saveGroup = findViewById<View>(R.id.signIn) as Button
        saveGroup!!.setOnClickListener(View.OnClickListener { saveGroupToDatabase() })
    }

    //TODO change value of id
    private val id: Int = 1

    /**
     * method to save a group in the database
     */
    private fun saveGroupToDatabase() {
        var url = BASE_URL + "5"

        createGroup = HttpRequests.postCreatedGroup(url)
    }
}
