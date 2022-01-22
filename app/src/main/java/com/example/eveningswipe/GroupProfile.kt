package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.eveningswipe.GroupID.groupIDforUserAdding
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.ui.groups.SlideshowFragment
import com.google.android.material.textfield.TextInputLayout

class GroupProfile : AppCompatActivity() {

    private var groupName: TextView? = null
    private var idNumber: TextView? = null
    private var addUserToGroup: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_profile)

        groupName = findViewById<View>(R.id.groupName) as TextView
        idNumber = findViewById<View>(R.id.idNumber) as TextView
        addUserToGroup = findViewById<View>(R.id.addUserToGroup) as Button
        addUserToGroup!!.setOnClickListener(View.OnClickListener { startAddUserActivityActivity() })

        val groupProfileName = intent.getStringExtra("groupName")
        val groupID = intent.getStringExtra("groupID")
        groupName!!.text = groupProfileName
        idNumber!!.text = groupID
        groupIDforUserAdding = (idNumber!!.text as String?).toString()


    }

    companion object{
        fun  getValue(): String? {
            return groupIDforUserAdding
        }
    }


    /**
     * send user to RegisterActivity if user has no account
     */
    private fun startAddUserActivityActivity() {
        val profileIntent = Intent(this@GroupProfile, AddUserActivity::class.java)
        startActivity(profileIntent)
        finish()
    }
}