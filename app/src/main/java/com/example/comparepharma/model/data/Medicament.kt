package com.example.comparepharma.model.data

data class Medicament constructor(
    var img: String = "",
    var name: String) {

    var count: Int = 0
        get() {
            registrator()
            return field
        }
        set(value) {
            registrator()
            field = value
        }

    constructor(img: String, name: String, count: Int) : this(img, name) {
        this.count = count
    }

    fun registrator(): Unit{

    }
}
