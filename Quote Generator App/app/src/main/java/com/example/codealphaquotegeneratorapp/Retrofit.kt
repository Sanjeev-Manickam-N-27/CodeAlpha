package com.example.codealphaquotegeneratorapp


import retrofit2. Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object Retrofit {
    private const val baseURL = "https://zenquotes.io/api/"

    fun getInstance() : Retrofit{
        return Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory
            .create()).build()
    }

    val RandomQuoteApi: randomQuoteApi = getInstance().create(randomQuoteApi::class.java)

}