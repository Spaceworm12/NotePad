package com.example.homework.data.models.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework.data.models.model.db.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = true)
abstract class Db : RoomDatabase() {
    abstract fun dao(): Dao
}
