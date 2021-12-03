package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class FinishedSwipeActivity : AppCompatActivity() {

    private var mostSwiped: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_swipe)

        mostSwiped = findViewById<View>(R.id.signIn) as Button
        mostSwiped!!.setOnClickListener(View.OnClickListener { showMostSwiped() })
    }

    /**
     * send user to ...
     */
    //TODO: RegisterActivity::class.java ersetzen mit richtiger activity !!
    private fun showMostSwiped() {
        val profileIntent = Intent(this@FinishedSwipeActivity, RegisterActivity::class.java)
        startActivity(profileIntent)
        finish()
    }
}