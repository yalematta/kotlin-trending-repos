package com.yalematta.trendingrepos.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.yalematta.trendingrepos.api.TrendingApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingRepository @Inject constructor(private val trendingApi: TrendingApi) {

    fun getTrendingRepos(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TrendingPagingSource(trendingApi, query) }
        ).liveData
}