package com.github.icarohs7.app.data.db

import androidx.room.Dao
import androidx.room.Query
import com.github.icarohs7.app.data.entities.Person
import com.github.icarohs7.unoxandroidarch.data.db.BaseDao
import io.reactivex.Flowable

@Dao
interface PersonDao : BaseDao<Person> {
    @Query("DELETE FROM `Person`")
    override suspend fun eraseTable()

    @Query("SELECT * FROM `Person`")
    suspend fun getAll(): List<Person>

    @Query("SELECT * FROM `Person` WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Person?

    @Query("SELECT * FROM `Person`")
    override fun flowable(): Flowable<List<Person>>
}