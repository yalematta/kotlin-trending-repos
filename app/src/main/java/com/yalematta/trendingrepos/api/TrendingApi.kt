package com.yalematta.trendingrepos.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApi {

    companion object {
        const val BASE_URL = "https://ghapi.huchen.dev/"
    }

    @GET("repositories")
    suspend fun getTrendingRepos(
        @Query("language") language: String,
        @Query("since") since: String,
        @Query("spoken_language_code") spokenLanguage: String
    ): TrendingResponse
}