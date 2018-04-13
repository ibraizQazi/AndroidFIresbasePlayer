package com.ibraiz.firebaseplayerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibraiz.firebaseplayerapp.R
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.ibraiz.firebaseplayerapp.App
import android.annotation.SuppressLint
import android.net.Uri
import android.support.v4.app.Fragment
import android.view.Surface
import android.view.View.inflate
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.decoder.DecoderCounters
import com.google.android.exoplayer2.audio.AudioRendererEventListener
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoRendererEventListener
import com.ibraiz.firebaseplayerapp.utils.withArgs
import timber.log.Timber
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.ibraiz.firebaseplayerapp.utils.inflate
import kotlinx.android.synthetic.main.video_player_fragment.*


class VideoPlayerFragment : Fragment(){

    private lateinit var videoUri: Uri

    private lateinit var player: SimpleExoPlayer

    private val componentListener: ComponentListener = ComponentListener()

    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var playWhenReady = true


    private val videoListViewModel = App.injectVideoListViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.video_player_fragment, container, false)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.putInt("current_window", currentWindow)
        savedInstanceState.putLong("play_back_pos", playbackPosition)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong("play_back_pos")
            currentWindow = savedInstanceState.getInt("current_window")
        }

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer () {

        player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(), DefaultLoadControl())

        playerView.player = player
        player.playWhenReady = this.playWhenReady
        player.seekTo(currentWindow,playbackPosition)
        videoUri = /*Uri.parse(videoListViewModel.viewModelVideoItem.videoLink) ?:*/ Uri.parse("https://player.vimeo.com/external/257512761.hd.mp4?s=2f4ede50164f921df0e039f7e11281ebe665a9fc&profile_id=175")
        val mediaSource = buildMediaSource(videoUri)
        player.prepare(mediaSource, true, false)
    }

    private fun releasePlayer() {
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        playWhenReady = player.playWhenReady
        player.removeListener(componentListener)
        player.removeVideoDebugListener(componentListener)
        player.removeAudioDebugListener(componentListener)
        player.release()
    }
    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
                DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri)
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    companion object {
        private const val TAG = "VideoPlayerFragment"
        private var BANDWIDTH_METER = DefaultBandwidthMeter()
        @JvmStatic fun newInstance(page: Int, url: String) = VideoPlayerFragment().withArgs {
            putInt("videoplayerpage", page)
            putString("videoplayerurl", url)
        }
    }

    private inner class ComponentListener : Player.DefaultEventListener(), VideoRendererEventListener, AudioRendererEventListener {

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            val stateString: String = when (playbackState) {
                Player.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                Player.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                Player.STATE_READY -> "ExoPlayer.STATE_READY     -"
                Player.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Timber.d(TAG, "changed state to $stateString playWhenReady: $playWhenReady")
        }

        // Implementing VideoRendererEventListener.

        override fun onVideoEnabled(counters: DecoderCounters) {
            // Do nothing.
        }

        override fun onVideoDecoderInitialized(decoderName: String, initializedTimestampMs: Long, initializationDurationMs: Long) {
            // Do nothing.
        }

        override fun onVideoInputFormatChanged(format: Format) {
            // Do nothing.
        }

        override fun onDroppedFrames(count: Int, elapsedMs: Long) {
            // Do nothing.
        }

        override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float) {
            // Do nothing.
        }

        override fun onRenderedFirstFrame(surface: Surface) {
            // Do nothing.
        }

        override fun onVideoDisabled(counters: DecoderCounters) {
            // Do nothing.
        }

        // Implementing AudioRendererEventListener.

        override fun onAudioEnabled(counters: DecoderCounters) {
            // Do nothing.
        }

        override fun onAudioSessionId(audioSessionId: Int) {
            // Do nothing.
        }

        override fun onAudioDecoderInitialized(decoderName: String, initializedTimestampMs: Long, initializationDurationMs: Long) {
            // Do nothing.
        }

        override fun onAudioInputFormatChanged(format: Format) {
            // Do nothing.
        }

        override fun onAudioSinkUnderrun(bufferSize: Int, bufferSizeMs: Long, elapsedSinceLastFeedMs: Long) {
            // Do nothing.
        }

        override fun onAudioDisabled(counters: DecoderCounters) {
            // Do nothing.
        }

    }
}