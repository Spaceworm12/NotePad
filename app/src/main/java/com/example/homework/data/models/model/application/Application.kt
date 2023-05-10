package com.example.homework.data.models.model.application

import android.app.Application
import androidx.room.Room
import androidx.room.RoomMasterTable.TABLE_NAME

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: com.example.homework.data.models.model.application.Application? =
            null
        private var db: ExampleDataBase? = null

        fun getExampleDao(): ExampleDao {
            checkDb()
            return db!!.exampleDao()
        }

        private fun checkDb() {
            if (db == null) {
                val builder = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    ExampleDataBase::class.java,
                    TABLE_NAME
                )
                db = builder
                    .allowMainThreadQueries()
                    .build()
            }
        }

    }
}