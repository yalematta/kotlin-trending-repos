package com.yalematta.trendingrepos.data.model

import android.os.Parcelable
import com.yalematta.trendingrepos.data.model.BuiltBy
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrendingResponseItem(
    val author: String,
    val avatar: String,
    val builtBy: List<BuiltBy>,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String,
    val languageColor: String,
    val name: String,
    val stars: Int,
    val url: String
): Parcelable