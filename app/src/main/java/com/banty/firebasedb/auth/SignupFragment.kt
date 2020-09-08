package com.banty.firebasedb.auth

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.banty.firebasedb.BaseFragment
import com.banty.firebasedb.R
import com.banty.firebasedb.auth.viewmodels.SignUpViewModel
import com.banty.firebasedb.utils.isValidEmail
import com.banty.firebasedb.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : BaseFragment() {
    private var alreadyMemberText: TextView? = null

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val viewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(this, SignUpViewModel.Factory()).get(SignUpViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        alreadyMemberText = view.findViewById(R.id.text_already_member)
        setupRichText(alreadyMemberText, R.string.already_member) {
            this@SignupFragment.findNavController()
                .navigate(SignupFragmentDirections.actionAuthFragmentToSignInFragment())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_signup.setOnClickListener { initSignUp() }
    }

    private fun initSignUp() {
        val email: String? = editText_email.text.toString()
        val password: String? = editText_password.text.toString()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            showToast("Please fill in all details")
        } else {
            if (email.isValidEmail()) {
                signup(email, password)
            } else {
                showToast("Not a valid email.")
            }
        }
    }

    private fun signup(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(SignupFragmentDirections.actionAuthFragmentToMainFragment())
                } else {
                    Log.w("Tag##", "User creation failed: ${task.exception?.message}")
                    showToast("Sign up failed, Try again")
                }
            }
    }

}