package com.primelab.nearBase.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.primelab.nearBase.R
import com.primelab.nearBase.databinding.ActivityLoginBinding
import com.primelab.nearBase.extensions.viewBinding
import com.primelab.nearBase.ui.base.BaseActivity
import com.primelab.nearBase.ui.home.HomeActivity
import com.primelab.nearBase.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private val binding by viewBinding(ActivityLoginBinding::inflate)


    private val userViewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        listenToViewEvents()
    }

    private fun initViews(){
    }

    private fun listenToViewEvents() {

    }

    private fun login() {

    }

    override fun onBackPressed() {
        startActivity(HomeActivity.getIntent(this))
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim)    }
}