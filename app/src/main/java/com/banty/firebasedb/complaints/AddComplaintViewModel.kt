package com.banty.firebasedb.complaints

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.IllegalArgumentException
import java.util.*

class AddComplaintViewModel : ViewModel() {

    private val _invalidInput = MutableLiveData<Boolean>()
    val invalidInput: LiveData<Boolean>
        get() = _invalidInput

    fun submitComplaint(complaint: Complaint) {
        if (invalidInput(complaint)) {
            _invalidInput.value = true
        } else {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val databaseRef = FirebaseDatabase.getInstance().reference
                databaseRef.child(currentUser.uid).child("complaints").child(UUID.randomUUID().toString()).setValue(complaint)
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