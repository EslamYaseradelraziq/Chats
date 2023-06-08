package com.example.chatapplication.ui.login

import androidx.databinding.ObservableField
import com.example.chatapplication.UserProvider
import com.example.chatapplication.base.BaseViewModel
import com.example.chatapplication.database.models.FireStoreUtils
import com.example.chatapplication.database.models.User
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : BaseViewModel<LoginNavigator>() {
    val email = ObservableField<String>()
    val emailError = ObservableField<String?>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<String?>()

    val auth = FirebaseAuth.getInstance()
    fun login() {
        if (!validForm()) return
        navigator?.showLoading("loading....")
        auth.signInWithEmailAndPassword(
            email.get()!!,
            password.get()!!
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                getUserFromDataBase(task.result.user?.uid ?: "")
                // navigator?.showMessage(task.result.user?.uid ?: "")
                return@addOnCompleteListener
            } else {
                navigator?.hideDialogue()
                navigator?.showMessage(task.exception?.localizedMessage ?: "")
            }
        }

    }

    private fun getUserFromDataBase(uid: String) {
        FireStoreUtils()
            .getUserFromDataBase(uid)
            .addOnCompleteListener { task ->
                navigator?.hideDialogue()
                if (task.isSuccessful) {
                    val user = task.result.toObject(User::class.java)
                    UserProvider.user = user
                    navigator?.goToHome()
                } else {
                    navigator?.showMessage(task.exception?.localizedMessage ?: "")
                }

            }
    }

    fun validForm(): Boolean {
        var isValid = true
        if (email.get().isNullOrBlank()) {
            emailError.set("please enter avalide email")
            isValid = false
        } else {
            isValid = true
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()) {
            passwordError.set("please enter valid password")
            isValid = false
        } else {
            isValid = true
            passwordError.set(null)
        }
        return isValid
    }

    fun goToRegister() {
        navigator?.goToRegister()
    }
}