package com.example.eveningswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

class AddFilterActivity : AppCompatActivity() {
    private var min_year: TextInputLayout? = null
    private  var max_year: TextInputLayout? = null
    private  var runtime: TextInputLayout? = null
    private var createFilter: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_filter)

        min_year = findViewById<View>(R.id.year_begin_input) as TextInputLayout
        max_year = findViewById<View>(R.id.year_end_input) as TextInputLayout
        runtime = findViewById<View>(R.id.runtime_input) as TextInputLayout
        createFilter = findViewById<Button>(R.id.create_filter) as Button
        createFilter!!.setOnClickListener(View.OnClickListener { addFilter() })


        // Set cut corner background for API 23+
        val layout = findViewById(R.id.header_layout) as View
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }

    fun addFilter(){

    }
}