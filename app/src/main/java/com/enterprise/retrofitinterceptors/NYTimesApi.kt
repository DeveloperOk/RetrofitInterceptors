package com.enterprise.retrofitinterceptors

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYTimesApi {

    @GET("lists/{date}/{list}.json")
    suspend fun getBooks(@Path("date") date: String,
                         @Path("list") list: String,
                         @Query("api-key") apiKey: String): Response<BooksData?>

}