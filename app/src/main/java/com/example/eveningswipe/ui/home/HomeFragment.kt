package com.example.eveningswipe.ui.home

import android.app.ActionBar
import android.graphics.drawable.ColorDrawable
import android.os.Build
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


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var letsSwipe: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            root.background = context?.getDrawable(R.drawable.cinema)
        }
        return root

        letsSwipe = root.findViewById(R.id.letsSwipe) as Button
        letsSwipe!!.setOnClickListener(View.OnClickListener { startSwiping() })
    }

    private fun startSwiping() {

    }
}