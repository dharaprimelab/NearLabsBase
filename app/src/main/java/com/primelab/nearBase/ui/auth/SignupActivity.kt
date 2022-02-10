package com.primelab.nearBase.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.primelab.nearBase.databinding.ActivitySignupBinding
import com.primelab.nearBase.ui.base.BaseActivity
import com.primelab.nearBase.viewmodel.UserViewModel
import com.primelab.nearBase.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SignupActivity::class.java)
        }
    }

    private val binding by viewBinding(ActivitySignupBinding::inflate)

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

}