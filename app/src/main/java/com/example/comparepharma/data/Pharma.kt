package com.example.comparepharma.data

data class Pharma constructor(var img: String = "", var name: String, var price: Int = 100) {

    var count: Int = 0
        get() {
            registrator()
            return field
        }
        set(value) {
            registrator()
            field = value
        }

    constructor(img: String, name: String, price: Int, count: Int) : this(img, name, price) {
        this.count = count
    }

    fun registrator(): Unit{

    }
}
