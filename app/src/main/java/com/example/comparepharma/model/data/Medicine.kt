package com.example.comparepharma.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Medicine constructor(
    //id возможно придется добавить
    var photo: String = "",
    var tradeName: String,
    var drugOrRecipe: Boolean,
    var releaseForm: String,
    var vendor: String,
    var dosage: String
) : Parcelable