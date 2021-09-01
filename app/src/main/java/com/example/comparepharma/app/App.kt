package com.example.comparepharma.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.comparepharma.service.Constants
import com.example.comparepharma.room.FavoritesDao
import com.example.comparepharma.room.FavoritesDataBase
import java.lang.IllegalStateException

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        context = applicationContext
    }

    companion object {
        private var appInstance: App? = null
        private var db: FavoritesDataBase? = null
        lateinit var context: Context

        fun getFavoritesDao(): FavoritesDao {
            if (db == null) {
                synchronized(FavoritesDataBase::class.java) {
                    if (db == null) {
                        if (appInstance != null) {
                            db = Room.databaseBuilder(
                                appInstance!!.applicationContext,
                                FavoritesDataBase::class.java,
                                Constants.DB_NAME
                            )
                                .allowMainThreadQueries()
                                .build()
                        } else {
                            throw IllegalStateException("Application is null meanwhile creating database")
                        }
                    }
                }
            }
            return db!!.favoritesDao()
        }
    }
}

interface IContextProvider{
    val context: Context
}

object ContextProvider: IContextProvider{
    override val context: Context
        get() = App.context

}