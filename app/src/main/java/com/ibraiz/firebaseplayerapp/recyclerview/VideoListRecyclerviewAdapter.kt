package com.ibraiz.firebaseplayerapp.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ibraiz.firebaseplayerapp.models.VideoItem

class VideoListRecyclerviewAdapter ( val items: List<ViewType>, val listener: (VideoItem) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




}