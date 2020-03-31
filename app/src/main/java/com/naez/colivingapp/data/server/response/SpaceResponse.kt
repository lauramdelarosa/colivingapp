package com.naez.colivingapp.data.server.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class SpaceResult(
    val page: Int,
    val results: List<Space>,
    val totalPages: Int,
    val totalResults: Int
)

@Parcelize
data class Space(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("release_date") val releaseDate: String,
    val image: String,
    val location: String,
    val latitude: String,
    val longitude: String,
    @SerializedName("type_living_place") val typeLivingPlace: String,
    val roomsNumber: Int,
    val amount: Int,
    val amountCurrency: String,
    val favorite: Boolean
) : Parcelable