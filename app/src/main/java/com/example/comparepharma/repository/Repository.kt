package com.example.comparepharma.repository

import com.example.comparepharma.data.Pharma

//открытый класс, по default final
open class Repository : IRepository {
    override fun getPharmas(): List<Pharma> {
        TODO("Not yet implemented")
    }
}

interface IRepository {
    fun getPharmas(): List<Pharma>
}

//Singletone ближе всего static
object RepositorySingle : IRepository {

    private val pharma = Pharma("", "ИФИМОЛ", 90)

    private val pharmaCopy = pharma.copy(price = 99, name = "ПАНАДОЛ")

    private val pharmas: List<Pharma> = listOf(
        Pharma(name = "ОТ ГРИППА И ПРОСТУДЫ ПОРОШОК СО ВКУСОМ ЛИМОНА", price = 440),
        Pharma(name = "САФИСТОН", price = 164),
        Pharma(name = "ТЕМПАЛГИН ТРИО", price = 157),
        Pharma(name = "ПАРАЦЕТАМОЛ", price = 58),
        Pharma(name = "ПАРАЦЕТАМОЛ", price = 71),
        Pharma(name = "ПАРАЦЕТАМОЛ", price = 21),
        Pharma(name = "ПАРАЦЕТАМОЛ-ЭКО"),
        pharma,
        pharmaCopy
    )

    override fun getPharmas(): List<Pharma> {
        return pharmas
    }
}