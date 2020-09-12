package com.banty.firebasedb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth


class MainFragment : Fragment() {

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        val addButton: FloatingActionButton = view.findViewById(R.id.add_complaint_fab)
        addButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddComplaintFragment())
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth?.addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSignInFragment())
            } else {
                //login
            }
        }
    }


}