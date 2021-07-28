package com.example.comparepharma.repository

import com.example.comparepharma.data.Pharma
import java.net.URL

//открытый класс, по default final
open class Repository : IRepository{
    override fun getPharmas(): List<Pharma> {
        TODO("Not yet implemented")
    }
}

interface IRepository{
    fun getPharmas() : List<Pharma>
}

//Singletone ближе всего static
object RepositorySingle : IRepository {

    private val pharmas: List<Pharma> = listOf(
        Pharma(URL(""), "ОТ ГРИППА И ПРОСТУДЫ ПОРОШОК СО ВКУСОМ ЛИМОНА", 440),
        Pharma(URL(""), "САФИСТОН", 164),
        Pharma(URL(""), "ТЕМПАЛГИН ТРИО", 157),
        Pharma(URL(""), "ПАРАЦЕТАМОЛ", 58),
        Pharma(URL(""), "ПАРАЦЕТАМОЛ", 71),
        Pharma(URL(""), "ПАРАЦЕТАМОЛ", 21),
    )

    override fun getPharmas(): List<Pharma> {
        return pharmas
    }
}