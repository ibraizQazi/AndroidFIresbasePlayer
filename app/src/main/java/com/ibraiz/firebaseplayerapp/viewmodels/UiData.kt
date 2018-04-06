package com.ibraiz.firebaseplayerapp.viewmodels

import com.ibraiz.firebaseplayerapp.models.VideoItem

data class VideosList(val videoItems: List<VideoItem>, val message: String, val error: Throwable? = null)
