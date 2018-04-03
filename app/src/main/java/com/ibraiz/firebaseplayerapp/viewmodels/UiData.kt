package com.ibraiz.firebaseplayerapp.viewmodels

import com.ibraiz.firebaseplayerapp.recyclerview.ViewType

data class VideosList(val videoItems: List<ViewType>, val message: String, val error: Throwable? = null)
