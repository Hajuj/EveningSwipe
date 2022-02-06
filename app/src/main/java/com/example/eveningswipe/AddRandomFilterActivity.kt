package com.example.eveningswipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.httpRequests.postRequests.FilterDto
import com.google.android.material.textfield.TextInputLayout

class AddRandomFilterActivity : AppCompatActivity() {
    val token = HttpRequests.responseToken
    private var size: TextInputLayout? = null
    private var nameFilter: TextInputLayout? = null
    private var createFilter: Button? = null
    val BASE_URL_CreateFilter = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/create/random"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_random_filter)

        size = findViewById<View>(R.id.size_input_random) as TextInputLayout
        nameFilter = findViewById<View>(R.id.name_random_input) as TextInputLayout
        createFilter = findViewById(R.id.create_filter_random) as Button
        createFilter!!.setOnClickListener(View.OnClickListener { addFilter() })

        // Set cut corner background for API 23+
        val layout = findViewById<View>(R.id.random_layout)
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }

    fun addFilter(){
        if ( size?.editText?.text!!.isEmpty() || nameFilter?.editText?.text!!.isEmpty()
        ) {
            Toast.makeText(
                this,
                resources.getString(R.string.fill_in_field),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val genre = getString(R.string.genre_action)
            val minYearInput = getString(R.string._1993).toInt()
            val maxYearInput = getString(R.string._2022).toInt()
            val runtimeInput = getString(R.string._50).toInt()
            val sizeInput = size?.editText?.text.toString().trim().toInt()
            val ratingInput = getString(R.string._8).toInt()
            val votesInput = getString(R.string._500).toInt()
            val nameInput = nameFilter?.editText?.text.toString().trim()

            val filter = FilterDto(
                nameInput, genre, "", "",
                minYearInput, maxYearInput, ratingInput, votesInput,
                runtimeInput, sizeInput, GroupProfile.getValue()?.toInt()!!
            )

            val response =
                filter.let { HttpRequests.postCreateFilter(BASE_URL_CreateFilter, token!!, it) }
            if (!response!!) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.filter_not_added),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.filter_added),
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }
    }
}