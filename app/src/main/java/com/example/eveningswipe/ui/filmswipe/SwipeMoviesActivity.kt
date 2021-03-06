package com.example.eveningswipe.ui.filmswipe

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eveningswipe.FinishedSwipeActivity
import com.example.eveningswipe.R
import com.example.eveningswipe.httpRequests.HttpRequests
import com.squareup.picasso.Picasso


/**
 * variable that can be accessed from other activities
 * @param swipeCount how many movies to swipe
 */
var swipeCount: Int? = null

/**
 * class to select the movie before swipe
 * @param temp variable to know when the last movie is reached
 * @param swipeState index in the movieList
 * @param token current token of the user
 * @param hintAccept true if the hint has been seen
 * @param movieId id of the selected movie
 * @param movieList list of all movie ids in the filter
 * @param pgsBar loading spinner
 * @param layout swipe layout
 * @param imgURL url of the movie image
 * @param layoutSwipe
 * @param swipeViewModel View Model of the activity
 * @param BASE_URL_MovieDetails url for http request
 * @param BASE_URL_RateMovie url for http request
 *
 */
class SwipeMoviesActivity : AppCompatActivity() {

    private var temp = 0
    var swipeState: Int = 0
    val token = HttpRequests.responseToken
    var hintAccept: Boolean = false
    var movieId: String? = null
    var movieList: ArrayList<String>? = null
    private var pgsBar: ProgressBar? = null
    private var layout: View? = null
    private var imgURL: String? = null
    private var layoutSwipe: ImageView? = null
    private var scrollView: ScrollView? = null
    private lateinit var swipeViewModel: SwipeViewModel
    val BASE_URL_MovieDetails = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/details/"
    val BASE_URL_MovieInfo = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/info/"
    val BASE_URL_RateMovie = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/rate/"
    val BASE_URL_SwipeState = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/swipestate"
    private var like: ImageView? = null
    private var dislike: ImageView? = null


