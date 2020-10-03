package com.yalematta.trendingrepos.data

import androidx.paging.PagingSource
import com.yalematta.trendingrepos.api.TrendingApi
import com.yalematta.trendingrepos.data.model.TrendingResponseItem
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class TrendingPagingSource(
    private val trendingApi: TrendingApi,
    private val query: String
): PagingSource<Int, TrendingResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingResponseItem> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = trendingApi.getTrendingRepos("kotlin", "daily", "en")

            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}