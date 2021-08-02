package com.example.comparepharma.repository

import com.example.comparepharma.model.data.Medicament
import com.example.comparepharma.model.data.Cost

//открытый класс, по default final
open class Repository : IRepository {
    override fun getPharmas(): List<Cost> {
        TODO("Not yet implemented")
    }
}

interface IRepository {
    fun getPharmas(): List<Cost>
}

//Singletone ближе всего static
object RepositorySingle : IRepository {

    private val pharma = Cost(Medicament("", "ИФИМОЛ"), 90)

    private val pharmaCopy = pharma.copy(Medicament(name = "ПАНАДОЛ"), price = 99)

    private val medicaments: List<Cost> = listOf(
        Cost(Medicament(name = "ОТ ГРИППА И ПРОСТУДЫ ПОРОШОК СО ВКУСОМ ЛИМОНА"), price = 440),
        Cost(Medicament(name = "САФИСТОН"), price = 164),
        Cost(Medicament(name = "ТЕМПАЛГИН ТРИО"), price = 157),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ"), price = 58),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ"), price = 71),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ"), price = 21),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ-ЭКО")),
        pharma,
        pharmaCopy
    )

    override fun getPharmas(): List<Cost> {
        return medicaments
    }
}