package com.example.eveningswipe

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RemoteViews

/**
 * activity show view that all movies are swiped and sends notification
 * @param mostSwiped button to show ranking result
 * @param notificationManager - description to create a notification
 */
class FinishedSwipeActivity : AppCompatActivity() {

    private var mostSwiped: Button? = null
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Result notification"

    /**
     * create content of activity
     * get views and call notification
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_swipe)

        mostSwiped = findViewById<View>(R.id.mostSwiped) as Button
        mostSwiped!!.setOnClickListener(View.OnClickListener { showMostSwiped() })

        // Set cut corner background
        val layout = findViewById<View>(R.id.finished_swipe_layout)
        layout.setBackgroundResource(R.drawable.shr_product_grid_background_shape)

        //notification
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        resultNotification()
    }

    /**
     * send user to ranking activity by click on button
     */
    private fun showMostSwiped() {
        val intent = Intent(this, RankingActivity::class.java)
        this.startActivity(intent)
    }

    /**
     * function that handles the notification
     * source: https://www.geeksforgeeks.org/notifications-in-kotlin/
     */
    @SuppressLint("RemoteViewLayout", "UnspecifiedImmutableFlag")
    fun resultNotification() {
        val intent = Intent(this, RankingActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationChannel =
            NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(true)
        this.notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(applicationContext, channelId)
            .setContentTitle(resources.getString(R.string.voting_over))
            .setContentText(resources.getString(R.string.click_noti))
            .setSmallIcon(R.drawable.movie_icon)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.ic_launcher_background
                )
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        this.notificationManager.notify(1234, builder.build())
    }
}