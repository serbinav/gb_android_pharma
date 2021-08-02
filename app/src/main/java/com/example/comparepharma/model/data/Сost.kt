package com.example.comparepharma.model.data

data class Cost constructor(
    var medicament: Medicament,
    var price: Int = 100
) {
    var count: Int = 0
        get() {
            registrator()
            return field
        }
        set(value) {
            registrator()
            field = value
        }

    constructor(medicament: Medicament, price: Int, count: Int) : this(medicament, price){
        this.count = count
    }

    fun registrator(): Unit {

    }
}

