package com.example.eveningswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.ui.filmswipe.SwipeFragment


val BASE_URL_MovieDetails = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/details/"
val URL_FILTER_RATING = "http://msp-WS2122-6.mobile.ifi.lmu.de:80/api/filter/rating"

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

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

        //TODO: wrong group name
        val groupNameView = findViewById<View>(R.id.ranking_group_name) as TextView
        val groupName = HttpRequests.responseGroupInfo!!.name
        val textGroupName = groupName + "'s result:"
        groupNameView.text = textGroupName

        val token = HttpRequests.responseToken
        val filterId = HttpRequests.responseFilterByGroupId!!.id
        var response: Boolean? = null
        if (token != null) {
            response = HttpRequests.getFilterRating(URL_FILTER_RATING, token, filterId)
        }

        val movieIdList: MutableList<String> = mutableListOf()
        val movieNameList: MutableList<String> = mutableListOf()
        if (!response!!) {
            //wait
        } else {
            for (i in 0..20) {
                HttpRequests.responseFilterRating?.get(i)?.movie_id?.let { movieIdList.add(i, it) }
            }
        }

        // get movie name list
        var response2: Boolean? = null
        var temp: Int = 0
        var i: Int = 0
        while (movieNameList.size < 10) {
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

        movieView1.text = "1. " + movieNameList[0]
        movieView2.text = "2. " + movieNameList[1]
        movieView3.text = "3. " + movieNameList[2]
        movieView4.text = "4. " + movieNameList[3]
        movieView5.text = "5. " + movieNameList[4]
        movieView6.text = "6. " + movieNameList[5]
        movieView7.text = "7. " + movieNameList[6]
        movieView8.text = "8. " + movieNameList[7]
        movieView9.text = "9. " + movieNameList[8]
        movieView10.text = "10. " + movieNameList[9]

        // Set cut corner background
        val layout = findViewById<View>(R.id.ranking_layout)
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }
}