package com.example.eveningswipe.ui.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eveningswipe.R

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

       /* galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        // Set cut corner background for API 23+
        val layout = root.findViewById<View>(R.id.gallery_corner)
        layout.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)
        return root
    }
}