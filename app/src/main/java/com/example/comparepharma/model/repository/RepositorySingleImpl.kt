package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.*

//Singleton ближе всего к static
object RepositorySingleImpl : Repository {

    private val pharmaCopy = MedicineCost(
        getDefaultMedicine().copy(), price = "99"
    )

    private val medicament: List<MedicineCost> = listOf(
        MedicineCost(
            Medicine(
                id = "3",
                tradeName = "ОТ ГРИППА И ПРОСТУДЫ ПОРОШОК СО ВКУСОМ ЛИМОНА",
                drugOrRecipet = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "440"
        ),
        MedicineCost(
            Medicine(
                id = "4",
                tradeName = "САФИСТОН",
                drugOrRecipet = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "164"
        ),
        MedicineCost(
            Medicine(
                id = "5",
                tradeName = "ТЕМПАЛГИН ТРИО",
                drugOrRecipet = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "157"
        ),
        MedicineCost(
            Medicine(
                id = "6",
                tradeName = "ПАРАЦЕТАМОЛ",
                drugOrRecipet = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "58"
        ),
        MedicineCost(
            Medicine(
                id = "7",
                tradeName = "ПАРАЦЕТАМОЛ",
                drugOrRecipet = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "71"
        ),
        MedicineCost(
            Medicine(
                id = "8",
                tradeName = "ПАРАЦЕТАМОЛ",
                drugOrRecipet = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "21"
        ),
        MedicineCost(
            Medicine(
                id = "9",
                tradeName = "ПАРАЦЕТАМОЛ-ЭКО",
                drugOrRecipet = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            )
        ),
        pharmaCopy
    )

    //Пока загрузка с сервера равна локальной загрузке
    override fun getPharmaFromServer() = medicament
    override fun getPharmaFromLocalAptekaRu() = getAptekaRuPharma()
    override fun getPharmaFromLocalAptekaApril() = getAptekaAprilPharma()
}