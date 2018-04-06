package com.ibraiz.firebaseplayerapp.viewmodels

import com.google.firebase.database.DatabaseReference
import com.ibraiz.firebaseplayerapp.models.VideoItem
import com.ibraiz.firebaseplayerapp.models.VideoRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class VideoListViewModel(val videoRepository: VideoRepository) {

    lateinit var viewModelVideoItem: VideoItem


    fun getVideos(postReference: DatabaseReference): Flowable<VideosList> {

        return videoRepository.getVideosFromFirebase(postReference)
                //to avoid UI flickers
                //.debounce(400, TimeUnit.MILLISECONDS)
                .map {
                    Timber.d("Mapping videos to UIData...")
                    VideosList(it, "Videos")
                }
                .onErrorReturn {
                    VideosList(emptyList(), "An error occurred", it)
                }
    }

    fun incCount(postReF: DatabaseReference) : Completable{
        return videoRepository.incVideoCount(postReF)
    }

    fun getVideoCount(postReF: DatabaseReference): Flowable<VideosList> {

        return  videoRepository.getVideosFromFirebase(postReF)
                //to avoid UI flickers
                //.debounce(400, TimeUnit.MILLISECONDS)
                .map {

                    it.sortedBy { it.count }
                    Timber.d("Mapping videos to UIData...")
                    VideosList(it, "Sorted Videos")
                }
                .onErrorReturn {
                    VideosList(emptyList(), "An error occurred", it)
                }
    }

}
