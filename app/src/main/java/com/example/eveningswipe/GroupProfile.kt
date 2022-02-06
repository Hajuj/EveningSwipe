package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.eveningswipe.GroupID.groupIDforUserAdding

class GroupProfile : AppCompatActivity() {

    private var groupName: TextView? = null
    private var idNumber: TextView? = null
    private var addUserToGroup: Button? = null
    private var addFilterToGroup: Button? = null
    private var addRandomFilter: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_profile)

        groupName = findViewById<View>(R.id.groupName) as TextView
        idNumber = findViewById<View>(R.id.idNumber) as TextView
        addUserToGroup = findViewById<View>(R.id.addUserToGroup) as Button
        addFilterToGroup = findViewById<View>(R.id.addFilterToGroup) as Button
        addRandomFilter = findViewById<View>(R.id.addRandomFilter) as Button
        addUserToGroup!!.setOnClickListener(View.OnClickListener { startAddUserActivityActivity() })
        addFilterToGroup!!.setOnClickListener(View.OnClickListener { startAddFilterActivity() })
        addRandomFilter!!.setOnClickListener(View.OnClickListener { startAddRandomFilterActivity() })

        val groupProfileName = intent.getStringExtra("groupName")
        val groupID = intent.getStringExtra("groupID")
        groupName!!.text = groupProfileName
        idNumber!!.text = groupID
        groupIDforUserAdding = (idNumber!!.text as String?).toString()

        // Set cut corner background for API 23+
        val layout = findViewById(R.id.group_profile_corner) as View
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
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

    /**
     * add filter for this group and start filter activity
     */
    private fun startAddFilterActivity() {
        val profileIntent = Intent(this@GroupProfile, AddFilterActivity::class.java)
        startActivity(profileIntent)
    }

    /**
     * add random filter for this group
     */
    private fun startAddRandomFilterActivity() {
        val profileIntent = Intent(this@GroupProfile, AddRandomFilterActivity::class.java)
        startActivity(profileIntent)
    }
}