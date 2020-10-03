package com.yalematta.trendingrepos.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuiltBy(
    val avatar: String,
    val href: String,
    val username: String
): Parcelable