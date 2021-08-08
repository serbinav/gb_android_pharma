package com.example.comparepharma.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicineCost constructor(
    var medicament: Medicine,
    var price: Int = 100
) : Parcelable {
    var count: Int = 0
        get() {
            registrator()
            return field
        }
        set(value) {
            registrator()
            field = value
        }

    constructor(medicament: Medicine, price: Int, count: Int) : this(medicament, price) {
        this.count = count
    }

    fun registrator(): Unit {

    }
}

