package com.primelab.nearBase.extensions

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*


fun provideFactory(application: Application, bundle: Bundle) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return try {
                modelClass.getConstructor(Application::class.java, Bundle::class.java)
                    .newInstance(application, bundle)
            } catch (e: Exception) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
    }

fun provideFactory(application: Application) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return try {
                modelClass.getConstructor(Application::class.java)
                    .newInstance(application)
            } catch (e: Exception) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
    }

fun FragmentActivity.onBackPressedOverride(func: () -> Unit?, viewLifecycleOwner: LifecycleOwner) {
    this.onBackPressedDispatcher.addCallback(
        viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                func()
            }
        })
}

fun Fragment.onBackPressedOverride(func: () -> Unit?) {
    activity?.onBackPressedOverride(func, viewLifecycleOwner)
}

fun Fragment.observeOnDestroy(action: () -> Unit) {
    viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { viewLifecycleOwner ->
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                action.invoke()
            }
        })
    }
}

fun Fragment.showKeyboard(target: View) {
    val imm = target.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(target, InputMethodManager.HIDE_IMPLICIT_ONLY)
}