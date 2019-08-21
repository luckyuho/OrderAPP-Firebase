package com.example.yuho.test.Model

import android.net.Uri
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Meal(
    var username: String? = "",
    var email: String? = "",
    var profileImageUri: String? = "",
    var ordered_meal: String? = "",
    var ordered_price: String? = ""
)