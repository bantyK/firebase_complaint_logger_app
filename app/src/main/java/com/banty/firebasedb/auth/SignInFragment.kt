package com.banty.firebasedb.auth

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.banty.firebasedb.BaseFragment
import com.banty.firebasedb.R
import com.banty.firebasedb.utils.isValidEmail
import com.banty.firebasedb.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_signup.editText_email
import kotlinx.android.synthetic.main.fragment_signup.editText_password

class SignInFragment : BaseFragment() {
    private var newMemberText: TextView? = null

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        newMemberText = view.findViewById(R.id.text_new_member)
        setupRichText(newMemberText, R.string.new_member) {
            this@SignInFragment.findNavController()
                .navigate(SignInFragmentDirections.actionSignInFragmentToAuthFragment())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_signin.setOnClickListener {
            initSignIn()
        }
    }

    private fun initSignIn() {
        val email: String? = editText_email.text.toString()
        val pass: String? = editText_password.text.toString()

        if (email.isNullOrEmpty() || pass.isNullOrEmpty()) {
            showToast("Please fill in all details")
        } else {
            if (email.isValidEmail()) {
                signin(email, pass)
            } else {
                showToast("Not a valid email address")
            }
        }
    }

    private fun signin(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment())
                } else {
                    Log.w("Tag##", "User creation failed: ${task.exception?.message}")
                    showToast("Sign in failed, Try again")
                }
            }
    }
}