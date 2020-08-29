package com.banty.firebasedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        val navHostFragment = nav_host_fragment as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)

        navController = navHostFragment.navController

        navGraph.startDestination = if (userLoggedIn()) R.id.mainFragment else R.id.authFragment
        navController?.graph = navGraph

    }

    override fun onStart() {
        super.onStart()
    }

    private fun userLoggedIn(): Boolean {
        val currentUser = mAuth?.currentUser
        return currentUser != null
    }
}