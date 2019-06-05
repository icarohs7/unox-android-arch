package com.github.icarohs7.unoxandroidarch.data.repository

import arrow.core.Try
import io.reactivex.Flowable

/**
 * Base Repository with methods related
 * to a Room Database Dao
 */
interface BaseRepository<T> {
    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.insert] */
    suspend fun insert(item: T): Try<Long> = Try.just(0L)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.insertAll] */
    suspend fun insertAll(items: List<T>): Try<List<Long>> = Try.just(emptyList())

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.insertAll] */
    suspend fun insertAll(vararg items: T): Try<List<Long>> = insertAll(items.toList())

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.update] */
    suspend fun update(item: T): Try<Int> = Try.just(0)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.updateAll] */
    suspend fun updateAll(items: List<T>): Try<Int> = Try.just(0)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.updateAll] */
    suspend fun updateAll(vararg items: T): Try<Int> = updateAll(items.toList())

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.delete] */
    suspend fun delete(item: T): Try<Int> = Try.just(0)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.deleteAll] */
    suspend fun deleteAll(items: List<T>): Try<Int> = Try.just(0)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.deleteAll] */
    suspend fun deleteAll(vararg items: T): Try<Int> = deleteAll(items.toList())

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.eraseTable] */
    suspend fun eraseTable(): Try<Unit> = Try { Unit }

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.getAll] */
    suspend fun getAll(): List<T> = emptyList()

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.flowable] */
    fun flowable(): Flowable<List<T>> = Flowable.empty()

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.flowable] */
    fun <R : Any> flowable(transformer: List<T>.() -> R): Flowable<R> = flowable().map(transformer)

    companion object
}