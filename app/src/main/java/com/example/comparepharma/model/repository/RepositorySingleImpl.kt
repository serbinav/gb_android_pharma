package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.*

//Singleton ближе всего к static
object RepositorySingleImpl : Repository {

    private val pharma = MedicineCost(
        Medicine(
            "",
            "ИФИМОЛ",
            drugOrRecipe = false,
            releaseForm = "таблетки",
            vendor = "ОЗОН,ООО",
            dosage = "0.5"
        ), "90"
    )

    private val pharmaCopy = pharma.copy(
        medicament = Medicine(
            tradeName = "ПАНАДОЛ",
            drugOrRecipe = false,
            releaseForm = "таблетки",
            vendor = "ОЗОН,ООО",
            dosage = "0.5"
        ), price = "99"
    )

    private val medicament: List<MedicineCost> = listOf(
        MedicineCost(
            Medicine(
                tradeName = "ОТ ГРИППА И ПРОСТУДЫ ПОРОШОК СО ВКУСОМ ЛИМОНА",
                drugOrRecipe = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "440"
        ),
        MedicineCost(
            Medicine(
                tradeName = "САФИСТОН",
                drugOrRecipe = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "164"
        ),
        MedicineCost(
            Medicine(
                tradeName = "ТЕМПАЛГИН ТРИО",
                drugOrRecipe = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "157"
        ),
        MedicineCost(
            Medicine(
                tradeName = "ПАРАЦЕТАМОЛ",
                drugOrRecipe = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "58"
        ),
        MedicineCost(
            Medicine(
                tradeName = "ПАРАЦЕТАМОЛ",
                drugOrRecipe = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "71"
        ),
        MedicineCost(
            Medicine(
                tradeName = "ПАРАЦЕТАМОЛ",
                drugOrRecipe = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            ), price = "21"
        ),
        MedicineCost(
            Medicine(
                tradeName = "ПАРАЦЕТАМОЛ-ЭКО",
                drugOrRecipe = false,
                releaseForm = "таблетки",
                vendor = "ОЗОН,ООО",
                dosage = "0.5"
            )
        ),
        pharma,
        pharmaCopy
    )

    //Пока загрузка с сервера равна локальной загрузке
    override fun getPharmaFromServer() = medicament
    override fun getPharmaFromLocalAptekaRu() = getAptekaRuPharma()
    override fun getPharmaFromLocalAptekaApril() = getAptekaAprilPharma()
}