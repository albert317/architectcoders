package com.app.login.presentation.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.app.login.presentation.R
import com.app.login.presentation.common.providers
import com.app.login.presentation.databinding.FragmentSignInBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    lateinit var binding: FragmentSignInBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        configAuth()
    }

    private fun configAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            Log.d("LOGIN", "" + firebaseAuth.currentUser?.displayName)
            Log.d("LOGIN", "PHOTO: " + firebaseAuth.currentUser?.photoUrl)
        } else {
            authLogin.launch(
                AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false, false)
                    .setTheme(com.app.uicomponent.R.style.Theme_UiArchitectCoders)
                    .setLockOrientation(true)
                    .build()
            )
        }
    }


    private val authLogin =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val response = IdpResponse.fromResultIntent(result.data)
            if (result.resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    Log.d("LOGIN", "" + it.toString())
                }
            } else {
                if (response == null) {
                    Log.d("LOGIN", "ERROR")
                } else {
                    response.error?.let {
                        if (it.errorCode == ErrorCodes.NO_NETWORK) {
                        } else {

                        }
                    }
                }
            }
        }
}
