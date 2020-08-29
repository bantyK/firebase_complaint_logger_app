package com.banty.firebasedb.auth

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.banty.firebasedb.R
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {
    private var alreadyMemberText: TextView? = null

    private val firebaseAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        alreadyMemberText = view.findViewById(R.id.text_already_member)
        setupAlreadyMemberText(alreadyMemberText)
        return view
    }

    private fun setupAlreadyMemberText(alreadyMemberText: TextView?) {
        alreadyMemberText?.text = Html.fromHtml(getString(R.string.already_member))
        alreadyMemberText?.setOnClickListener {
            findNavController().navigate(SignupFragmentDirections.actionAuthFragmentToSignInFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}