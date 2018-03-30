package com.ibraiz.firebaseplayerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ibraiz.firebaseplayerapp.App
import com.ibraiz.firebaseplayerapp.R
import com.ibraiz.firebaseplayerapp.viewmodels.VideoList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.videos_fragment.*
import timber.log.Timber
import java.net.ConnectException

class VideosListFragment : MvvmFragment() {

    val videoListViewModel = App.injectVideoListViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.videos_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        subscribe(videoListViewModel.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Received UIModel with ${it.videoItems.size} videoItems.")
                    showUsers(it)
                }, {
                    Timber.w(it)
                    showError()
                }))
    }

    fun showUsers(data: VideoList) {
        when {

            data.error == null -> videosList.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, data.videoItems)

            data.error is ConnectException -> Timber.d("No connection, maybe inform user that data loaded from DB.")

            else -> showError()
        }
    }

    fun showError() {
        Toast.makeText(context, "An error occurred :(", Toast.LENGTH_SHORT).show()
    }
}
