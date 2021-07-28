package com.example.comparepharma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.repository.RepositorySingle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerPharma = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = PharmaAdapter(RepositorySingle)
        recyclerPharma.adapter = adapter
    }
}