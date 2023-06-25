package com.example.homework.data.models.model.app

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.example.homework.data.models.model.db.Dao
import com.example.homework.data.models.model.db.Db
import com.example.homework.data.models.model.db.entity.TABLE_NOTES

class DbNotes : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: DbNotes? = null
        private var db: Db? = null
        private var sharedPreferences: SharedPreferences? = null

        fun dao(): Dao {
            checkDb()
            return db!!.dao()
        }

        fun getDb(): Db {
            checkDb()
            return db!!
        }

        private fun checkDb() {
            if (db == null) {
                val builder = Room.databaseBuilder(
                    appInstance!!.applicationContext,
                    Db::class.java, TABLE_NOTES
                )
                db = builder
                    .allowMainThreadQueries()
                    .build()
            }
        }
        fun getSettings(): SharedPreferences {
            if (sharedPreferences == null) {
                sharedPreferences =
                    appInstance!!.applicationContext.getSharedPreferences("THEME", MODE_PRIVATE)
            }
            return sharedPreferences!!
        }
    }
}
