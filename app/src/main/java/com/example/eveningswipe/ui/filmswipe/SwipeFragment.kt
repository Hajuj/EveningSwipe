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

class SwipeFragment : Fragment() {
    private var layout: View? = null
    private var imgURL: String? = null
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
        val movieTextView: TextView = root.findViewById(R.id.movie_text)
        val imgView: ImageView = root.findViewById(R.id.img_swipe)
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
            }
        })
    }
}