package com.ibraiz.firebaseplayerapp.models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Observable

class VideoRepository{

    private val firebaseData: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }

    fun getVideosFromFirebase(): Observable<List<VideoItem>> {
        return Observable.empty()
    }
}