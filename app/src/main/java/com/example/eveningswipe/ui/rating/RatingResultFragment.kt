package com.example.eveningswipe.ui.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eveningswipe.R
import com.example.eveningswipe.ui.rating.RatingResultViewModel

class RatingResultFragment : Fragment() {

    private lateinit var ratingResultViewModel: RatingResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ratingResultViewModel =
            ViewModelProvider(this).get(RatingResultViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_rating_result, container, false)
        val textView: TextView = root.findViewById(R.id.text_rating_result)
        ratingResultViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}