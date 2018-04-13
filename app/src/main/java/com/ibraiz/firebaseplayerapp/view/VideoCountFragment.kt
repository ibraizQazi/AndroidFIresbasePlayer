package com.ibraiz.firebaseplayerapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ibraiz.firebaseplayerapp.App
import com.ibraiz.firebaseplayerapp.R
import com.ibraiz.firebaseplayerapp.recyclerview.VideoListRecyclerviewAdapter
import com.ibraiz.firebaseplayerapp.utils.inflate
import com.ibraiz.firebaseplayerapp.utils.toast
import com.ibraiz.firebaseplayerapp.utils.withArgs
import com.ibraiz.firebaseplayerapp.viewmodels.VideosList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.videos_fragment.*
import timber.log.Timber
import java.net.ConnectException

class VideoCountFragment : MvvmFragment() {

    val videoListViewModel = App.injectVideoListViewModel()
    val firebaseRef = App.injectFirebaseDataRef()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.videos_fragment, false)
    }

    override fun onStart() {
        super.onStart()
        recyclerViewVideoList.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        }
        subscribe(videoListViewModel.getVideoCount(firebaseRef)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.d("Received UIModel with ${it.videoItems.size} videoItems.")
                            showVideoCount(it)
                        },
                        {
                            Timber.w(it)
                            context?.toast(it.localizedMessage)
                        }
                ))
    }

    private fun showVideoCount(data: VideosList) {
        when {
            data.error == null -> {
                val list = data.videoItems.sortedBy { it.count }
                recyclerViewVideoList.adapter = VideoListRecyclerviewAdapter(list){
                    context?.toast("videoItem clicked :) "+it.videoName)
                    //videoListViewModel.viewModelVideoItem = it
                }
            }
            data.error is ConnectException -> Timber.d("No connection, maybe inform user that data loaded from DB.")

            else -> context?.toast("error occurred :(")
        }
    }

    companion object {
        private val TAG = "VideoCountFragment"
        @JvmStatic fun newInstance(page: Int, title: String) = VideoCountFragment().withArgs {
            putInt("videocountpage", page)
            putString("videocounttitle", title)
        }
    }
}