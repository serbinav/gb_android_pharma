package com.example.comparepharma.model.data

import com.example.comparepharma.utils.Constants
import com.example.comparepharma.model.dto.SearchAprilDTO
import com.example.comparepharma.room.FavoritesEntity

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
                        dosage = dosageDTO,
                        photo = this.images.first() ?: ""
                    ), price.toString()
                )
            )
        }
    }
    return listOf(MedicineCost())
}

fun convertFavoritesEntityToMedicineCost(entityList: List<FavoritesEntity>): List<MedicineCost> {
    return entityList.map {
        MedicineCost(
            Medicine(
                id = it.medicineId,
                tradeName = it.tradeName,
                drugOrRecipet = false,
                releaseForm = "",
                vendor = "",
                dosage = "",
            ),
            it.price
        )
    }
}

fun convertMedicineCostToEntity(medicineCost: MedicineCost): FavoritesEntity {
    return FavoritesEntity(
        0,
        medicineCost.medicament.id,
        medicineCost.medicament.tradeName,
        medicineCost.price
    )
}