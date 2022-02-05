package com.example.eveningswipe

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.ui.filmswipe.*

/**
 * variables that can be accessed from other activities
 * @param movie top three movies
 */
var movie1: String? = null
var movie2: String? = null
var movie3: String? = null

/**
 * activity that handles the ranking
 */
class RankingActivity : AppCompatActivity() {
    val BASE_URL_MovieDetails = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/details/"
    val BASE_URL_FilterRating = "http://msp-WS2122-6.mobile.ifi.lmu.de:80/api/filter/rating"
    val BASE_URL_MovieInfo = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/info/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val btn = findViewById<View>(R.id.refresh_ranking) as Button
        btn.setOnClickListener(View.OnClickListener { showRanking() })
        val backBtn = findViewById<View>(R.id.back_btn) as Button
        backBtn.setOnClickListener(View.OnClickListener { back() })
        showRanking()

        // Set cut corner background
        val layout = findViewById<View>(R.id.ranking_layout)
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }

    /**
     * function to show the ranking
     * get the ranking from server and add it to view
     * update the widget
     */
    @SuppressLint("SetTextI18n")
    fun showRanking() {
        val token = HttpRequests.responseToken

        val movieView1 = findViewById<View>(R.id.movie1) as TextView
        val movieView2 = findViewById<View>(R.id.movie2) as TextView
        val movieView3 = findViewById<View>(R.id.movie3) as TextView
        val movieView4 = findViewById<View>(R.id.movie4) as TextView
        val movieView5 = findViewById<View>(R.id.movie5) as TextView
        val movieView6 = findViewById<View>(R.id.movie6) as TextView
        val movieView7 = findViewById<View>(R.id.movie7) as TextView
        val movieView8 = findViewById<View>(R.id.movie8) as TextView
        val movieView9 = findViewById<View>(R.id.movie9) as TextView
        val movieView10 = findViewById<View>(R.id.movie10) as TextView

        // set group name
        val groupNameView = findViewById<View>(R.id.ranking_group_name) as TextView
        val textGroupName = resources.getString(R.string.group_ranking, groupName)
        groupNameView.text = textGroupName

        var response: Boolean? = null
        if (token != null) {
            response = HttpRequests.getFilterRating(BASE_URL_FilterRating, token, filterId!!)
        }

        // get the ids of the movies in ranking order
        val movieIdList: MutableList<String> = mutableListOf()
        val movieVotingList: MutableList<String> = mutableListOf()
        if (!response!!) {
            // wait for response
        } else {
            for (i in 0..swipeCount!! - 1) {
                HttpRequests.responseFilterRating?.get(i)?.movie_id?.let { movieIdList.add(i, it) }
                HttpRequests.responseFilterRating?.get(i)?.rating?.let { movieVotingList.add(i, it) }
            }
        }
        // get the original movie names
        var response2: Boolean? = null
        var response3: Boolean? = null
        var temp: Int = 0
        var indexVoting: Int = 0
        var index: Int = 0
        val movieNameList: MutableList<String> = mutableListOf()
        while (movieNameList.size < swipeCount!! && movieIdList.size > temp) {
            if (token != null) {
                response2 =
                    HttpRequests.getMovieDetails(BASE_URL_MovieDetails, token, movieIdList[temp])
            }
            if (!response2!!) {
                if (token != null) {
                    response3 =
                        HttpRequests.getOtherMovieDetails(BASE_URL_MovieInfo, token, movieIdList[temp])
                }
                if(!response3!!){
                    movieVotingList.removeAt(temp - indexVoting)
                    indexVoting += 1
                    temp += 1
                } else{
                    movieNameList.add(index, HttpRequests.responseOtherMovieDetails?.original_title.toString())
                    temp += 1
                    index += 1
                }
            } else {
                movieNameList.add(index, HttpRequests.responseMovieDetails?.original_title.toString())
                temp += 1
                index += 1
            }
        }
        // add movie name to view and initialize the top three
        val textList = listOf<TextView>(movieView1, movieView2, movieView3, movieView4, movieView5, movieView6,
            movieView7, movieView8, movieView9, movieView10)

        for (i in 0..9){
            if (movieNameList.size > i) {
                val num = i+1
                textList[i].text = getString(R.string.ranking, num.toString(), movieVotingList[i], movieNameList[i])
            }
        }

        if(movieNameList.size >0){
            movie1 = movieNameList[0]
        }
        if (movieNameList.size > 1){
            movie2 = movieNameList[1]
        }
        if(movieNameList.size > 2){
            movie3 = movieNameList[2]
        }

        // update the text in the widget
        if(widgetIds != null){
            for (i in 0..widgetIds!!.size - 1) {
                updateAppWidget(this, AppWidgetManager.getInstance(this), widgetIds!![i])
            }
        }
    }

    private fun back() {
        val profileIntent = Intent(this@RankingActivity, NavigationDrawer::class.java)
        startActivity(profileIntent)
        finish()
    }
}