package com.ibraiz.firebaseplayerapp.recyclerview

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ibraiz.firebaseplayerapp.R
import com.ibraiz.firebaseplayerapp.models.OtherItem
import com.ibraiz.firebaseplayerapp.models.VideoItem
import com.ibraiz.firebaseplayerapp.utils.inflate
import kotlinx.android.synthetic.main.other_viewholder.view.*
import kotlinx.android.synthetic.main.video_viewholder.view.*

class VideoListRecyclerviewAdapter ( val items: List<ViewType>, val listener: (VideoItem) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TYPE_1 ->
                VideoViewHolder(parent.inflate(R.layout.video_viewholder))

            ViewType.TYPE_2 ->
                VideoViewHolder(parent.inflate(R.layout.video_viewholder))

            else -> throw IllegalStateException("unsupported item type")
        }    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when(viewType){

            ViewType.TYPE_1 -> {
                val videoItem = items[position] as VideoItem
                val videoViewHolder = holder as VideoViewHolder
                videoViewHolder.bindItems(videoItem, listener)
            }

            ViewType.TYPE_2 -> {
                val otherItem = items[position] as OtherItem
                val otherViewholder = holder as OtherViewHolder
                otherViewholder.bindItems(otherItem)
            }
            else -> throw IllegalStateException("unsupported item type")
        }
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun getItemCount(): Int = items.size


    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(dataObj: VideoItem, listener: (VideoItem) -> Unit) {
            with(itemView) {
                videoName.text = "Name: ${dataObj.videoName}"
                videoCount.text = "Count: ${dataObj.count}"
                deleteBtn.setOnClickListener{
                    it.setBackgroundColor(Color.GREEN)
                    Toast.makeText(context, "Delete request sent :(", Toast.LENGTH_SHORT).show()
                }
                setOnClickListener { listener(dataObj) }
            }
        }
    }

    inner class OtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(dataObj: OtherItem) {
            with(itemView) {
                otherTextView.text = "Name: ${dataObj.otherName}"
            }
        }
    }

}