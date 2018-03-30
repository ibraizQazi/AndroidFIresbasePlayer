package com.ibraiz.firebaseplayerapp.viewmodels

import com.ibraiz.firebaseplayerapp.models.VideoRepository
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class VideoListViewModel(val videoRepository: VideoRepository) {

    fun getUsers(): Observable<VideoList> {
        //Create the data for your UI, the users lists and maybe some additional data needed as well
        return videoRepository.getVideosFromFirebase()
                //Drop DB data if we can fetch item fast enough from the API
                //to avoid UI flickers
                .debounce(400, TimeUnit.MILLISECONDS)
                .map {
                    Timber.d("Mapping users to UIData...")
                    VideoList(it.take(10), "Top 10 Users")
                }
                .onErrorReturn {
                    VideoList(emptyList(), "An error occurred", it)
                }
    }
}
