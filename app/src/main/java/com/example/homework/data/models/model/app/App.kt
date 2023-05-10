package com.example.homework.data.models.model.app

import android.app.Application
import androidx.room.Room
import androidx.room.RoomMasterTable.TABLE_NAME
import com.example.homework.data.models.model.db.DaoExample
import com.example.homework.data.models.model.db.DataBaseExample

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: Application? = null
        private var db: DataBaseExample? = null

        fun getExampleDao(): DaoExample {
            checkDb()
            return db!!.daoExample()
        }

        private fun checkDb() {
            if (db == null) {
                val builder = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    DataBaseExample::class.java,
                    TABLE_NAME
                )
                db = builder
                    .allowMainThreadQueries()
                    .build()
            }
        }

    }
}