package com.example.eveningswipe.ui.filmswipe

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.eveningswipe.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.widget.Button
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import com.example.eveningswipe.ui.rating.RatingResultFragment

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

        notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        layout = root.findViewById(R.id.swipe_layout)
        swipeViewModel.movieTitle.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        swipeViewModel.movieText.observe(viewLifecycleOwner, Observer {
            movieTextView.text = it
        })
        swipeViewModel.nextMovie(imgView)
        //imgURL = swipeViewModel.getMovieData()
        //Picasso.get().load(imgURL).into(imgView)
        touchListener(imgView)
        return root
    }

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener(imgView: ImageView){
        layout?.setOnTouchListener(object : OnSwipeTouchListener(activity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                swipeViewModel.nextMovie(imgView)
               // imgURL = swipeViewModel.getMovieData()
               // Picasso.get().load(imgURL).into(imgView)

                Toast.makeText(activity, "no match", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                swipeViewModel.rateMovie()
                swipeViewModel.nextMovie(imgView)
                //imgURL = swipeViewModel.getMovieData()
                //Picasso.get().load(imgURL).into(imgView)

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
                    .setContentTitle("Voting is finished!")
                    .setContentText("<groupname>" + " voted for " + "<moviename")
                    .setSmallIcon(R.drawable.movie_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            this.notificationManager.notify(1234, builder.build())
        }

}