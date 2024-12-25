package com.enterprise.retrofitinterceptors

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNYTimes {

    companion object{

        private fun getOkHttpClient(context: Context): OkHttpClient {

            val client = OkHttpClient.Builder()
                .addInterceptor(NetworkInterceptor(context = context))
                .addInterceptor(AuthInterceptor())
                .build()

            return client

        }


        private fun getRetrofitNewYorkTimes(context: Context): Retrofit {

            val client = getOkHttpClient(context = context)

            val retrofit =
                Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NytimesApiConstants.BaseUrl)
                .client(client)
                .build()

            return retrofit

        }


        fun getRetrofitNewYorkTimesApi(context: Context): NYTimesApi{

            val retrofitNewYorkTimes = getRetrofitNewYorkTimes(context = context)

            val nYTimesApi = retrofitNewYorkTimes.create(NYTimesApi::class.java)

            return nYTimesApi

        }


    }

}