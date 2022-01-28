package com.example.eveningswipe.ui.filmswipe

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.eveningswipe.R
import android.content.Intent
import android.widget.*
import com.example.eveningswipe.httpRequests.HttpRequests
import com.squareup.picasso.Picasso
import android.widget.Toast
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.eveningswipe.FinishedSwipeActivity
import com.example.eveningswipe.httpRequests.GetFilterByGroupId2


const val BASE_URL_ById = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/byid/"
const val BASE_URL_MovieDetails = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/details/"
const val BASE_URL_RateMovie = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/rate/"
const val URL_GroupInfo = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/group/info"

class SwipeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        fun newInstance() = SwipeFragment()
    }

    private var temp = 0
    private lateinit var root: View
    private var layout: View? = null
    private var imgURL: String? = null
    private var pgsBar: ProgressBar? = null
    private lateinit var swipeViewModel: SwipeViewModel
    val token = HttpRequests.responseToken
    var movieList: ArrayList<String>? = null
    var groupIdList: List<Int>? = null
    var currentGroupName: String? = null
    var groupNameList: MutableList<String> = mutableListOf("select group")
    lateinit var chooseLayout: RelativeLayout
    var i: Int = 0
    var hintAccept: Boolean = false

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.swipe_fragment, container, false)

        groupIdList = HttpRequests.responseUserInfo.groupId.distinct()

        for (i in 0..groupIdList!!.size - 1) {
            val response = HttpRequests.getGroupInformation(URL_GroupInfo, token!!, groupIdList!![i])

            if (!response!!){
                // error
            } else {
                groupNameList.add(HttpRequests.responseGroupInfo!!.name)
            }
        }
        //first you need to select the group
        chooseLayout = root.findViewById(R.id.choose_group)
        chooseLayout.setVisibility(View.VISIBLE)

        //use spinner for selection
        val spinner: Spinner = root.findViewById(R.id.groups_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            groupNameList
        )
        // Set layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.setAdapter(adapter)

        spinner.onItemSelectedListener = this
        chooseLayout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)

        return root
    }

    fun startSwipe() {
        swipeViewModel =
            ViewModelProvider(this).get(SwipeViewModel::class.java)

        val movieTitleView: TextView = root.findViewById(R.id.movie_title)
        val movieTextView: TextView = root.findViewById(R.id.movie_text)
        val movieDateView: TextView = root.findViewById(R.id.movie_date)
        val movieVoteView: TextView = root.findViewById(R.id.movie_vote)
        val movieVoteCountView: TextView = root.findViewById(R.id.movie_vote_count)
        val imgView: ImageView = root.findViewById(R.id.img_swipe)

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

        pgsBar = root.findViewById(R.id.pBar1)

        layout = root.findViewById(R.id.swipe_layout)

        swipeViewModel.movieTitle.observe(viewLifecycleOwner, Observer {
            movieTitleView.text = it
        })
        swipeViewModel.movieText.observe(viewLifecycleOwner, Observer {
            movieTextView.text = it
        })
        swipeViewModel.movieDate.observe(viewLifecycleOwner, Observer {
            movieDateView.text = it
        })
        swipeViewModel.movieVote.observe(viewLifecycleOwner, Observer {
            movieVoteView.text = it.toString()
        })
        swipeViewModel.movieVoteCount.observe(viewLifecycleOwner, Observer {
            movieVoteCountView.text = it
        })

        nextMovie(imgView)

        touchListener(imgView)

        // Set cut corner background
        root.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)

    }

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener(imgView: ImageView) {
        layout?.setOnTouchListener(object : OnSwipeTouchListener(activity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                nextMovie(imgView)

                Toast.makeText(activity, "dislike", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                rateMovie()
                nextMovie(imgView)
                Toast.makeText(activity, "like", Toast.LENGTH_SHORT)
                    .show()
                //TODO: wie lange darf geswiped werden?
                if (temp == 1) {
                    //start Finished Swipe Activity
                    val intent = Intent(context, FinishedSwipeActivity::class.java)
                    startActivity(intent)
                    temp = 0
                } else {
                    temp += 1
                }
            }
        })
    }

    fun nextMovie(imgView: ImageView) {
        var responseDetails: Boolean? = null
        if (token != null) {
            responseDetails =
                HttpRequests.getMovieDetails(BASE_URL_MovieDetails, token, movieList!!.get(i))
        }

        //check ob response error oder success
        if (!responseDetails!!) {
            // kein film, nichts machen außer nächsten anzeigen
            i += 1
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
                val animator1 = ObjectAnimator.ofFloat(view, "translationX", 100f).apply {
                    duration = 750
                }
                val animator2 = ObjectAnimator.ofFloat(view, "translationX", -200f).apply {
                    duration = 1500
                }
                val animator3 = ObjectAnimator.ofFloat(view, "translationX", 0f).apply {
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

    fun rateMovie() {
        val movieId = movieList!!.get(i - 1)

      /*  val filterId = HttpRequests.responseFilterByGroupId!!.id

        if (token != null) {
            HttpRequests.postRateMovie(BASE_URL_RateMovie, movieId, filterId, token)
        }*/
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        if (groupNameList.get(pos) == groupNameList[0]) {
            //do nothing first element is just a hint to select group
        } else {
            //check if group has a filter
            //get movie list
            if (token != null) {
                val response = HttpRequests.getFilterByGroupId(BASE_URL_ById, token, groupIdList!!.get(pos - 1))
                if (!response!! ) {
                    Toast.makeText(
                        context,
                        "This group needs a filter. Please go back to the group settings and add a filter.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    //do nothing
                } else {
                    //TODO: add! FilterId + rateMovie()
                    movieList = HttpRequests.responseFilterByGroupId?.get(0)?.selection
                    startSwipe()
                    currentGroupName = groupNameList.get(pos)
                    chooseLayout.setVisibility(View.GONE)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}