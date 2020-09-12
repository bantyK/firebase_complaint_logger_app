package com.banty.firebasedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.banty.firebasedb.complaints.Complaint
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainFragment : Fragment() {

    private var firebaseAuth: FirebaseAuth? = null

    private lateinit var recylerView: RecyclerView
    private lateinit var progressBar: ProgressBar


    private val complaintsAdapter = ComplaintsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        progressBar = view.findViewById(R.id.progress_circular)
        val addButton: FloatingActionButton = view.findViewById(R.id.add_complaint_fab)
        addButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddComplaintFragment())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
    }


    private fun setupRecyclerView(view: View) {
        recylerView = view.findViewById(R.id.complaints_recycler_view)
        recylerView.layoutManager = LinearLayoutManager(view.context)
        recylerView.setHasFixedSize(true)
        recylerView.adapter = complaintsAdapter
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth?.addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSignInFragment())
            } else {
                val currentUser = auth.currentUser?.uid ?: ""
                FirebaseDatabase.getInstance().reference.child(currentUser)
                    .addValueEventListener(object :
                        ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            // NO OP
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val complaints: MutableList<Complaint> = ArrayList()
                            snapshot.child("complaints").children.forEach { complaint ->
                                val value: Complaint? = complaint.getValue(Complaint::class.java)
                                value?.let { complaints.add(it) }
                            }
                            progressBar.visibility = View.GONE
                            recylerView.visibility = View.VISIBLE
                            complaintsAdapter.updateComplaintList(complaints)
                        }

                    })
            }
        }
    }


}