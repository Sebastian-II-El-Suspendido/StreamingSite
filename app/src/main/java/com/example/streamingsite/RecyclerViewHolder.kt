package com.example.streamingsite

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.streamingsite.databinding.ActivityLoginBinding
import com.google.android.material.imageview.ShapeableImageView

class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view){

    val movieData = view.findViewById<TextView>(R.id.textView14)
    val poster = view.findViewById<ShapeableImageView>(R.id.imageView4)
    fun render(movieDataModel: MovieDataClass){
        movieData.text = movieDataModel.title
        movieData.text = movieDataModel.posterPath


    }

}