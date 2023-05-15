package com.example.homework.data.models.model.app

import android.app.Application
import androidx.room.Room
import androidx.room.RoomMasterTable.TABLE_NAME
import com.example.homework.data.models.model.db.Db

class ApplicationDb : Application() {

    companion object {
        private var appInstance: Application? = null
        private var db: Db? = null

        fun dao() {
            checkDb()
            db!!.dao()

        }

        fun checkDb() {
            if (db == null) {
                val builder = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    Db::class.java, TABLE_NAME
                )
                db = builder
                    .allowMainThreadQueries()
                    .build()

            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}
