package com.example.comparepharma.model.data

import com.example.comparepharma.model.dto.SearchAprilDTO
import com.example.comparepharma.view.DOSAGE
import com.example.comparepharma.view.RELEASE_FORM
import com.example.comparepharma.view.VENDOR

fun convertDtoToModel(searchAprilDTO: SearchAprilDTO): List<MedicineCost> {
    searchAprilDTO.apply {
        val releaseFormDTO = description.first { it?.typeID == RELEASE_FORM }?.description
        val dosageDTO = properties.first { it?.typeID == DOSAGE }?.name
        val vendorDTO = properties.first { it?.typeID == VENDOR }?.name
        val price = price?.withoutCard
        if (name != null &&
            releaseFormDTO != null &&
            dosageDTO != null &&
            vendorDTO != null &&
            price != null
        ) {
            return listOf(
                MedicineCost(
                    Medicine(
                        id = "1",
                        tradeName = name!!,
                        drugOrRecipet = false,
                        releaseForm = description.first { it?.typeID == RELEASE_FORM }?.description!!,
                        vendor = properties.first { it?.typeID == VENDOR }?.name!!,
                        dosage = properties.first { it?.typeID == DOSAGE }?.name!!
                    ), price.toString()
                )
            )
        }
    }
    return listOf(MedicineCost())
}
