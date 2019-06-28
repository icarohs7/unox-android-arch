package com.github.icarohs7.unoxandroidarch.data.db

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
    suspend fun insert(item: T): Long

    /**
     * Insert a collection of items
     * in the given table
     * @return The list of rowIds
     *      of the affected items
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<T>): List<Long>

    /**
     * Update an item registered
     * in the given table
     * @return The number of affected
     *      rows
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: T): Int

    /**
     * Update a collection of items
     * from the given table
     * @return The number of affected
     *      rows
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(items: List<T>): Int

    /**
     * Remove an item from the given table
     * @return The number of affected
     *      rows
     */
    @Delete
    suspend fun delete(item: T): Int

    /**
     * Remove a collection of items
     * from the given table
     * @return The number of affected
     *      rows
     */
    @Delete
    suspend fun deleteAll(items: List<T>): Int

    /**
     * Remove all items from the given table
     */
    suspend fun eraseTable(): Unit = Unit

    /**
     * Get a flowable emitting the
     * latest values from the given table
     */
    fun flowable(): Flowable<List<T>> = Flowable.empty()
}