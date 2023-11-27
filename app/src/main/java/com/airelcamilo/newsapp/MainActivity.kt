package com.airelcamilo.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.airelcamilo.newsapp.adapter.NewsAdapter
import com.airelcamilo.newsapp.databinding.ActivityMainBinding
import com.airelcamilo.newsapp.model.AllNewsAPI
import com.airelcamilo.newsapp.model.NewsResponse
import com.airelcamilo.newsapp.model.RecentNewsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvRecentNews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvAllNews.layoutManager = LinearLayoutManager(this)
        getRecentNews()
        getAllNews()
    }

    private fun getRecentNews() {
        val recentNews: RecentNewsAPI = APIClient.getClient().create(RecentNewsAPI::class.java)
        recentNews.getRecentNews("us", "7df0f95ad35e45de8fd1da98392acdc3").
                enqueue(object: Callback<NewsResponse> {
                    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                        if (response.isSuccessful) {
                            val articles = response.body()?.articles ?: emptyList()
                            binding.rvRecentNews.adapter = NewsAdapter(articles.subList(0, 10))
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        Log.d("FETCH_ERROR", "Cannot fetch articles")
                    }
                })
    }

    private fun getAllNews() {
        val allNews: AllNewsAPI = APIClient.getClient().create(AllNewsAPI::class.java)
        allNews.getAllNews("us","7df0f95ad35e45de8fd1da98392acdc3").
        enqueue(object: Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    binding.rvAllNews.adapter = NewsAdapter(articles)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d("FETCH_ERROR", "Cannot fetch articles")
            }
        })
    }
}