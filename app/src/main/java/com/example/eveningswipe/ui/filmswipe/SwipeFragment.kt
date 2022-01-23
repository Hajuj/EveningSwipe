package com.example.eveningswipe.ui.filmswipe

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
import android.os.Build
import android.widget.*
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.ui.rating.RatingResultFragment
import com.squareup.picasso.Picasso
import android.widget.Toast

import android.widget.RelativeLayout

import android.widget.TextView


const val BASE_URL_ById = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/byid/"
const val BASE_URL_MovieDetails = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/movie/details/"
const val BASE_URL_RateMovie = "http://msp-ws2122-6.mobile.ifi.lmu.de:80/api/filter/rate/"

//TODO: erste 3 Movies geben bad request -- handle bad request?
var i: Int = 4
var currentId: Int = 0
var posterTemp: Int = 0
var hintAccept: Boolean = false

class SwipeFragment : Fragment() {
    companion object {
        fun newInstance() = SwipeFragment()
    }
    private var temp = 0

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Result notification"
    private lateinit var swipeViewModel: SwipeViewModel
    var movieTitle: String? = null
    private var layout: View? = null
    private var imgURL: String? = null
    private var pgsBar: ProgressBar? = null
    val token = HttpRequests.responseToken
    var groupId = 431
    var movieList: ArrayList<String>? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        swipeViewModel =
            ViewModelProvider(this).get(SwipeViewModel::class.java)
        val root = inflater.inflate(R.layout.swipe_fragment, container, false)
        val movieTitleView: TextView = root.findViewById(R.id.movie_title)
        val movieTextView: TextView = root.findViewById(R.id.movie_text)
        val movieDateView: TextView = root.findViewById(R.id.movie_date)
        val movieVoteView: TextView = root.findViewById(R.id.movie_vote)
        val movieVoteCountView: TextView = root.findViewById(R.id.movie_vote_count)
        val imgView: ImageView = root.findViewById(R.id.img_swipe)

        //layout for swipe hint
        //https://www.spaceotechnologies.com/android-overlay-app-tutorial/
        val hintLayout: RelativeLayout = root.findViewById(R.id.hint_layout)
        val btnHint: Button = root.findViewById(R.id.got_it)
        if (!hintAccept){
            hintLayout.setVisibility(View.VISIBLE)
            btnHint.setOnClickListener(View.OnClickListener {
                hintLayout.setVisibility(View.GONE)
                hintAccept = true
            })
        }

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
            HttpRequests.getFilterByGroupId(BASE_URL_ById, token, groupId)
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
        return root
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

                Toast.makeText(activity, "like", Toast.LENGTH_SHORT)
                    .show()
                if(temp==5){
                    resultNotification()
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
                            Picasso.get().load("https://media.istockphoto.com/vectors/back-row-view-of-a-cinema-screen-vector-id96611482?k=20&m=96611482&s=612x612&w=0&h=clnNR2OsgNFjk83AYDTJoh8Q6cVaYD4LsYO0teqNTN4=").into(imgView)
                        }
                    })
            }
        i+=1
    }

    fun rateMovie() {
        val movieId = dummy[i]
        val filterId = 503
        if (token != null) {
           // HttpRequests.postRateMovie(BASE_URL_RateMovie, movieId, filterId, token)
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
                    .setContentText("<groupname>" + " voted for " + "<moviename")
                    .setSmallIcon(R.drawable.movie_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            this.notificationManager.notify(1234, builder.build())
        }
}


//dummy list
var dummy = listOf(
    "tt0120667",
    "tt0120737",
    "tt0120755",
    "tt0120804",
    "tt0120903",
    "tt0121765",
    "tt0121766",
    "tt0132910",
    "tt0133152",
    "tt0134847",
    "tt0141926",
    "tt0145487",
    "tt0146316",
    "tt0151150",
    "tt0151299",
    "tt0159273",
    "tt0159378",
    "tt0160009",
    "tt0160275",
    "tt0164052",
    "tt0164184",
    "tt0165929",
    "tt0167190",
    "tt0167260",
    "tt0167261",
    "tt0168504",
    "tt0170452",
    "tt0171827",
    "tt0172156",
    "tt0172495",
    "tt0173840",
    "tt0177971",
    "tt0179626",
    "tt0179987",
    "tt0181689",
    "tt0181852",
    "tt0183790",
    "tt0184858",
    "tt0184894",
    "tt0186566",
    "tt0187078",
    "tt0187393",
    "tt0187738",
    "tt0190332",
    "tt0190865",
    "tt0192614",
    "tt0194104",
    "tt0194704",
    "tt0199753",
    "tt0199898",
    "tt0202677",
    "tt0205380",
    "tt0206634",
    "tt0207198",
    "tt0207826",
    "tt0208198",
    "tt0208988",
    "tt0209163",
    "tt0210223",
    "tt0211938",
    "tt0212346",
    "tt0212867",
    "tt0212879",
    "tt0213149",
    "tt0214728",
    "tt0215715",
    "tt0215824",
    "tt0216216",
    "tt0216651",
    "tt0218533",
    "tt0218817",
    "tt0220623",
    "tt0227194",
    "tt0227445",
    "tt0228750",
    "tt0230055",
    "tt0232500",
    "tt0233142",
    "tt0233469",
    "tt0233600",
    "tt0234000",
    "tt0234215",
    "tt0236027",
    "tt0236167",
    "tt0237534",
    "tt0238380",
    "tt0238552",
    "tt0238883",
    "tt0239235",
    "tt0239341",
    "tt0239655",
    "tt0240510",
    "tt0242445",
    "tt0242519",
    "tt0242653",
    "tt0243278",
    "tt0243415",
    "tt0243573",
    "tt0243609",
    "tt0243876",
    "tt0244479",
    "tt0244884",
    "tt0245562",
    "tt0245803",
    "tt0245844",
    "tt0246460",
    "tt0246544",
    "tt0247944",
    "tt0248012",
    "tt0248185",
    "tt0249371",
    "tt0250687",
    "tt0251094",
    "tt0251433",
    "tt0251756",
    "tt0252277",
    "tt0252503",
    "tt0253556",
    "tt0265086",
    "tt0265226",
    "tt0266048",
    "tt0266308",
    "tt0266408",
    "tt0266465",
    "tt0266697",
    "tt0266987",
    "tt0267287",
    "tt0269043",
    "tt0269217",
    "tt0271748",
    "tt0271946",
    "tt0272020",
    "tt0272105",
    "tt0273516",
    "tt0273870",
    "tt0274188",
    "tt0274830",
    "tt0275083",
    "tt0275277",
    "tt0275688",
    "tt0275773",
    "tt0276210",
    "tt0276816",
    "tt0277327",
    "tt0277386",
    "tt0277434",
    "tt0277941",
    "tt0278274",
    "tt0278291",
    "tt0278351",
    "tt0279112",
    "tt0280380",
    "tt0280427",
    "tt0280486",
    "tt0280490",
    "tt0280609",
    "tt0280990",
    "tt0281019",
    "tt0281718"
)