package com.example.chatapplication.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.R
import com.example.chatapplication.UserProvider
import com.example.chatapplication.database.models.FireStoreUtils
import com.example.chatapplication.database.models.User
import com.example.chatapplication.ui.home.HomeActivity
import com.example.chatapplication.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                navigate()

            }, 2000
        )

    }

    private fun navigate() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            goToLogin()
            return
        }
        FireStoreUtils()
            .getUserFromDataBase(auth.currentUser?.uid ?: "")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    goToLogin()
                    return@addOnCompleteListener
                }
                val user = task.result.toObject(User::class.java)
                UserProvider.user = user
                goToHome()

            }


    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}