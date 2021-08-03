package com.example.comparepharma.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.example.comparepharma.databinding.MainActivityBinding
import com.example.comparepharma.model.data.Cost
import com.example.comparepharma.model.repository.RepositorySingleImpl
import java.util.*

const val PARACETAMOL: String = "ПАРАЦЕТАМОЛ"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: MaterialButton = binding.buttonSearch

        button.setOnClickListener { it ->
            val data: List<Cost> = RepositorySingleImpl.getPharmasFromLocal()

            for (i in data.indices) {
                when (data[i].medicament.name) {
                    PARACETAMOL -> {
                        Toast.makeText(
                            this@MainActivity,
                            data[i].medicament.name + " №" + i,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            data[i].medicament.name,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }
}


//  С этого занятия все практические задания относятся к приложению для поиска фильмов.
//  Если вы выбрали проект погодного приложения, которое описывается на занятии,
//  то потребуется добавить собственные улучшения и оптимизации.
//  Потребуется больше внимания уделять задачам для дополнительного обучения,
//  так как код приложения у вас уже есть.
//  Вы уже изучили API базы данных с фильмами и примерно представляете, каким будет приложение.
//  Создайте проект вашего приложения. Скриншот ниже даётся в качестве возможного примера.
//  Картинки можно не отображать, мы научимся скачивать их в ближайшее время,
//  а в остальном — заполните всё заглушками. Массивы и коллекции применять необязательно,
//  о них мы поговорим на следующем занятии.
//Главная задача — выстроить архитектуру MVVM для будущего приложения,
//создать заглушки для экранов и переходы между ними.