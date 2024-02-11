package com.example.streamingsite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(var MovieList:List<MovieDataClass>) : RecyclerView.Adapter<RecyclerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)


        return RecyclerViewHolder(layoutInflater.inflate(R.layout.recycleritems, parent,false))

    }

    override fun getItemCount(): Int = MovieList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = MovieList[position]
        holder.render(item)

    }

}