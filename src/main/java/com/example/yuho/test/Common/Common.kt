package com.example.yuho.test.Common

import com.example.yuho.test.Remote.IMyAPI
import com.example.yuho.test.Remote.RetrofitClient

object Common {
    val BASE_URL="http://192.168.64.2/test/"

    val api: IMyAPI
        get() = RetrofitClient.getClient(BASE_URL).create(IMyAPI::class.java)
}