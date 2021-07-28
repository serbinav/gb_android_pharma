package com.example.comparepharma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.repository.RepositorySingle
import com.google.android.material.button.MaterialButton
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerPharma = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = PharmaAdapter.getInstance(RepositorySingle)
        recyclerPharma.adapter = adapter

        val button = findViewById<MaterialButton>(R.id.button_search)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажата кнопка", Toast.LENGTH_SHORT).show()
            }
        })
    }
}