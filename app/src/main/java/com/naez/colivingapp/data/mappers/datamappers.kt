package com.naez.colivingapp.data.mappers

import com.naez.domain.Space
import com.naez.colivingapp.data.database.entities.Space as LocalSpace
import com.naez.colivingapp.data.server.response.Space as ServerSpace


fun ServerSpace.toDomainSpace(): Space =
    Space(
        id = 0,
        image = image,
        location = location,
        title = title,
        typeLivingPlace = typeLivingPlace,
        roomsNumber = roomsNumber,
        description = description,
        amount = amount,
        amountCurrency = amountCurrency,
        latitude = latitude,
        longitude = longitude,
        favorite = favorite
    )


fun Space.toRoomSpace(): LocalSpace =
    LocalSpace(
        id = id,
        image = image,
        location = location,
        title = title,
        typeLivingPlace = typeLivingPlace,
        roomsNumber = roomsNumber,
        description = description,
        amount = amount,
        amountCurrency = amountCurrency,
        latitude = latitude,
        longitude = longitude,
        favorite = favorite
    )

fun LocalSpace.toDomainSpace(): Space = Space(
    id = id,
    image = image,
    location = location,
    title = title,
    typeLivingPlace = typeLivingPlace,
    roomsNumber = roomsNumber,
    description = description,
    amount = amount,
    amountCurrency = amountCurrency,
    latitude = latitude,
    longitude = longitude,
    favorite = favorite
)