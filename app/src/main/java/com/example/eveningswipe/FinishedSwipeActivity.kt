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

        mostSwiped = findViewById<View>(R.id.mostSwiped) as Button
        mostSwiped!!.setOnClickListener(View.OnClickListener { showMostSwiped() })

        // Set cut corner background
        val layout = findViewById<View>(R.id.finished_swipe_layout)
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
        // swipe frag: chooseLayout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)

    }

    /**
     * send user to ...
     */
    //TODO: RegisterActivity::class.java ersetzen mit richtiger activity !!
    private fun showMostSwiped() {
        val profileIntent = Intent(this@FinishedSwipeActivity, RankingActivity::class.java)
        startActivity(profileIntent)
        finish()
    }
}