package com.example.homework.data.models.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework.data.models.model.db.entity.EntityExample


@Database(entities = [EntityExample::class], version = 1, exportSchema = true)

abstract class DataBaseExample : RoomDatabase() {
    abstract fun daoExample(): DaoExample
}