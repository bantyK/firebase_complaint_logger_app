package com.banty.firebasedb.complaints

class Complaint constructor(
    val firstname: String,
    val lastname: String,
    val flatno: String,
    val complaintType: String,
    val description: String,
    val locality: String,
    val date: String
) {
    // required for firebase database
    constructor() : this("", "", "", "", "", "", "")

}