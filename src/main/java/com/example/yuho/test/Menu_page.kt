package com.example.yuho.test

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.yuho.test.Model.Meal
import kotlinx.android.synthetic.main.menu.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
//import androidx.cardview.widget.CardView
import com.google.firebase.database.IgnoreExtraProperties


class Menu_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        val listView = findViewById<ListView>(R.id.listview)

        val customAdptor = CustomAdptor(this)
        listView.adapter = customAdptor

//        val username = intent.getBundleExtra("userinfo").getString("username")

        val intent = Intent(this, Checkout_page::class.java)

        listView.setOnItemClickListener{ parent, view, position, id ->
//            var bundle = Bundle()
//            bundle.putString("meal_username", username)

            val user = FirebaseAuth.getInstance().currentUser
            val username = user?.getDisplayName()
            val email = user?.getEmail()
            val user_profile = user?.photoUrl
            Log.d("MenuActivity", "username: ${username}")
            Log.d("MenuActivity", "email: ${email}")
            Log.d("MenuActivity", "ordered_meal: ${customAdptor.names[position]}")

            val user_meal = Meal(username, email, user_profile.toString(), customAdptor.names[position], customAdptor.price[position])
            lateinit var database: DatabaseReference
            database = FirebaseDatabase.getInstance().reference
            database.child("users").child(username!!).setValue(user_meal)

//            bundle.putString("meal_name", customAdptor.names[position])
//            bundle.putString("meal_price", customAdptor.price[position])
//            intent.putExtra("meal", bundle)
            startActivity(intent)
        }

    }
}

class CustomAdptor(private val context: Activity): BaseAdapter() {
    //Array of fruits names
    var names = arrayOf("大麥克", "雙層牛肉吉事堡", "四盎司牛肉堡", "雙層四盎司牛肉堡",
                        "美式培根牛肉堡", "麥香魚", "麥香雞", "麥克雞塊(6塊)")
    //Array of fruits desc
    var price = arrayOf("129", "125", "135", "165",
                        "149", "115", "108", "119")

    //Array of fruits images
    var image = intArrayOf(R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4,
                            R.drawable.food5, R.drawable.food6, R.drawable.food7, R.drawable.food8)

    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.layoutInflater
        val view1 = inflater.inflate(R.layout.row_data,null)
        val fimage = view1.findViewById<ImageView>(R.id.fimageView)
        val fName = view1.findViewById<TextView>(R.id.fName)
        val fDesc = view1.findViewById<TextView>(R.id.fDesc)
        fimage.setImageResource(image[p0])
        fName.text = names[p0]
        fDesc.text = "NT$$" + price[p0]
        return view1
    }

    override fun getItem(p0: Int): Any {
        return image[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return image.size
    }

}
