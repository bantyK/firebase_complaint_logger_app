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

class SignInFragment : Fragment() {
    private var newMemberText: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        newMemberText = view.findViewById(R.id.text_new_member)
        setupNewMemberText(newMemberText)
        return view
    }

    private fun setupNewMemberText(textView: TextView?) {
        textView?.text = Html.fromHtml(getString(R.string.new_member))
        textView?.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAuthFragment())
        }
    }
}