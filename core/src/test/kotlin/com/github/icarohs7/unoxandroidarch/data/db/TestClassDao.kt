package com.github.icarohs7.unoxandroidarch.data.db

import androidx.room.Dao
import androidx.room.Query
import com.github.icarohs7.unoxandroidarch.data.entities.TestClass
import io.reactivex.Flowable

@Dao
interface TestClassDao : BaseDao<TestClass> {
    @Query("DELETE FROM `TestClass`")
    override suspend fun eraseTable()

    @Query("SELECT * FROM `TestClass`")
    suspend fun getAll(): List<TestClass>

    @Query("SELECT * FROM `TestClass` WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TestClass?

    @Query("SELECT * FROM `TestClass`")
    override fun flowable(): Flowable<List<TestClass>>
}