package com.banty.firebasedb.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import java.lang.IllegalArgumentException

class SignUpViewModel(): ViewModel() {



    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
                return SignUpViewModel() as T
            }

            throw IllegalArgumentException("SignInViewModel cannot be created")
        }

    }
}