package com.example.comparepharma.repository

import com.example.comparepharma.data.Pharma
import java.net.URL

class Repository {
    private val pharmas: List<Pharma>

    init {
        pharmas = listOf(
            Pharma(URL(""), "ОТ ГРИППА И ПРОСТУДЫ ПОРОШОК СО ВКУСОМ ЛИМОНА", 440),
            Pharma(URL(""), "САФИСТОН", 164),
            Pharma(URL(""), "ТЕМПАЛГИН ТРИО", 157),
            Pharma(URL(""), "ПАРАЦЕТАМОЛ", 58),
            Pharma(URL(""), "ПАРАЦЕТАМОЛ", 71),
            Pharma(URL(""), "ПАРАЦЕТАМОЛ", 21),
        )
    }
}