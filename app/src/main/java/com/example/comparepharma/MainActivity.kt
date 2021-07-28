package com.example.comparepharma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.data.Pharma
import com.example.comparepharma.repository.RepositorySingle
import com.google.android.material.button.MaterialButton
import java.util.*

const val PARACETAMOL: String = "ПАРАЦЕТАМОЛ"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerPharma = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = PharmaAdapter.getInstance(RepositorySingle)
        recyclerPharma.adapter = adapter

        val button = findViewById<MaterialButton>(R.id.button_search)

        button.setOnClickListener { it ->
            val data: List<Pharma> = RepositorySingle.getPharmas()

            for (i in data.indices) {
                when (data[i].name) {
                    PARACETAMOL -> {
                        Toast.makeText(
                            this@MainActivity,
                            data[i].name + " №" + i,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            data[i].name,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}