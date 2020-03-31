package com.naez.domain

data class Space(
    val id: Int,
    val title: String,
    val image: String,
    val location: String,
    val typeLivingPlace: String,
    val roomsNumber: Int,
    val description: String,
    val amount: Int,
    val amountCurrency: String,
    val latitude: String,
    val longitude: String,
    val favorite: Boolean
)