package com.github.icarohs7.unoxandroidarch.data.db

import androidx.room.Dao
import androidx.room.Query
import com.github.icarohs7.unoxandroidarch.data.entities.TestClass
import io.reactivex.Flowable

@Dao
interface TestDao : BaseDao<TestClass> {
    @Query("DELETE FROM TestClass")
    override fun eraseTable()

    @Query("SELECT * FROM TestClass")
    override fun getAll(): List<TestClass>

    @Query("SELECT * FROM TestClass")
    override fun flowable(): Flowable<List<TestClass>>
}