package com.banty.firebasedb.complaints

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.banty.firebasedb.R
import com.banty.firebasedb.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class AddComplaintFragment : Fragment() {

    private lateinit var dateTextView: TextView
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var flatNumberEditText: EditText
    private lateinit var complaintEditText: EditText
    private lateinit var complaintTypeTextView: AutoCompleteTextView
    private lateinit var localityTextView: AutoCompleteTextView
    private lateinit var submitButton: Button

    private val viewModel: AddComplaintViewModel by lazy {
        ViewModelProviders.of(
            this,
            AddComplaintViewModel.Factory()
        )[AddComplaintViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_complaint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setAutocompleteAdapter(view.findViewById(R.id.complaint_type_text), R.array.complaint_types)
        setAutocompleteAdapter(view.findViewById(R.id.locality), R.array.locality)
        dateTextView.setOnClickListener { showDatePicker() }

        registerObservers()
    }

    override fun onStart() {
        super.onStart()
        FirebaseDatabase.getInstance().reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Tag##", "${snapshot?.child(FirebaseAuth.getInstance().uid!!).value}")
            }
        })
    }

    private fun registerObservers() {
        viewModel.invalidInput.observe(viewLifecycleOwner, androidx.lifecycle.Observer { invalid ->
            if (invalid) {
                showToast("Please fill all the values")
                viewModel.invalidHandled()
            }
        })
    }


    private fun initViews(view: View) {
        dateTextView = view.findViewById(R.id.date_layout)
        firstNameEditText = view.findViewById(R.id.editText_name)
        lastNameEditText = view.findViewById(R.id.editText_lname)
        flatNumberEditText = view.findViewById(R.id.editText_flatno)
        complaintEditText = view.findViewById(R.id.editText_complaint)
        complaintTypeTextView = view.findViewById(R.id.complaint_type_text)
        localityTextView = view.findViewById(R.id.locality)
        submitButton = view.findViewById(R.id.button_submit)

        submitButton.setOnClickListener { submitComplaint() }
    }

    private fun submitComplaint() {
        viewModel.submitComplaint(
            Complaint(
                firstname = firstNameEditText.text.toString(),
                lastname = lastNameEditText.text.toString(),
                flatno = flatNumberEditText.text.toString(),
                description = complaintEditText.text.toString(),
                complaintType = complaintTypeTextView.text.toString(),
                locality = localityTextView.text.toString(),
                date = dateTextView.text.toString()
            )
        )
    }


    private fun setAutocompleteAdapter(
        autoCompleteTextView: AutoCompleteTextView,
        arrayResourceInt: Int
    ) {
        context?.let {
            val complaintTypes = it.resources.getStringArray(arrayResourceInt)
            val adapter: ArrayAdapter<String> = ArrayAdapter(
                it,
                R.layout.dropdown_menu_popup_item,
                complaintTypes
            )
            autoCompleteTextView.setAdapter(adapter)
        }
    }

    private fun showDatePicker() {
        context?.let {
            val datePicker = DatePickerDialog(
                it,
                { view, year, month, dayOfMonth -> setTimeInView(it, dayOfMonth, month, year) },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    private fun setTimeInView(
        context: Context,
        dayOfMonth: Int,
        month: Int,
        year: Int
    ) {
        val dayStr: String = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
        val monthStr: String = if (month < 10) "0$month" else "$month"

        dateTextView.text =
            context.resources.getString(R.string.date_string, dayStr, monthStr, year.toString())
    }


}