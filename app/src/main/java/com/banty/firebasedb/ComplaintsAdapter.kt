package com.banty.firebasedb

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.banty.firebasedb.complaints.Complaint

class ComplaintsAdapter : RecyclerView.Adapter<ComplaintsAdapter.ComplaintsViewHolder>() {
    private val complaintsList: MutableList<Complaint> = ArrayList()

    class ComplaintsViewHolder(rowItemView: View) :
        RecyclerView.ViewHolder(rowItemView) {

        val complaintDescriptionView: TextView =
            rowItemView.findViewById(R.id.complaint_description)
        val complaintDateView: TextView = rowItemView.findViewById(R.id.complaint_creation_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.complaint_row_item, parent, false)
        return ComplaintsViewHolder(row)
    }

    fun updateComplaintList(list: List<Complaint>) {
        complaintsList.clear()
        complaintsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = complaintsList.size


    override fun onBindViewHolder(holder: ComplaintsViewHolder, position: Int) {
        holder.complaintDescriptionView.text = complaintsList[position].description
        holder.complaintDateView.text = complaintsList[position].date
    }

}
