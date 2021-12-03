package com.example.eveningswipe.ui.filmswipe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.eveningswipe.R

class SwipeFragment : Fragment() {

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
        swipeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}