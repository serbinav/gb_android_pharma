package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.MedicineCost

//открытый класс, по default final
open class RepositoryOpenImpl : Repository {
    override fun getPharmaFromServer(): List<MedicineCost> {
        TODO("Not yet implemented")
    }

    override fun getPharmaFromLocalAptekaRu(): List<MedicineCost> {
        TODO("Not yet implemented")
    }

    override fun getPharmaFromLocalAptekaApril(): List<MedicineCost> {
        TODO("Not yet implemented")
    }
}