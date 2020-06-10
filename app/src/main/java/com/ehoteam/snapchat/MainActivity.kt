package com.ehoteam.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText)

        if (auth.currentUser != null) {

        }
    }

    fun goClicked(view: View) {
        Log.i("email", emailEditText?.text.toString())
        // check if we can log in this user
        auth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    // sign up the user
                    auth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // add to database
                                logIn()
                            } else {
                                Toast.makeText(this, "Login failed :(", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }




    }

    fun logIn() {
        // move to next activity
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
    }
}