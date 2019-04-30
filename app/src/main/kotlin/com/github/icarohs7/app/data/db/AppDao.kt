package com.github.icarohs7.app.data.db

import androidx.room.Dao
import androidx.room.Query
import com.github.icarohs7.app.data.entities.Person
import com.github.icarohs7.unoxandroidarch.data.db.BaseDao
import io.reactivex.Flowable

@Dao
interface PersonDao : BaseDao<Person> {
    @Query("DELETE FROM Person")
    override fun eraseTable()

    @Query("SELECT * FROM Person")
    override fun getAll(): List<Person>

    @Query("SELECT * FROM Person")
    override fun flowable(): Flowable<List<Person>>
}