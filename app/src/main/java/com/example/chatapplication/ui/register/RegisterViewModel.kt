package com.example.chatapplication.ui.register

import androidx.databinding.ObservableField
import com.example.chatapplication.UserProvider
import com.example.chatapplication.base.BaseViewModel
import com.example.chatapplication.database.models.FireStoreUtils
import com.example.chatapplication.database.models.User
import com.example.chatapplication.isValidEmail
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel :
    BaseViewModel<Navigator>() {

    val userName = ObservableField<String>()
    val userNameError = ObservableField<String?>()
    val email = ObservableField<String>()
    val emailError = ObservableField<String?>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<String?>()
    val passwordConfirm = ObservableField<String>()
    val passwordConfirmError = ObservableField<String?>()


    var auth = FirebaseAuth.getInstance()
    fun register() {
        if (!validateForm()) return

        auth.createUserWithEmailAndPassword(
            email.get()!!,
            password.get()!!,
        ).addOnCompleteListener { task ->
            insertUserToDataBase(task.result.user?.uid!!)
            if (task.isSuccessful) {
                navigator?.showMessage("success Registration")
            } else {
                navigator?.hideDialogue()
                navigator?.showMessage(task.exception?.localizedMessage ?: "")
            }

        }


    }

    private fun insertUserToDataBase(uid: String) {
        val user = User(
            id = uid,
            userName = userName.get(),
            email = email.get()

        )
        FireStoreUtils()
            .insertUserToDataBase(user)
            .addOnCompleteListener { task ->
                navigator?.hideDialogue()
                if (task.isSuccessful) {
                    //     navigator?.showMessage("succesful registeration")
                    UserProvider.user = user
                    navigator?.goToHome()

                } else {
                    navigator?.showMessage(task.exception?.localizedMessage ?: "")
                }

            }

    }

    var isValid = true
    fun validateForm(): Boolean {

        if (userName.get().isNullOrBlank()) {
            userNameError.set("please enter userName")
            isValid = false
        } else {
            isValid = true
            userNameError.set(null)
        }
        if (email.get().isNullOrBlank()) {
            emailError.set("please enter your email ")
            isValid = false
        } else if (email.get()?.isValidEmail() == false) {
            emailError.set("please enter valid email")
            isValid = false

        } else {
            isValid = true
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()) {
            passwordError.set("please enter password")
            isValid = false
        } else {
            isValid = true
            passwordError.set(null)
        }
        if (passwordConfirm.get().isNullOrBlank()) {
            isValid = false
            passwordConfirmError.set("please re_enter password ")
        } else if (password.get()?.equals(passwordConfirm.get()) == false) {
            passwordConfirmError.set("not match")
        } else {
            isValid = true
            passwordConfirmError.set(null)
        }



        return isValid
    }

    fun goToLogin() {
        navigator?.goToLogin()
    }

}

