package com.example.chatapplication.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatapplication.R
import com.example.chatapplication.base.BaseActivity
import com.example.chatapplication.databinding.ActivityRegisterBinding
import com.example.chatapplication.ui.home.HomeActivity
import com.example.chatapplication.ui.login.LoginActivity

class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterViewModel>(),
    Navigator {

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun generateViewModel(): RegisterViewModel {
        return ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.vm = viewModel
        viewModel.navigator = this


    }


}