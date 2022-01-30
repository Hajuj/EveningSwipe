package com.example.eveningswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.ui.filmswipe.*


val BASE_URL_MovieDetails = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/details/"
val URL_FILTER_RATING = "http://msp-WS2122-6.mobile.ifi.lmu.de:80/api/filter/rating"

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

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

        val groupNameView = findViewById<View>(R.id.ranking_group_name) as TextView
        val textGroupName = groupName + "'s ranking:"
        groupNameView.text = textGroupName

        var response: Boolean? = null
        if(token != null){
            response = HttpRequests.getFilterRating(URL_FILTER_RATING, token, filterId!!)
        }

        val movieIdList: MutableList<String> = mutableListOf()
        val movieNameList: MutableList<String> = mutableListOf()
        if (!response!!) {
            //wait
        } else {
            for (i in 0..swipeCount!!-1) {
                HttpRequests.responseFilterRating?.get(i)?.movie_id?.let { movieIdList.add(i, it) }
            }
        }

        // get movie name list
        var response2: Boolean? = null
        var temp: Int = 0
        var i: Int = 0
        while (movieNameList.size < swipeCount!!) {
            if (token != null) {
                response2 = HttpRequests.getMovieDetails(BASE_URL_MovieDetails, token, movieIdList[temp])
            }
            if(!response2!!) {
                //wait
                temp +=1
            }else {
                movieNameList.add(i, HttpRequests.responseMovieDetails?.original_title.toString())
                temp +=1
                i +=1
            }
        }

        if(movieNameList.size > 0){
            movieView1.text = "1. " + movieNameList[0]
        }
        if(movieNameList.size > 1){
            movieView2.text = "2. " + movieNameList[1]
        }
        if(movieNameList.size > 2){
            movieView3.text = "3. " + movieNameList[2]
        }
        if(movieNameList.size > 3){
            movieView4.text = "4. " + movieNameList[3]
        }
        if(movieNameList.size > 4){
            movieView5.text = "5. " + movieNameList[4]
        }
        if(movieNameList.size > 5){
            movieView6.text = "6. " + movieNameList[5]
        }
        if(movieNameList.size > 6){
            movieView7.text = "7. " + movieNameList[6]
        }
        if(movieNameList.size > 7){
            movieView8.text = "8. " + movieNameList[7]
        }
        if(movieNameList.size > 8){
            movieView9.text = "9. " + movieNameList[8]
        }
        if(movieNameList.size > 9){
            movieView10.text = "10. " + movieNameList[9]
        }

        // Set cut corner background
        val layout = findViewById<View>(R.id.ranking_layout)
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }
}