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

    private val pharmas: List<Pharma> = listOf(
        Pharma("", "ОТ ГРИППА И ПРОСТУДЫ ПОРОШОК СО ВКУСОМ ЛИМОНА", 440),
        Pharma("", "САФИСТОН", 164),
        Pharma("", "ТЕМПАЛГИН ТРИО", 157),
        Pharma("", "ПАРАЦЕТАМОЛ", 58),
        Pharma("", "ПАРАЦЕТАМОЛ", 71),
        Pharma("", "ПАРАЦЕТАМОЛ", 21),
    )

    override fun getPharmas(): List<Pharma> {
        return pharmas
    }
}