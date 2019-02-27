package com.github.icarohs7.unoxandroidarch.data.repository

import arrow.effects.IO
import io.reactivex.Flowable

/**
 * Base Repository with methods related
 * to a Room Database Dao
 */
interface BaseRepository<T> {
    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.insert] */
    suspend fun insert(item: T): IO<Long> = IO.just(0L)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.insertAll] */
    suspend fun insertAll(items: List<T>): IO<List<Long>> = IO.just(emptyList())

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.insertAll] */
    suspend fun insertAll(vararg items: T): IO<List<Long>> = insertAll(items.toList())

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.update] */
    suspend fun update(item: T): IO<Int> = IO.just(0)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.updateAll] */
    suspend fun updateAll(items: List<T>): IO<Int> = IO.just(0)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.updateAll] */
    suspend fun updateAll(vararg items: T): IO<Int> = updateAll(items.toList())

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.delete] */
    suspend fun delete(item: T): IO<Int> = IO.just(0)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.deleteAll] */
    suspend fun deleteAll(items: List<T>): IO<Int> = IO.just(0)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.deleteAll] */
    suspend fun deleteAll(vararg items: T): IO<Int> = deleteAll(items.toList())

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.eraseTable] */
    suspend fun eraseTable(): IO<Unit> = IO.unit

    /** Filtered elements from [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.getAll] */
    suspend fun get(matcher: (T) -> Boolean): T? = getAll().find(matcher)

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.getAll] */
    suspend fun getAll(): List<T> = emptyList()

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.flowable] */
    fun flowable(): Flowable<List<T>> = Flowable.empty()

    /** [com.github.icarohs7.unoxandroidarch.data.db.BaseDao.flowable] */
    fun <R : Any> flowable(transformer: List<T>.() -> R): Flowable<R> = flowable().map(transformer)

    companion object
}