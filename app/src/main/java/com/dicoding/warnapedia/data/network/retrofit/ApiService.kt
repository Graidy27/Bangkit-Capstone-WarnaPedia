package com.dicoding.warnapedia.data.network.retrofit

import com.dicoding.warnapedia.data.network.request.RequestData
import com.dicoding.warnapedia.data.network.response.ResponseData
import com.dicoding.warnapedia.data.network.response.ResponseTest
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("chat/")
    fun postString(
        @Body requestData: RequestData
    ) : Call<ResponseData>

    @GET("/")
    fun getTest() : Call<ResponseTest>
}
