package com.banty.firebasedb.complaints

data class Complaint(
    val firstname: String,
    val lastname: String,
    val flatno: String,
    val complaintType: String,
    val description: String,
    val locality: String,
    val date: String
)