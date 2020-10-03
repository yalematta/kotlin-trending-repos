package com.yalematta.trendingrepos.data

import com.yalematta.trendingrepos.api.TrendingApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingRepository @Inject constructor(private val trendingApi: TrendingApi) {

}