package com.ibraiz.firebaseplayerapp.viewmodels

import com.ibraiz.firebaseplayerapp.models.VideoItem

data class VideoList(val videoItems: List<VideoItem>, val message: String, val error: Throwable? = null)
