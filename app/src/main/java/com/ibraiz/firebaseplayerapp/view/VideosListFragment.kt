package com.ibraiz.firebaseplayerapp.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.ibraiz.firebaseplayerapp.App
import com.ibraiz.firebaseplayerapp.MainActivity
import com.ibraiz.firebaseplayerapp.R
import com.ibraiz.firebaseplayerapp.recyclerview.VideoListRecyclerviewAdapter
import com.ibraiz.firebaseplayerapp.utils.inflate
import com.ibraiz.firebaseplayerapp.utils.replaceFragment
import com.ibraiz.firebaseplayerapp.utils.toast
import com.ibraiz.firebaseplayerapp.utils.withArgs
import com.ibraiz.firebaseplayerapp.viewmodels.VideosList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.videos_fragment.*
import timber.log.Timber
import java.net.ConnectException

class VideosListFragment : MvvmFragment() {

    private val videoListViewModel = App.injectVideoListViewModel()
    private val firebaseRef = App.injectFirebaseDataRef()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.videos_fragment, false)
    }

    override fun onStart() {
        super.onStart()
        recyclerViewVideoList.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        }
        subscribe(disposable = videoListViewModel.getVideos(firebaseRef)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.d("Received UIModel with ${it.videoItems.size} videoItems.")
                            showVideos(it)
                        },
                        {
                            Timber.w(it)
                        }
                ))
    }

    private fun showVideos(data: VideosList) {
        when {
            data.error == null -> {
                recyclerViewVideoList.adapter = VideoListRecyclerviewAdapter(data.videoItems){
                    context?.toast("videoItem clicked :) "+it.videoName)
                    this.videoListViewModel.viewModelVideoItem = it
                    this.videoListViewModel.incCount(firebaseRef)
                    //startVideoPlayerFragment()
                }
            }
            data.error is ConnectException -> Timber.d("No connection, maybe inform user that data loaded from DB.")

            else -> context?.toast("error occurred :(")
        }
    }

    private fun startVideoPlayerFragment(){
        this.replaceFragment(VideoPlayerFragment.newInstance(2,"PLYR"), R.layout.video_player_fragment)
    }

    companion object {
        private val TAG = "VideoListFragment"

        @JvmStatic fun newInstance(page: Int, title: String ) = VideosListFragment().withArgs {
            putInt("videolistpage", page)
            putString("videolisttitle", title)
        }
    }
}
