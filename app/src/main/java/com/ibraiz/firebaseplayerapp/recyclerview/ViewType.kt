package com.ibraiz.firebaseplayerapp.recyclerview

interface ViewType {
    companion object {
        val TYPE_1 = 0
        val TYPE_2 = 1
    }
    fun getViewType(): Int
}