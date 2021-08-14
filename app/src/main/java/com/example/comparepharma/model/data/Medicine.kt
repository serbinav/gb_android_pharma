package com.example.comparepharma.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Medicine constructor(
    var id: String,
    var photo: String = "",
    var tradeName: String,
    var drugOrRecipet: Boolean,
    var releaseForm: String,
    var vendor: String,
    var dosage: String
) : Parcelable