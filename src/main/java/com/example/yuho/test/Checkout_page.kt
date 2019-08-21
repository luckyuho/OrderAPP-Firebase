package com.example.yuho.test

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
//import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.yuho.test.Model.Meal
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.checkout_layout.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class Checkout_page : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_layout)

//        val intent = intent

        // Input data
        val result_username = findViewById<TextView>(R.id.checkout_username)
        val result_meal = findViewById<TextView>(R.id.checkout_meal)
        val result_price = findViewById<TextView>(R.id.checkout_price)

        // Show checkout information
        GetOrderedInformation(result_username, result_meal, result_price)

        // Save Order
        checkout_confirm.setOnClickListener {
            // poopout image
            val image = checkout_image.findViewById<ImageView>(R.id.checkout_image)
            image.setImageResource(R.drawable.checkout)

            Toast.makeText(this@Checkout_page, "Smart Choice!", Toast.LENGTH_SHORT).show()
        }

    }
}

private fun ShowCheckout(result_username:TextView, result_meal:TextView, result_price:TextView){
    // Get bundle data

//    val user = FirebaseAuth.getInstance().currentUser
//    val username = user?.displayName
//    val meal_name = GetOrderedInformation("ordered_meal")
//    val meal_price = GetOrderedInformation("ordered_price")
//
////    val username = intent.getBundleExtra("meal").getString("meal_username")
////    val meal_name = intent.getBundleExtra("meal").getString("meal_name")
////    val meal_price = intent.getBundleExtra("meal").getString("meal_price")
//
//    result_username.text = "Eater:  $username"
//    result_meal.text = "Meal:  $meal_name"
//    result_price.text = "Price:  NT$$meal_price"
}

private fun GetOrderedInformation(result_username:TextView, result_meal:TextView, result_price:TextView){
    val user = FirebaseAuth.getInstance().currentUser
    val username = user?.displayName

    // Write a message to the database
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users").child(username!!)

    // Read from the database
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            val user_meal = dataSnapshot.getValue(Meal::class.java)
            Log.d("Checkout", "Value is: ${user_meal}")

            result_username.text = "Eater:  $username"
            result_meal.text = "Meal:  ${user_meal!!.ordered_meal}"
            result_price.text = "Price:  NT$${user_meal!!.ordered_price}"

        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w("Checkout", "Failed to read value.", error.toException())
        }
    })
}
