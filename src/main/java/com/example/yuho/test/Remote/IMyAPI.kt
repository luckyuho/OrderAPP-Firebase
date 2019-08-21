package com.example.yuho.test.Remote

import com.example.yuho.test.Model.ARIResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IMyAPI {

    @FormUrlEncoded
    @POST("register.php")
    fun registerUser(@Field("username") username:String, @Field("password") password:String):Call<ARIResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun loginUser(@Field("username") username:String, @Field("password") password:String):Call<ARIResponse>


}