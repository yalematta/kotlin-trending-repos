package com.yalematta.trending.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("html_url")
    val url: String,

    @SerializedName("stargazers_count")
    val stars: Int,

    @SerializedName("forks_count")
    val forks: Int,

    @SerializedName("language")
    val language: String?,

    @SerializedName("watchers")
    val watchers: Int,

    @SerializedName("owner")
    val owner: Owner,

    @SerializedName("created_at")
    val createDate: String,

    @SerializedName("updated_at")
    val updateDate: String,

    @SerializedName("open_issues")
    val openIssues: Int

) : Parcelable