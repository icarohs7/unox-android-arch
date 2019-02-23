package com.github.icarohs7.unoxandroidarch.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Flowable

/** Base dao class with insert and delete methods */
interface BaseDao<T> {
    /**
     * Insert an item in the given table
     * @return The rowId of the
     *      affected item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T): Long

    /**
     * Insert a collection of items
     * in the given table
     * @return The list of rowIds
     *      of the affected items
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<T>): List<Long>

    /**
     * Update an item registered
     * in the given table
     * @return The number of affected
     *      rows
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: T): Int

    /**
     * Update a collection of items
     * from the given table
     * @return The number of affected
     *      rows
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(items: List<T>): Int

    /**
     * Remove an item from the given table
     * @return The number of affected
     *      rows
     */
    @Delete
    fun delete(item: T): Int

    /**
     * Remove a collection of items
     * from the given table
     * @return The number of affected
     *      rows
     */
    @Delete
    fun deleteAll(items: List<T>): Int

    /**
     * Remove all items from the given table
     */
    fun eraseTable(): Unit = Unit

    /**
     * Get all items stored in the
     * given table
     */
    fun getAll(): List<T> = emptyList()

    /**
     * Get a livedata emitting the
     * latest values from the given table
     */
    fun liveData(): LiveData<List<T>> = MutableLiveData()

    /**
     * Get a flowable emitting the
     * latest values from the given table
     */
    fun flowable(): Flowable<List<T>> = Flowable.empty()
}