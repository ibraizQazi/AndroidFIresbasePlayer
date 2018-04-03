package com.ibraiz.firebaseplayerapp

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ibraiz.firebaseplayerapp.models.VideoRepository
import com.ibraiz.firebaseplayerapp.viewmodels.VideoListViewModel
import timber.log.Timber

class App : Application() {

    companion object {

        private val firebaseDataRef: DatabaseReference by lazy {
            FirebaseDatabase.getInstance().reference
        }
        private lateinit var videoRepository: VideoRepository
        private lateinit var videoListViewModel: VideoListViewModel


        fun injectVideoListViewModel() = videoListViewModel
        fun injectFirebaseDataRef() = firebaseDataRef
    }

    override fun onCreate() {
        super.onCreate()
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

        videoRepository = VideoRepository()
        videoListViewModel = VideoListViewModel(videoRepository)
    }


}