package com.banty.firebasedb.complaints

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.IllegalArgumentException
import java.util.*

class AddComplaintViewModel : ViewModel() {

    private val _invalidInput = MutableLiveData<Boolean>(false)
    val invalidInput: LiveData<Boolean>
        get() = _invalidInput

    private val _dataPosted = MutableLiveData<Boolean>(false)
    val dataPosted: LiveData<Boolean>
        get() = _dataPosted

    private val _dataPostError = MutableLiveData<Boolean>(false)
    val dataPostError: LiveData<Boolean>
        get() = _dataPostError


    private val valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            _dataPosted.value = true
        }

        override fun onCancelled(databaseError: DatabaseError) {
            _dataPostError.value = true
        }
    }

    fun submitComplaint(complaint: Complaint) {
        if (invalidInput(complaint)) {
            _invalidInput.value = true
        } else {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val databaseRef = FirebaseDatabase.getInstance().reference
                databaseRef.addValueEventListener(valueEventListener)

                databaseRef.child(currentUser.uid).child("complaints")
                    .child(System.currentTimeMillis().toString()).setValue(complaint)
            }
        }
    }


    private fun invalidInput(complaint: Complaint): Boolean {
        return complaint.firstname.isNullOrEmpty()
                || complaint.lastname.isNullOrEmpty()
                || complaint.flatno.isNullOrEmpty()
                || complaint.description.isNullOrEmpty()
                || complaint.complaintType.isNullOrEmpty()
                || complaint.locality.isNullOrEmpty()
                || complaint.date.isNullOrEmpty()
    }

    fun invalidHandled() {
        _invalidInput.value = false
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddComplaintViewModel::class.java)) {
                return AddComplaintViewModel() as T
            }
            throw IllegalArgumentException("View model cannot be instantiated")
        }

    }
}