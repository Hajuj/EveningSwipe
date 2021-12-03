package com.example.eveningswipe.ui.filmswipe

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.eveningswipe.R

class SwipeFragment : Fragment() {
    private var layout: View? = null

    companion object {
        fun newInstance() = SwipeFragment()
    }

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
        layout = root.findViewById(R.id.swipe_layout)
        swipeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        touchListener()
        return root
    }

    @SuppressLint("ClickableViewAccessibility")
    fun touchListener(){
        layout?.setOnTouchListener(object : OnSwipeTouchListener(activity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                Toast.makeText(activity, "Swipe Left gesture detected",
                    Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                Toast.makeText(
                    activity,
                    "Swipe Right gesture detected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onSwipeUp() {
                super.onSwipeUp()
                Toast.makeText(activity, "Swipe up gesture detected", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                Toast.makeText(activity, "Swipe down gesture detected", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


}