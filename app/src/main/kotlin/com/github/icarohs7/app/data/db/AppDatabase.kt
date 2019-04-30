package com.github.icarohs7.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.icarohs7.app.data.entities.Person

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}