    /**
     * create context of activity
     * fill movieList with movie ids
     * get all views
     */
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_movies)
        swipeViewModel = ViewModelProvider(this).get(SwipeViewModel::class.java)

        // get swipe state
        val responseState = HttpRequests.getSwipeState(BASE_URL_SwipeState, token!!, filterId!!)
        if (!responseState!!) {
            //wait
        } else {
            swipeState = HttpRequests.responseSwipeState!!.nextMovie
        }
        temp = swipeState

        // get the ids of all movies in the filter
        movieList = filterIndex?.let { HttpRequests.responseFilterByGroupId?.get(it)?.selection }

        val filterSize = filterIndex?.let { HttpRequests.responseFilterByGroupId?.get(it)?.size }
        //decide how many movies to swipe (count of movies or filter size)
        if (movieList!!.size < filterSize!!) {
            swipeCount = movieList!!.size
        } else {
            swipeCount = filterSize
        }

        // get all views
        val movieTitleView: TextView = findViewById(R.id.movie_title)
        val movieTextView: TextView = findViewById(R.id.movie_text)
        val movieDateView: TextView = findViewById(R.id.movie_date)
        val movieVoteView: TextView = findViewById(R.id.movie_vote)
        val movieVoteCountView: TextView = findViewById(R.id.movie_vote_count)
        val imgView: ImageView = findViewById(R.id.img_swipe)
        layoutSwipe = findViewById(R.id.img_swipe)
        scrollView = findViewById(R.id.swipe_movie_layout)
        pgsBar = findViewById(R.id.pBar1)
        like = findViewById(R.id.like)
        dislike = findViewById(R.id.dislike)

        //layout for swipe hint -- old
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

        // get data from view model
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
        layout = findViewById(R.id.swipe_layout)
        layout!!.setBackgroundResource(R.drawable.shr_product_grid_background_shape)
    }

    /**
     * function to show next movie
     * get movie details and fill view with content
     */
    @SuppressLint("ObjectAnimatorBinding")
    fun nextMovie(imgView: ImageView) {
        //if all movies are swiped call finished swipe activity
        if (temp >= swipeCount!! - 1) {
            //start Finished Swipe Activity
            val intent = Intent(applicationContext, FinishedSwipeActivity::class.java)
            startActivity(intent)
            temp = 0
        } else {

            // get movie details of the current shown movie
            var responseDetails: Boolean? = null
            var responseOtherDetails: Boolean? = null
            movieId = movieList!!.get(swipeState)
            if (token != null) {
                responseDetails =
                    HttpRequests.getMovieDetails(BASE_URL_MovieDetails, token, movieId!!)
            }

            //check if response error or success
            if (!responseDetails!!) {
                // no data for movie - show next one
                if (token != null) {
                    responseOtherDetails =
                        HttpRequests.getOtherMovieDetails(BASE_URL_MovieInfo, token, movieId!!)
                }

                if (!responseOtherDetails!!) {
                    swipeState += 1
                    swipeCount = swipeCount?.minus(1)
                    temp += 1
                    nextMovie(imgView)
                } else {
                    // fill views with movie details
                    // fancy original title? -> add title in brackets
                    if (HttpRequests.responseOtherMovieDetails!!.title.contains(HttpRequests.responseOtherMovieDetails!!.original_title)) {
                        swipeViewModel.movieTitle.value =
                            HttpRequests.responseOtherMovieDetails!!.original_title
                    } else {
                        swipeViewModel.movieTitle.value =
                            HttpRequests.responseOtherMovieDetails!!.original_title + "\n(" +
                                    HttpRequests.responseOtherMovieDetails!!.title + ")"
                    }
                    swipeViewModel.movieText.value = getString(R.string.google)
                    swipeViewModel.movieDate.value =
                        HttpRequests.responseOtherMovieDetails!!.release_date
                    swipeViewModel.movieVote.value =
                        HttpRequests.responseOtherMovieDetails!!.vote_average
                    swipeViewModel.movieVoteCount.value =
                        "(" + HttpRequests.responseOtherMovieDetails!!.vote_count.toString() + ")"

                    // get image, show loading spinner while loading
                    imgURL = HttpRequests.responseOtherMovieDetails!!.poster_path
                    pgsBar?.setVisibility(View.VISIBLE)
                    if (imgURL == "") {
                        imgView.setImageResource(R.drawable.cinema_small)
                        pgsBar?.setVisibility(View.GONE)
                    } else {
                        Picasso.get()
                            .load(imgURL)
                            .into(imgView, object : com.squareup.picasso.Callback {
                                override fun onSuccess() {
                                    pgsBar?.setVisibility(View.GONE)
                                }

                                override fun onError(e: java.lang.Exception?) {
                                    imgView.setImageResource(R.drawable.cinema_small)
                                    pgsBar?.setVisibility(View.GONE)
                                }
                            })
                    }


                    // show swipe animation hint - just one time
                    if (!hintAccept) {
                        val animator1 =
                            ObjectAnimator.ofFloat(layoutSwipe, "translationX", 100f).apply {
                                duration = 750
                            }
                        val animator2 =
                            ObjectAnimator.ofFloat(layoutSwipe, "translationX", -100f).apply {
                                duration = 750
                            }
                        val animator2_1 =
                            ObjectAnimator.ofFloat(layoutSwipe, "translationX", -200f).apply {
                                duration = 750
                            }
                        val animator3 =
                            ObjectAnimator.ofFloat(layoutSwipe, "translationX", 0f).apply {
                                duration = 750
                            }
                        val animator4 =
                            ObjectAnimator.ofFloat(like, View.ALPHA, 1f).apply {
                                duration = 750
                            }
                        val animator5 =
                            ObjectAnimator.ofFloat(like, View.ALPHA, 0f).apply {
                                duration = 750
                            }
                        val animator6 =
                            ObjectAnimator.ofFloat(dislike, View.ALPHA, 1f).apply {
                                duration = 750
                            }
                        val animator7 =
                            ObjectAnimator.ofFloat(dislike, View.ALPHA, 0f).apply {
                                duration = 750
                            }
                        val liking = AnimatorSet().apply {
                            play(animator1).with(animator4)
                        }
                        val back1 = AnimatorSet().apply {
                            play(animator2).with(animator5)
                        }
                        val disliking = AnimatorSet().apply {
                            play(animator2_1).with(animator6)
                        }
                        val back2 = AnimatorSet().apply {
                            play(animator3).with(animator7)
                        }

                        AnimatorSet().apply {
                            play(liking).before(back1)
                            play(disliking).after(back1)
                            play(back2).after(disliking)
                            start()
                        }
                        hintAccept = true
                    }
                    swipeState += 1
                }

            } else {
                // fill views with movie details
                // fancy original title? -> add title in brackets
                if (HttpRequests.responseMovieDetails!!.original_title != HttpRequests.responseMovieDetails!!.title) {
                    swipeViewModel.movieTitle.value =
                        HttpRequests.responseMovieDetails!!.original_title + "\n(" +
                                HttpRequests.responseMovieDetails!!.title + ")"
                } else {
                    swipeViewModel.movieTitle.value =
                        HttpRequests.responseMovieDetails!!.original_title
                }
                swipeViewModel.movieText.value = HttpRequests.responseMovieDetails!!.overview
                swipeViewModel.movieDate.value = HttpRequests.responseMovieDetails!!.release_date
                swipeViewModel.movieVote.value = HttpRequests.responseMovieDetails!!.vote_average
                swipeViewModel.movieVoteCount.value =
                    "(" + HttpRequests.responseMovieDetails!!.vote_count.toString() + ")"

                // get image, show loading spinner while loading
                imgURL = HttpRequests.responseMovieDetails!!.poster_path
                if (imgURL == "") {
                    imgView.setImageResource(R.drawable.cinema_small)
                    pgsBar?.setVisibility(View.GONE)
                } else {
                    Picasso.get()
                        .load(imgURL)
                        .into(imgView, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                pgsBar?.setVisibility(View.GONE)
                            }

                            override fun onError(e: java.lang.Exception?) {
                                imgView.setImageResource(R.drawable.cinema_small)
                                pgsBar?.setVisibility(View.GONE)
                            }
                        })
                }


                // show swipe animation hint - just one time
                if (!hintAccept) {
                    val animator1 =
                        ObjectAnimator.ofFloat(layoutSwipe, "translationX", 100f).apply {
                            duration = 750
                        }
                    val animator2 =
                        ObjectAnimator.ofFloat(layoutSwipe, "translationX", -100f).apply {
                            duration = 750
                        }
                    val animator2_1 =
                        ObjectAnimator.ofFloat(layoutSwipe, "translationX", -200f).apply {
                            duration = 750
                        }
                    val animator3 =
                        ObjectAnimator.ofFloat(layoutSwipe, "translationX", 0f).apply {
                            duration = 750
                        }
                    val animator4 =
                        ObjectAnimator.ofFloat(like, View.ALPHA, 1f).apply {
                            duration = 750
                        }
                    val animator5 =
                        ObjectAnimator.ofFloat(like, View.ALPHA, 0f).apply {
                            duration = 750
                        }
                    val animator6 =
                        ObjectAnimator.ofFloat(dislike, View.ALPHA, 1f).apply {
                            duration = 750
                        }
                    val animator7 =
                        ObjectAnimator.ofFloat(dislike, View.ALPHA, 0f).apply {
                            duration = 750
                        }
                    val liking = AnimatorSet().apply {
                        play(animator1).with(animator4)
                    }
                    val back1 = AnimatorSet().apply {
                        play(animator2).with(animator5)
                    }
                    val disliking = AnimatorSet().apply {
                        play(animator2_1).with(animator6)
                    }
                    val back2 = AnimatorSet().apply {
                        play(animator3).with(animator7)
                    }

                    AnimatorSet().apply {
                        play(liking).before(back1)
                        play(disliking).after(back1)
                        play(back2).after(disliking)
                        start()
                    }
                    hintAccept = true
                }
                swipeState += 1
            }
        }
    }

    /**
     * function to swipe the movie image
     * track user's movement and correspond accordingly
     */
    @SuppressLint("ClickableViewAccessibility")
    fun touchListener(imgView: ImageView) {

        val displayMetrics = resources.displayMetrics
        val halfDisplayWidth = displayMetrics.widthPixels.toFloat() / 2
        val maxSwipe = halfDisplayWidth + 250
        val minSwipe = halfDisplayWidth - 250
        var lastX = 0f
        var newX = 0f

        layoutSwipe?.setOnTouchListener(
            View.OnTouchListener { view, event ->

                when (event.action) {
                    //hold the movie image
                    MotionEvent.ACTION_DOWN -> {
                        scrollView!!.requestDisallowInterceptTouchEvent(true)
                        lastX = view.x - event.rawX
                    }

                    //move the movie image
                    MotionEvent.ACTION_MOVE -> {
                        newX = event.rawX

                        //move the image to the right or left
                        view.animate().x(newX + lastX).setDuration(0).start()
                        updateAlphaOfBadges(newX);
                    }

                    //release the movie image
                    MotionEvent.ACTION_UP -> {
                        val currentX = newX

                        //center the image when swiped or finger released
                        layoutSwipe!!.animate().translationX(0f).setDuration(150).start()
                        updateAlphaOfBadges(halfDisplayWidth);

                        if (currentX != 0f) {
                            //swipe all the way to the right
                            if (currentX > maxSwipe) {
                                newX = 0f
                                rateMovie(true)
                                nextMovie(imgView)
                                Toast.makeText(
                                    applicationContext,
                                    resources.getString(R.string.like),
                                    Toast.LENGTH_SHORT
                                ).show()

                                temp += 1
                            }

                            //swipe all the way to the lef
                            if (currentX < minSwipe) {
                                newX = 0f
                                rateMovie(false)
                                nextMovie(imgView)
                                Toast.makeText(
                                    applicationContext,
                                    resources.getString(R.string.dislike),
                                    Toast.LENGTH_SHORT
                                ).show()

                                temp += 1
                            }
                        }
                    }
                }
                return@OnTouchListener true
            }
        )
    }

    /**
     * function is called if movie is a match/like
     * post movie rating
     */
    fun rateMovie(like: Boolean) {
        val movieId = movieList!!.get(swipeState - 1)
        if (token != null) {
            HttpRequests.postRateMovie(BASE_URL_RateMovie, movieId, filterId!!, like, token)
        }
    }

    // set alpha of like and dislike badges
    private fun updateAlphaOfBadges(posX: Float) {
        val screenWidth: Float = resources.displayMetrics.widthPixels.toFloat()

        var alpha = 0f
        if (posX > screenWidth / 2) {
            alpha = posX / (screenWidth)
            like!!.alpha = alpha
            dislike!!.alpha = -alpha
        } else if (posX == screenWidth / 2) {
            dislike!!.alpha = alpha
            like!!.alpha = alpha
        } else {
            alpha = posX / (screenWidth / 2)
            dislike!!.alpha = 1 - alpha
            like!!.alpha = -alpha
        }
    }
}