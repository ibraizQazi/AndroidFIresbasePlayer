package com.ibraiz.firebaseplayerapp.viewmodels

import com.google.firebase.database.DatabaseReference
import com.ibraiz.firebaseplayerapp.models.VideoRepository
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class VideoListViewModel(val videoRepository: VideoRepository) {

    fun getVideos(postReference: DatabaseReference): Observable<VideosList> {
        //Create the data for your UI, the users lists and maybe some additional data needed as well
        return videoRepository.getVideosFromFirebase(postReference)
                //Drop DB data if we can fetch item fast enough from the API
                //to avoid UI flickers
                .debounce(400, TimeUnit.MILLISECONDS)
                .map {
                    Timber.d("Mapping videos to UIData...")
                    VideosList(it, "Videos")
                }
                .onErrorReturn {
                    VideosList(emptyList(), "An error occurred", it)
                }
    }
}
