package com.example.eveningswipe.ui.filmswipe

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eveningswipe.FinishedSwipeActivity
import com.example.eveningswipe.R
import com.example.eveningswipe.httpRequests.HttpRequests
import com.squareup.picasso.Picasso

var swipeCount: Int? = null

class SwipeMoviesActivity : AppCompatActivity() {

    private var temp = 0
    var i: Int = 0
    val token = HttpRequests.responseToken
    var hintAccept: Boolean = false
    var movieId: String? = null
    var movieList: ArrayList<String>? = null
    private var pgsBar: ProgressBar? = null
    private var layout: View? = null
    private var imgURL: String? = null
    private lateinit var swipeViewModel: SwipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_movies)
        swipeViewModel = ViewModelProvider(this).get(SwipeViewModel::class.java)

        movieList = filterIndex?.let { HttpRequests.responseFilterByGroupId?.get(it)?.selection }
        swipeCount = movieList!!.size

        val movieTitleView: TextView = findViewById(R.id.movie_title)
        val movieTextView: TextView = findViewById(R.id.movie_text)
        val movieDateView: TextView = findViewById(R.id.movie_date)
        val movieVoteView: TextView = findViewById(R.id.movie_vote)
        val movieVoteCountView: TextView = findViewById(R.id.movie_vote_count)
        val imgView: ImageView = findViewById(R.id.img_swipe)

        //layout for swipe hint
        //https://www.spaceotechnologies.com/android-overlay-app-tutorial/
        /* val hintLayout: RelativeLayout = root.findViewById(R.id.hint_layout)
         val btnHint: Button = root.findViewById(R.id.got_it)
         if (!hintAccept){
             hintLayout.setVisibility(View.VISIBLE)
             btnHint.setOnClickListener(View.OnClickListener {
                 hintLayout.setVisibility(View.GONE)
                 hintAccept = true
             })
         }*/

        pgsBar = findViewById(R.id.pBar1)

        layout = findViewById(R.id.swipe_layout)

        swipeViewModel.movieTitle.observe(this, Observer {
            movieTitleView.text = it
        })
        swipeViewModel.movieText.observe(this, Observer {
            movieTextView.text = it
        })
        swipeViewModel.movieDate.observe(this, Observer {
            movieDateView.text = it
        })
        swipeViewModel.movieVote.observe(this, Observer {
            movieVoteView.text = it.toString()
        })
        swipeViewModel.movieVoteCount.observe(this, Observer {
            movieVoteCountView.text = it
        })

        nextMovie(imgView)

        touchListener(imgView)

        // Set cut corner background
        layout!!.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun nextMovie(imgView: ImageView) {
        if (temp == swipeCount!!-1) {
            //start Finished Swipe Activity
            val intent = Intent(applicationContext, FinishedSwipeActivity::class.java)
            startActivity(intent)
            temp = 0
        }else{

        var responseDetails: Boolean? = null
        movieId = movieList!!.get(i)
        if (token != null) {
            responseDetails =
                HttpRequests.getMovieDetails(BASE_URL_MovieDetails, token, movieId!!)
        }

        //check ob response error oder success
        if (!responseDetails!!) {
            // kein film, nichts machen außer nächsten anzeigen
            i += 1
            swipeCount = swipeCount?.minus(1)
            nextMovie(imgView)
        } else {
            // wenn der original Titel und Titel unters. sind, soll der Titel in Klammer drunter stehen
            if (HttpRequests.responseMovieDetails!!.original_title != HttpRequests.responseMovieDetails!!.title) {
                swipeViewModel.movieTitle.value =
                    HttpRequests.responseMovieDetails!!.original_title + "\n(" +
                            HttpRequests.responseMovieDetails!!.title + ")"
            } else {
                swipeViewModel.movieTitle.value = HttpRequests.responseMovieDetails!!.original_title
            }
            swipeViewModel.movieText.value = HttpRequests.responseMovieDetails!!.overview
            swipeViewModel.movieDate.value = HttpRequests.responseMovieDetails!!.release_date
            swipeViewModel.movieVote.value = HttpRequests.responseMovieDetails!!.vote_average
            swipeViewModel.movieVoteCount.value =
                "(" + HttpRequests.responseMovieDetails!!.vote_count.toString() + ")"

            //show image
            imgURL = HttpRequests.responseMovieDetails!!.poster_path
            // loading spinner
            pgsBar?.setVisibility(View.VISIBLE)
            Picasso.get()
                .load(imgURL)
                .into(imgView, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        pgsBar?.setVisibility(View.GONE)

                    }

                    override fun onError(e: java.lang.Exception?) {
                        imgView.setImageResource(R.drawable.cinema)
                    }
                })

            // hint: deute swipe function an
            if (!hintAccept) {
                val animator1 = ObjectAnimator.ofFloat(this, "translationX", 100f).apply {
                    duration = 750
                }
                val animator2 = ObjectAnimator.ofFloat(this, "translationX", -200f).apply {
                    duration = 1500
                }
                val animator3 = ObjectAnimator.ofFloat(this, "translationX", 0f).apply {
                    duration = 750
                }

                AnimatorSet().apply {
                    play(animator1).before(animator2)
                    play(animator3).after(animator2)
                    start()
                }

                hintAccept = true
            }
            i += 1
        }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener(imgView: ImageView) {
        layout?.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                nextMovie(imgView)

                Toast.makeText(applicationContext, "dislike", Toast.LENGTH_SHORT)
                    .show()

                temp += 1
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                rateMovie()
                nextMovie(imgView)
                Toast.makeText(applicationContext, "like", Toast.LENGTH_SHORT)
                    .show()

                temp += 1
            }
        })
    }

    fun rateMovie() {
        val movieId = movieList!!.get(i - 1)
          if (token != null) {
              HttpRequests.postRateMovie(BASE_URL_RateMovie, movieId, filterId!!, token)
          }
    }
}