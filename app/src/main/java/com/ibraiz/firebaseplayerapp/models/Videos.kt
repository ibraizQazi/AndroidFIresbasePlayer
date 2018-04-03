package com.ibraiz.firebaseplayerapp.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.ibraiz.firebaseplayerapp.recyclerview.ViewType
import kotlinx.android.parcel.Parcelize

/*@Parcelize
data class VideoItem (val videoName: String, val videoLink: String, var count: Int = 0, var uuid: String = "") : Parcelable, ViewType {
    override fun getViewType(): Int = ViewType.TYPE_1
}*/

@IgnoreExtraProperties
class VideoItem : ViewType{

    var videoName: String? = ""
    var videoLink: String? = ""
    var count: Int? = 0

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    constructor(vidName: String, vidLink: String, cnt: Int) {
        this.videoName = vidName
        this.videoLink = vidLink
        this.count = cnt
    }

    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put("videoname", videoName!!)
        result.put("videoLink", videoLink!!)
        result.put("count", count!!)

        return result
    }

    override fun getViewType(): Int = ViewType.TYPE_1
}

/*
@Parcelize
data class TextItem (val textString: String) : Parcelable, ViewType {
    override fun getViewType(): Int = ViewType.TYPE_2
}*/
@IgnoreExtraProperties
class OtherItem : ViewType{
    var otherName: String? = ""

    constructor(){

    }

    constructor(otherName: String){
        this.otherName = otherName
    }

    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result.put("othername", otherName!!)
        return result
    }
    override fun getViewType(): Int = ViewType.TYPE_2
}