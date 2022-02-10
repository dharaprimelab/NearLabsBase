package com.primelab.nearBase.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.primelab.nearBase.R
import com.primelab.nearBase.extensions.startNewActivityWithDefaultAnimation
import com.primelab.nearBase.ui.auth.LoginActivity
import com.primelab.nearBase.ui.home.HomeActivity
import com.primelab.nearBase.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val userViewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(!userViewModel.loggedIn){
            startNewActivityWithDefaultAnimation(LoginActivity.getIntent(this))
        }else{
            startNewActivityWithDefaultAnimation(HomeActivity.getIntent(this))
        }

    }
}