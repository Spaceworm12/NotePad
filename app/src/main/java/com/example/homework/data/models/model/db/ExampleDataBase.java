package com.example.homework.data.models.model.db;


import androidx.room.Database;

@Database(entities = [ExampleEntity::class], version = 1, exportSchema = true)
abstract class ExampleDataBase: RoomDatabase(){
abstract fun exampleDao():ExampleDao
}

