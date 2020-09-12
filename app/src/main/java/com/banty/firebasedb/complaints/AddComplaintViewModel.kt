package com.banty.firebasedb.complaints

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AddComplaintViewModel : ViewModel() {

    private val _invalidInput = MutableLiveData<Boolean>()
    val invalidInput: LiveData<Boolean>
        get() = _invalidInput

    fun submitComplaint(
        firstname: String,
        lastname: String,
        flatno: String,
        description: String,
        complaintType: String,
        locality: String,
        date: String
    ) {
        if (invalidInput(firstname, lastname, flatno, description, complaintType, locality, date)) {
            _invalidInput.value = true
        } else {
            // store it in firebase
        }
    }

    private fun invalidInput(
        firstname: String,
        lastname: String,
        flatno: String,
        description: String,
        complaintType: String,
        locality: String,
        date: String
    ): Boolean {
        return firstname.isNullOrEmpty()
                || lastname.isNullOrEmpty()
                || flatno.isNullOrEmpty()
                || description.isNullOrEmpty()
                || complaintType.isNullOrEmpty()
                || locality.isNullOrEmpty()
                || date.isNullOrEmpty()
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