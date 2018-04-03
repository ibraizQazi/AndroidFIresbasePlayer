package com.ibraiz.firebaseplayerapp.models

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Transaction
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.database.MutableData
import timber.log.Timber
import com.firebase.ui.database.FirebaseRecyclerAdapter
import android.text.method.TextKeyListener.clear
import com.google.firebase.database.ValueEventListener
import com.ibraiz.firebaseplayerapp.recyclerview.ViewType


class VideoRepository{



    fun getVideosFromFirebase(postRef: DatabaseReference): Observable<List<ViewType>> {
        val videoList = ArrayList<ViewType>()
 /*       postRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Timber.d("The read failed: " + databaseError.message)
            }

            override fun onDataChange(videoSnapshot: DataSnapshot?) {
                videoList.clear()
                for (videoSnapshot in snapshot.children) {
                    val videoItem = videoSnapshot.getValue<VideoItem>(VideoItem::class.java)

                    videoItem?.let { videoList.add(it) }

                    // here you can access to name property like university.name

                }
            }

        })*/
        postRef.child("videoItems").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                videoList.clear()

                for (videoSnapshot in snapshot.children) {
                    val videoItem = videoSnapshot.getValue<VideoItem>(VideoItem::class.java)

                    videoItem?.let {
                        videoList.add(it)
                        Timber.d("snapshot.children :" + it.videoName)
                    }

                    // here you can access to name property like university.name

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Timber.d("The read failed: " + databaseError.message)
            }
        })


        return Observable.just(videoList)
    }

    fun incVideoCount(postRef: DatabaseReference) {

        postRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val value = mutableData.getValue(VideoItem::class.java)
                if (value?.count == null) {
                    mutableData.value = 0
                } else {
                    mutableData.value = value.count?.inc()
                }

                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError, b: Boolean, dataSnapshot: DataSnapshot) {
                Timber.d("transaction:onComplete:$databaseError")
            }
        })
    }

    fun removeVideo(postRef: DatabaseReference){
        postRef.removeValue()
    }
}