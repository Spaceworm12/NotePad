package com.example.homework.data.models.model.app

import android.app.Application
import androidx.room.Room
import androidx.room.RoomMasterTable.TABLE_NAME
import com.example.homework.data.models.model.db.Dao
import com.example.homework.data.models.model.db.Db

class ApplicationDb : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: ApplicationDb? = null
        private var db: Db? = null

        fun dao(): Dao {
            checkDb()
            return db!!.dao()
        }

        private fun checkDb() {
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


}
