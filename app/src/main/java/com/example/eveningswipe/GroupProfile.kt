package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class GroupProfile : AppCompatActivity() {

    private var groupName: TextView? = null
    private var addUserToGroup: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_profile)

        groupName = findViewById<View>(R.id.groupName) as TextView
        addUserToGroup = findViewById<View>(R.id.addUserToGroup) as Button
        addUserToGroup!!.setOnClickListener(View.OnClickListener { startAddUserActivityActivity() })

        val groupProfileName = intent.getStringExtra("groupName")
        groupName!!.text = groupProfileName

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