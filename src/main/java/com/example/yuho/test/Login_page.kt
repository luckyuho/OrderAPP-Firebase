package com.example.yuho.test

//import android.support.v7.app.AppCompatActivity
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.yuho.test.Common.Common
import com.example.yuho.test.Model.ARIResponse
import com.example.yuho.test.Remote.IMyAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class Login_page : AppCompatActivity() {

//    internal lateinit var mService: IMyAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init service
//        mService = Common.api
//        val intent = Intent(this, Menu_page::class.java)



        //Event
        login.setOnClickListener {

            // Write a message to the database
//            val database = FirebaseDatabase.getInstance()
//            val myRef = database.getReference("message")
//
//            myRef.setValue("Hello, World!")
//            Toast.makeText(this@Login_page, "Helo World", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Menu_page::class.java)
            firebase_authenticateUser(login_email.text.toString(), login_password.text.toString(), intent)

//            php_authenticateUser(login_username.text.toString(), login_password.text.toString(), intent)
        }

        // register
        register.setOnClickListener {

            showRegistration()

            // select photo
            register_select_photo.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)
            }

            // save new user infomation
            save.setOnClickListener {

                firebase_createNewUser(register_username.text.toString(), register_email.text.toString(), register_password.text.toString())

//            php_createNewUser(register_username.text.toString(), register_password.text.toString())
//            showHome()
            }

        }

        Login_to_home.setOnClickListener {
            showHome()
        }
    }

//    private fun php_createNewUser(username: String, password: String) {
//        mService.registerUser(username, password).enqueue(object : Callback<ARIResponse> {
//            override fun onFailure(call: Call<ARIResponse>, t: Throwable) {
//                Toast.makeText(this@Login_page, t!!.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<ARIResponse>, response: Response<ARIResponse>) {
//                if (username.isEmpty() || password.isEmpty() || response!!.body()!!.error)
//                    Toast.makeText(this@Login_page, response.body()!!.error_msg, Toast.LENGTH_SHORT).show()
//                else {
//                    Toast.makeText(this@Login_page, "Register Success!" + response.body()!!.uid, Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//        })
//    }

//    private fun php_authenticateUser(username: String, password: String, intent: Intent) {
//        mService.loginUser(username, password).enqueue(object : Callback<ARIResponse> {
//            override fun onFailure(call: retrofit2.Call<ARIResponse>, t: Throwable) {
//                Toast.makeText(this@Login_page, t!!.message, Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: retrofit2.Call<ARIResponse>, response: Response<ARIResponse>) {
//                if (response!!.body()!!.error)
//                    Toast.makeText(this@Login_page, response.body()!!.error_msg, Toast.LENGTH_SHORT).show()
//                else {
//                    Toast.makeText(this@Login_page, "Hello " + username + "!", Toast.LENGTH_SHORT).show()
//                    var bundle = Bundle()
//                    bundle.putString("username", username)
//                    bundle.putString("password", password)
//                    intent.putExtra("userinfo", bundle)
//                    startActivity(intent)
//                }
//            }
//        })
//    }

    private fun showRegistration() {
        registration_layout.visibility = View.VISIBLE
        home_l1.visibility = View.GONE
    }

    private fun showHome() {
        registration_layout.visibility = View.GONE
        home_l1.visibility = View.VISIBLE
    }

    // Firebase create new user
    private fun firebase_createNewUser(username: String, email: String, password: String) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username, email and password!", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()
            uploadImageToFirebaseStorage()

        }.addOnFailureListener {
            Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }

    }

    // Firebase login
    private fun firebase_authenticateUser(email: String, password: String, intent: Intent) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password!", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }.addOnFailureListener {
            Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }

    }

    var selectedPhotoUri: Uri? = null

    // startActivityForResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            // proceed and Check what the selected image was...
            Log.d("RegisterActivity", "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            register_save_photo.setImageBitmap(bitmap)

            register_select_photo.alpha = 0f
        }
    }

    // Firebase upload image
    private fun uploadImageToFirebaseStorage(){
        Log.d("uploadImageToFirebaseStorage", "selectedPhotoUri: ${selectedPhotoUri}")
        if (selectedPhotoUri == null){

            UpdateProfile(register_username.text.toString(), register_email.text.toString())

        } else {

            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/Images/$filename")

            ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
                Log.d("uploadImageToFirebaseStorage", "Successfully upload image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("uploadImageToFirebaseStorage", "File location: $it")

                    UpdateProfile(
                        register_username.text.toString(),
                        register_email.text.toString(),
                        it.toString()
                    )
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // save new user information
    private fun UpdateProfile(username: String, email: String, profileImageUri: String? = "") {

//        val user_database = User(username, email, profileImageUri)
//        lateinit var database: DatabaseReference
//        database = FirebaseDatabase.getInstance().reference
//        database.child("users").child(username).setValue(user_database)
        Log.d("saveUserbaseDatabase", "Save information to Firebase Database")
        val user_profile = FirebaseAuth.getInstance().currentUser

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .setPhotoUri(Uri.parse(profileImageUri))
            .build()

        user_profile?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("UpdateProfile", "User profile updated.")
                }
            }

    }

//    private fun UpdateProfile() {
//        val user = FirebaseAuth.getInstance().currentUser
//
//        val profileUpdates = UserProfileChangeRequest.Builder()
//            .setDisplayName(username)
//            .setPhotoUri(Uri.parse(profileImageUri))
//            .build()
//
//        user?.updateProfile(profileUpdates)
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("UpdateProfile", "User profile updated.")
//                }
//            }
//    }

}

//@IgnoreExtraProperties
//data class User(
//    var username: String? = "",
//    var email: String? = "",
//    var profileImageUri: String? = ""
//)
