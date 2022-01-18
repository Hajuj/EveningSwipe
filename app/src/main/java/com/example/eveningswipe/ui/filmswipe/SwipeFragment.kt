package com.example.eveningswipe.ui.filmswipe

import android.R.attr
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
import com.example.eveningswipe.httpRequests.FilterByGroupId
import com.example.eveningswipe.httpRequests.HttpRequests
import com.example.eveningswipe.ui.rating.RatingResultFragment
import com.squareup.picasso.Picasso
import android.widget.Toast
import android.widget.LinearLayout

import android.widget.RelativeLayout

import android.widget.TextView
import android.R.attr.button
import android.opengl.Visibility


const val IMG_BASE_URL = "https://image.tmdb.org/t/p/original"
const val BASE_URL_ById = "http://192.168.2.104:8080/api/filter/byid/"
//"http://localhost:8080/api/movie/details/" --> doesn't work because it's local
//instead use: "http://YOUR_IP_ADRESS:8080/api/movie/details/"
const val BASE_URL_MovieDetails = "http://192.168.2.104:8080/api/movie/details/"
const val BASE_URL_RateMovie = "http://192.168.2.104:8080/api/filter/rate/"
var MovieById = ArrayList<FilterByGroupId>()
var i: Int = 0
var currentId: Int = 0
var posterTemp: Int = 0
var hintAccept: Boolean = false

class SwipeFragment : Fragment() {
    private var layout: View? = null
    private var imgURL: String? = null
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
    private var pgsBar: ProgressBar? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        swipeViewModel =
            ViewModelProvider(this).get(SwipeViewModel::class.java)
        val root = inflater.inflate(R.layout.swipe_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_swipe)
        val movieTextView: TextView = root.findViewById(R.id.movie_text)
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
            textView.text = it
        })
        swipeViewModel.movieText.observe(viewLifecycleOwner, Observer {
            movieTextView.text = it
        })
        //nextMovie()
        swipeViewModel.movieTitle.value = "Lorem ipsum dolor sit amet, "
        swipeViewModel.movieText.value = "consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."

        imgURL = IMG_BASE_URL + dummyPoster[posterTemp]
        Picasso.get().load(imgURL).into(imgView)
        if (posterTemp == dummyPoster.size){
            posterTemp = 0
        } else {
            posterTemp+=1
        }

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

                Toast.makeText(activity, "no match", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                rateMovie()
                nextMovie(imgView)

                Toast.makeText(activity, "This is a match", Toast.LENGTH_SHORT)
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
        val url = BASE_URL_ById + "5"
        // + element in list with group Id
        MovieById = HttpRequests.getMovieById(url)

        val handler = android.os.Handler()
        pgsBar?.setVisibility(View.VISIBLE)
        handler.postDelayed({
            //swipeViewModel.movieTitle.value = MovieById.toString()
            swipeViewModel.movieTitle.value = "Lorem ipsum dolor sit amet, "
            swipeViewModel.movieText.value = "consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
            pgsBar?.setVisibility(View.GONE)
            imgURL = IMG_BASE_URL + dummyPoster[posterTemp]
            Picasso.get().load(imgURL).into(imgView)
            posterTemp+=1

            if(posterTemp == 2){
                posterTemp =0
            }
        }, 1000)

        //movieTitle.value = "show movie "+"#"+i+" with poster and description"

        //currentId = MovieById.id
        //var imgURL = IMG_BASE_URL + MovieById.poster_path
        //Picasso.get().load(imgURL).into(imgView)
        i+=1
    }

    fun rateMovie() {
        val url = BASE_URL_RateMovie
        //+ dummy[i] + "/503"
        val movieId = "tt0165929"
        val filterId = 503
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI5IiwiaWF0IjoxNjQxMDQ2MTg5LCJleHAiOjE2NDExMzI1ODl9.Rdu8nYi_844wJLbsay0QGE3a19sbWUBMNCBbzdQ4cN0"
        HttpRequests.postRateMovie(url, movieId, filterId, token)
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

var dummyPoster = listOf(
    "/q9ASlBI2Fe5xinuMjO81UCjRoDz.jpg",
    "/vvevzdYIrk2636maNW4qeWmlPFG.jpg"
)