package com.airelcamilo.newsapp.model

import retrofit2.http.GET
import retrofit2.http.Query

interface RecentNewsAPI {
    @GET("top-headlines")
    fun getRecentNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): retrofit2.Call<NewsResponse>
}