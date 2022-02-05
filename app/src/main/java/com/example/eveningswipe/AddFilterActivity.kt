package com.example.eveningswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.httpRequests.postRequests.FilterDto
import com.google.android.material.textfield.TextInputLayout

/**
 * activity for creating a new filter for a group
 * @param minYear - votes declare variables for the input layout
 * @param genre selected genre in spinner
 * @param token current user identification
 * @param genreList list for spinner
 * @param BASE_URL_CreateFilter url for http request
 */
class AddFilterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var minYear: TextInputLayout? = null
    private var maxYear: TextInputLayout? = null
    private var runtime: TextInputLayout? = null
    private var size: TextInputLayout? = null
    private var createFilter: Button? = null
    private var rating: TextInputLayout? = null
    private var votes: TextInputLayout? = null
    private var nameFilter: TextInputLayout? = null
    var genre: String? = null
    val token = HttpRequests.responseToken
    var genreList: Array<String>? = null
    val BASE_URL_CreateFilter = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/create"

    /**
     * create context of activity
     * initialize views, create spinner
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_filter)

        minYear = findViewById<View>(R.id.year_begin_input) as TextInputLayout
        maxYear = findViewById<View>(R.id.year_end_input) as TextInputLayout
        runtime = findViewById<View>(R.id.runtime_input) as TextInputLayout
        size = findViewById<View>(R.id.size_input) as TextInputLayout
        rating = findViewById<View>(R.id.rating_input) as TextInputLayout
        votes = findViewById<View>(R.id.votes_input) as TextInputLayout
        nameFilter = findViewById<View>(R.id.filter_name_input) as TextInputLayout
        createFilter = findViewById(R.id.create_filter) as Button
        createFilter!!.setOnClickListener(View.OnClickListener { addFilter() })

        //use spinner for selection
        genreList = resources.getStringArray(R.array.genre_array)
        val spinner: Spinner = findViewById(R.id.genre_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            genreList!!
        )
        // Set layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.setAdapter(adapter)
        spinner.onItemSelectedListener = this

        // Set cut corner background for API 23+
        val layout = findViewById<View>(R.id.add_filter_layout)
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }

    /**
     * function called on button click to create new filter
     * checks if all input fields are filled out
     * send new filter properties to endpoint
     */
    fun addFilter() {
        if (genre == null || minYear?.editText?.text!!.isEmpty() || maxYear?.editText?.text!!.isEmpty() ||
            runtime?.editText?.text!!.isEmpty() || size?.editText?.text!!.isEmpty() ||
            rating?.editText?.text!!.isEmpty() || votes?.editText?.text!!.isEmpty() || nameFilter?.editText?.text!!.isEmpty()
        ) {
            Toast.makeText(
                this,
                resources.getString(R.string.fill_in_field),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val minYearInput = minYear?.editText?.text.toString().trim().toInt()
            val maxYearInput = maxYear?.editText?.text.toString().trim().toInt()
            val runtimeInput = runtime?.editText?.text.toString().trim().toInt()
            val sizeInput = size?.editText?.text.toString().trim().toInt()
            val ratingInput = rating?.editText?.text.toString().trim().toInt()
            val votesInput = votes?.editText?.text.toString().trim().toInt()
            val nameInput = nameFilter?.editText?.text.toString().trim()

            val filter = FilterDto(
                nameInput, genre.toString(), "", "",
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
            }
        }

    }

    /**
     *  function what happens if a genre is selected in the spinner
     *  @property pos the index of the selected item
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        if (genreList != null){
            if (genreList!!.get(pos) == genreList!![0]) {
                //do nothing first element is just a hint to select group
            } else {
                genre = genreList!!.get(pos)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //do nothing, something should be selected
    }
}