package com.example.comparepharma.model.repository

import com.example.comparepharma.model.data.Cost

//открытый класс, по default final
open class RepositoryOpenImpl : Repository {
    override fun getPharmaFromServer(): List<Cost> {
        TODO("Not yet implemented")
    }

    override fun getPharmaFromLocal(): List<Cost> {
        TODO("Not yet implemented")
    }
}