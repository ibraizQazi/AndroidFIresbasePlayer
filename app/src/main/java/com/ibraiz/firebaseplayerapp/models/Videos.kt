package com.ibraiz.firebaseplayerapp.models

import android.os.Parcelable
import com.ibraiz.firebaseplayerapp.recyclerview.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoItem (val videoName: String, val videoLink: String, var count: Int = 0, var uuid: String = "") : Parcelable, ViewType {
    override fun getViewType(): Int = ViewType.TYPE_1
}

@Parcelize
data class TextItem (val textString: String) : Parcelable, ViewType {
    override fun getViewType(): Int = ViewType.TYPE_2
}