package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.Cost
import com.example.comparepharma.repository.Repository

//открытый класс, по default final
open class RepositoryOpenImpl : Repository {
    override fun getPharmasFromServer(): List<Cost> {
        TODO("Not yet implemented")
    }

    override fun getPharmasFromLocal(): List<Cost> {
        TODO("Not yet implemented")
    }
}