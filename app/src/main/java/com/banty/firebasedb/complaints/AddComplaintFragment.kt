package com.banty.firebasedb.complaints

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.banty.firebasedb.R
import java.util.*

class AddComplaintFragment : Fragment() {

    private lateinit var dateTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_complaint, container, false)
        setAutocompleteAdapter(view.findViewById(R.id.complaint_type_text), R.array.complaint_types)
        setAutocompleteAdapter(view.findViewById(R.id.locality), R.array.locality)
        dateTextView = view.findViewById(R.id.date_layout)
        dateTextView.setOnClickListener { showDatePicker() }
        return view
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
                { view, year, month, dayOfMonth -> setTimeInView(it,dayOfMonth, month, year) },
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
        val dayStr: String = if (dayOfMonth < 10)
            "0$dayOfMonth" else "$dayOfMonth"
        val monthStr: String = if (month < 10) "0$month" else "$month"

        dateTextView.text = context.resources.getString(R.string.date_string, dayStr, monthStr, year.toString())
    }


}