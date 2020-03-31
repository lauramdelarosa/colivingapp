package com.naez.colivingapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Space(
    @PrimaryKey(autoGenerate = true) val id: Int,
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