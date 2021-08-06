package com.example.comparepharma.model.data

fun getAptekaRuPharma(): List<Cost> {
    return listOf(
        Cost(Medicament(name = "ОТ ГРИППА И ПРОСТУДЫ ПОРОШОК СО ВКУСОМ ЛИМОНА"), price = 540),
        Cost(Medicament(name = "САФИСТОН"), price = 144),
        Cost(Medicament(name = "ТЕМПАЛГИН ТРИО"), price = 134),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ"), price = 58),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ"), price = 70),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ"), price = 62),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ ДЕТСКИЙ"), price = 78),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ ЭКСТРАТАБ"), price = 94),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ ВЕЛФАРМ"), price = 68),
        Cost(Medicament(name = "ПАРАЦЕТАМОЛ-АЛЬТФАРМ"), price = 31),
    )
}

fun getAptekaAprilPharma(): List<Cost> {
    return listOf(
        Cost(Medicament(name = "Парацетамол"), price = 68),
        Cost(Medicament(name = "Парацетамол"), price = 12),
        Cost(Medicament(name = "Парацетамол таблетки шипучие"), price = 140),
        Cost(Medicament(name = "Парацетамол суппозитории ректальные"), price = 20),
        Cost(Medicament(name = "Парацетамол таблетки шипучие стрипы"), price = 140),
        Cost(Medicament(name = "Парацетамол суспензия для приема внутрь"), price = 68),
        Cost(Medicament(name = "Парацетамол суспензия для приема внутрь"), price = 131),
        Cost(Medicament(name = "Парацетамол раствор для внутреннего применения"), price = 82),
        Cost(Medicament(name = "Цефекон Д суппозитории ректальные"), price = 56),
        Cost(Medicament(name = "Панадол суспензия для приема внутрь"), price = 96)
    )
}