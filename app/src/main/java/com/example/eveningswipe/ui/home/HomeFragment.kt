package com.example.eveningswipe.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eveningswipe.R
import com.example.eveningswipe.ui.filmswipe.SwipeFragment


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var letsSwipe: Button? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        root.setBackgroundResource(R.drawable.cinema_background)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        // Set cut corner background
        root.background = context?.getDrawable(R.drawable.cinema_background)

        letsSwipe = root.findViewById(R.id.letsSwipe) as Button
        letsSwipe?.setVisibility(View.VISIBLE)
        letsSwipe!!.setOnClickListener(View.OnClickListener { startSwiping(root) })

        return root
    }

    @SuppressLint("ResourceAsColor")
    private fun startSwiping(root: View) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment ,SwipeFragment())
        transaction?.addToBackStack(null);
        transaction?.commit()
        root.setBackgroundResource(R.drawable.bg_canteloupe)
        letsSwipe?.setVisibility(View.GONE)
    }
}