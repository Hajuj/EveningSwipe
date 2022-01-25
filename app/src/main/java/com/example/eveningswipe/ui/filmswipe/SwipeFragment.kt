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
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.*
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.ui.rating.RatingResultFragment
import com.squareup.picasso.Picasso
import android.widget.Toast

import android.widget.RelativeLayout

import android.widget.TextView
import androidx.core.view.marginTop
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil.setContentView
import com.example.eveningswipe.FinishedSwipeActivity


const val BASE_URL_ById = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/byid/"
const val BASE_URL_MovieDetails = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/details/"
const val BASE_URL_RateMovie = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/rate/"
const val URL_GroupInfo = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/group/info"

//TODO: erste 4 Movies geben bad request -- handle bad request?
var i: Int = 4
var hintAccept: Boolean = false

class SwipeFragment : Fragment(), AdapterView.OnItemSelectedListener{
    companion object {
        fun newInstance() = SwipeFragment()
    }
    private var temp = 0
    private lateinit var root: View
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Result notification"
    private var layout: View? = null
    private var imgURL: String? = null
    private var pgsBar: ProgressBar? = null
    private lateinit var swipeViewModel: SwipeViewModel
    var movieTitle: String? = null
    val token = HttpRequests.responseToken
    var movieList: ArrayList<String>? = null
    var groupIdList: List<Int>? = null
    var currentGroupName: String? = null
    var groupNameList: MutableList<String> = mutableListOf("select group")
    lateinit var chooseLayout: RelativeLayout

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.swipe_fragment, container, false)
        groupIdList = HttpRequests.responseUserInfo.groupId.distinct()

        for (i in 0..groupIdList!!.size-1){
            HttpRequests.getGroupInformation(URL_GroupInfo, token!!, groupIdList!![i])
            while(HttpRequests.responseGroupInfo?.name == null) {
                // waiting for initialization
            }

            //kann entfernt werden, sobald es keine doppelten Gruppennamen mehr gibt
            if (HttpRequests.responseGroupInfo!!.name == "androidx.appcompat.widget.AppCompatEditText{6bf6ff7 VFED..CL. ........ 15,90-1065,274 #7f0800d1 app:id/fullName}"){
                // doppelter Gruppenname
            }else{
                while(HttpRequests.responseGroupInfo?.name == groupNameList[i]) {
                    // waiting for initialization
                }
                groupNameList.add(HttpRequests.responseGroupInfo!!.name)
            }

        }
        //first you need to select the group
        chooseLayout = root.findViewById(R.id.choose_group)
        chooseLayout.setVisibility(View.VISIBLE)

        //use spinner for selection
        val spinner: Spinner = root.findViewById(R.id.groups_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(requireContext(),
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

    fun startSwipe(currentgroupId: Int){
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

        //notification
        notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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

        //get movie list
        if (token != null) {
            HttpRequests.getFilterByGroupId(BASE_URL_ById, token, currentgroupId)
            while (HttpRequests.responseFilterByGroupId?.selection == null){
                // waiting for initialization
            }
            movieList = HttpRequests.responseFilterByGroupId?.selection
        }

        nextMovie(imgView)

        touchListener(imgView)
        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            root.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener(imgView: ImageView){
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
                println("temp: " + temp)
                Toast.makeText(activity, "like", Toast.LENGTH_SHORT)
                    .show()
                if(temp==1){
                    println("ende!!! " + temp)
                    //resultNotification()

                    //start Finished Swipe Activity
                    val intent = Intent(context, FinishedSwipeActivity::class.java)
                    startActivity(intent)
                    temp = 0
                }else{
                    temp+=1
                }
            }
        })
    }

    fun nextMovie(imgView: ImageView) {
            if (token != null) {
                //TODO: error handling wenn response 404
                HttpRequests.getMovieDetails(BASE_URL_MovieDetails, token, movieList!!.get(i))

                while(HttpRequests.responseMovieDetails == null || HttpRequests.responseMovieDetails!!.original_title == movieTitle) {
                    // waiting for initialization
                    pgsBar?.setVisibility(View.VISIBLE)
                }
                //ohne while kommt Fehlermeldung
                // FATAL EXCEPTION: main
                //    Process: com.example.eveningswipe, PID: 8511
                //    java.lang.NullPointerException

                movieTitle = HttpRequests.responseMovieDetails!!.original_title

                if (HttpRequests.responseMovieDetails!!.original_title != HttpRequests.responseMovieDetails!!.title){
                    swipeViewModel.movieTitle.value = HttpRequests.responseMovieDetails!!.original_title + "\n(" +
                            HttpRequests.responseMovieDetails!!.title + ")"
                }else{
                    swipeViewModel.movieTitle.value = HttpRequests.responseMovieDetails!!.original_title
                }
                swipeViewModel.movieText.value = HttpRequests.responseMovieDetails!!.overview
                swipeViewModel.movieDate.value = HttpRequests.responseMovieDetails!!.release_date
                swipeViewModel.movieVote.value = HttpRequests.responseMovieDetails!!.vote_average
                swipeViewModel.movieVoteCount.value = "("+ HttpRequests.responseMovieDetails!!.vote_count.toString() +")"

                //show image
                imgURL = HttpRequests.responseMovieDetails!!.poster_path
                pgsBar?.setVisibility(View.VISIBLE)
                Picasso.get()
                    .load(imgURL)
                    .into(imgView, object: com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            pgsBar?.setVisibility(View.GONE)

                        }
                        override fun onError(e: java.lang.Exception?) {
                            imgView.setImageResource(R.drawable.cinema)
                        }
                    })
            }
        if (!hintAccept){
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
        i+=1
    }

    fun rateMovie() {
        val movieId = movieList!!.get(i-1)
        val filterId = HttpRequests.responseFilterByGroupId!!.id
        println("rate??? " + filterId + movieId + HttpRequests.responseMovieDetails!!.original_title)
        if (token != null) {
            HttpRequests.postRateMovie(BASE_URL_RateMovie, movieId, filterId, token)
        }
    }

    // https://www.geeksforgeeks.org/notifications-in-kotlin/
    @SuppressLint("RemoteViewLayout")
    fun resultNotification() {
            val intent = Intent(activity, RatingResultFragment::class.java)
            val pendingIntent = PendingIntent.getActivity(activity, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            //val contentView = RemoteViews(activity?.getPackageName(), R.layout.fragment_rating_result)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                this.notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(requireContext(), channelId)
                    .setContentTitle("Voting is over!")
                    .setContentText( currentGroupName + " voted for " + "<moviename")
                    .setSmallIcon(R.drawable.movie_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            this.notificationManager.notify(1234, builder.build())
        }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        if(groupNameList.get(pos) == groupNameList[0]){
            //do nothing
        }else{
            startSwipe(groupIdList!!.get(pos-1))
            currentGroupName = groupNameList.get(pos)
            chooseLayout.setVisibility(View.GONE)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}