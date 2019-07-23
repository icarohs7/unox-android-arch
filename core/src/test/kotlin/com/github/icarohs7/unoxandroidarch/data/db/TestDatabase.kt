package com.github.icarohs7.unoxandroidarch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.icarohs7.unoxandroidarch.data.entities.TestClass

@Database(entities = [TestClass::class], version = 1)
abstract class TestDatabase : RoomDatabase() {
    abstract fun testDao(): TestClassDao
}