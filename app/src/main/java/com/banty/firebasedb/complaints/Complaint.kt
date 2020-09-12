package com.banty.firebasedb.complaints

data class Complaint(
    val username: String,
    val flatNo: String,
    val complaintType: String,
    val description: String,
    val location: String,
    val date: String
)