package com.example.eveningswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.httpRequests.postRequests.FilterDto
import com.example.eveningswipe.ui.filmswipe.BASE_URL_ById
import com.google.android.material.textfield.TextInputLayout

val BASE_URL_CREATEFILTER = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/create"

class AddFilterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var minYear: TextInputLayout? = null
    private var maxYear: TextInputLayout? = null
    private var runtime: TextInputLayout? = null
    private var size: TextInputLayout? = null
    private var createFilter: Button? = null
    var genre: String? = null
    val token = HttpRequests.responseToken
    var genreList: MutableList<String> = mutableListOf("select genre")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_filter)

        minYear = findViewById<View>(R.id.year_begin_input) as TextInputLayout
        maxYear = findViewById<View>(R.id.year_end_input) as TextInputLayout
        runtime = findViewById<View>(R.id.runtime_input) as TextInputLayout
        size = findViewById<View>(R.id.size_input) as TextInputLayout
        createFilter = findViewById<Button>(R.id.create_filter) as Button
        createFilter!!.setOnClickListener(View.OnClickListener { addFilter() })


        //use spinner for selection
        genreList.addAll(mutableListOf("Action", "Adult", "Adventure", "Biography", "Comedy",
            "Crime", "Documentary", "Drama", "Family", "Fantasy", "Film-Noir",
            "Game-Show", "History", "Horror", "Music", "Musical", "Mystery", "Reality-TV",
            "Romance", "Short", "Sport", "Thriller", "War"))

        val spinner: Spinner = findViewById(R.id.groups_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            genreList
        )
        // Set layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.setAdapter(adapter)

        spinner.onItemSelectedListener = this

        // Set cut corner background for API 23+
        val layout = findViewById(R.id.header_layout) as View
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }

    fun addFilter(){
        val min_year = minYear?.editText?.text.toString().trim()
        val max_year = maxYear?.editText?.text.toString().trim()
        val runtime = runtime?.editText?.text.toString().trim()
        val size = size?.editText?.text.toString().trim()

        val filter: FilterDto? = null
        filter?.genre_1 = genre.toString()
    //    filter?.genre_2 =
    //    filter?.genre_3 =
        filter?.min_year = min_year.toInt()
        filter?.max_year = max_year.toInt()
    //    filter?.rating =
    //    filter?.votes =
        filter?.max_runtime = runtime.toInt()
        filter?.size = size.toInt()
        //TODO: grouppID required
    //    filter?.group_id =

        val response =
            filter?.let { HttpRequests.postCreateFilter(BASE_URL_CREATEFILTER, token!!, it) }
        if (!response!!){
            // error
        } else {
            Toast.makeText(
                this,
                "Filter is added to group.",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        if (genreList.get(pos) == genreList[0]) {
            //do nothing first element is just a hint to select group
        } else {
            genre = genreList.get(pos)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}