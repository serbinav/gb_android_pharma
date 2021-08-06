package com.example.comparepharma.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Medicament constructor(
    var img: String = "",
    var name: String
) : Parcelable