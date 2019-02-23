package com.github.icarohs7.unoxandroidarch.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/** Helper method used to create a RoomDatabase instance */
inline fun <reified T : RoomDatabase> Context.buildDatabase(): RoomDatabase.Builder<T> {
    return Room
            .databaseBuilder(this, T::class.java, "app.db")
            .fallbackToDestructiveMigration()
}