package com.applsh.androiduipractice.daangn

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DaangnItemModel(
    val title: String,
    val image: String,
) : Parcelable