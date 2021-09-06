package com.example.comparepharma.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicineCost constructor(
    var medicament: Medicine = getDefaultMedicine(),
    var price: String = "100"
) : Parcelable {
    var count: Int = 0

    constructor(medicament: Medicine, price: String, count: Int) : this(medicament, price) {
        this.count = count
    }
}

fun getDefaultMedicine() = Medicine(
    "1",
    "",
    "ИФИМОЛ",
    drugOrRecipet = false,
    releaseForm = "таблетки",
    vendor = "ОЗОН,ООО",
    dosage = "0.5"
)
