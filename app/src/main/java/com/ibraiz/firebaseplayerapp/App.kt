package com.ibraiz.firebaseplayerapp

import android.app.Application
import com.ibraiz.firebaseplayerapp.models.VideoRepository
import com.ibraiz.firebaseplayerapp.viewmodels.VideoListViewModel
import timber.log.Timber

class App : Application() {

    companion object {

        private lateinit var videoRepository: VideoRepository
        private lateinit var videoListViewModel: VideoListViewModel


        fun injectVideoListViewModel() = videoListViewModel

    }

    override fun onCreate() {
        super.onCreate()
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

        videoRepository = VideoRepository()
        videoListViewModel = VideoListViewModel(videoRepository)
    }


}