package com.example.codealphaquotegeneratorapp

import retrofit2.Response
import retrofit2.http.GET

interface randomQuoteApi {

    @GET("random")
    suspend fun getRandomQuote() :Response<List<Quotes>>
}