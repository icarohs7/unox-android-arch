package com.github.icarohs7.unoxandroidarch.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/** Helper method used to create a RoomDatabase instance */
inline fun <reified T : RoomDatabase> Context.buildDatabase(): RoomDatabase.Builder<T> {
    return Room
            .databaseBuilder(this, T::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .setQueryExecutor { query -> GlobalScope.launch(Dispatchers.IO) { query.run() } }
}