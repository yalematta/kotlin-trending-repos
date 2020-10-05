package com.yalematta.trending.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val id: Int,
    val login: String,
    val avatar_url: String
): Parcelable