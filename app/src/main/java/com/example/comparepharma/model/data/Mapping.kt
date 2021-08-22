package com.example.comparepharma.model.data

import com.example.comparepharma.model.Constants
import com.example.comparepharma.model.dto.SearchAprilDTO

fun convertDtoToModel(searchAprilDTO: SearchAprilDTO): List<MedicineCost> {
    searchAprilDTO.apply {
        val releaseFormDTO =
            description.first { it?.typeID == Constants.DESCRIPTION_NUMBER_RELEASE_FORM }?.description
        val vendorDTO =
            properties.first { it?.typeID == Constants.PROPERTIES_NUMBER_VENDOR }?.name
        val dosageDTO = properties.first { it?.typeID == Constants.PROPERTIES_NUMBER_DOSAGE }?.name
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
                        tradeName = name,
                        drugOrRecipet = false,
                        releaseForm = releaseFormDTO,
                        vendor = vendorDTO,
                        dosage = dosageDTO
                    ), price.toString()
                )
            )
        }
    }
    return listOf(MedicineCost())
}
