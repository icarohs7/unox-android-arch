package com.github.icarohs7.unoxandroidarch.data.repository

import arrow.effects.IO
import com.github.icarohs7.unoxandroid.extensions.coroutines.onBackground
import com.github.icarohs7.unoxandroid.sideEffectBg
import com.github.icarohs7.unoxandroidarch.data.db.BaseDao
import io.reactivex.Flowable

/**
 * Class implementing the methods from the [BaseRepository] wrapping
 * the corresponding dao methods in a background coroutine
 */
abstract class BaseRepositoryDaoAdapter<T, DAO : BaseDao<T>>(protected val dao: DAO) : BaseRepository<T> {

    /** [BaseRepository.insert] */
    override suspend fun insert(item: T): IO<Long> {
        return sideEffectBg { dao.insert(item) }
    }

    /** [BaseRepository.insertAll] */
    override suspend fun insertAll(items: List<T>): IO<List<Long>> {
        return sideEffectBg { dao.insertAll(items) }
    }

    /** [BaseRepository.update] */
    override suspend fun update(item: T): IO<Int> {
        return sideEffectBg { dao.update(item) }
    }

    /** [BaseRepository.updateAll] */
    override suspend fun updateAll(items: List<T>): IO<Int> {
        return sideEffectBg { dao.updateAll(items) }
    }

    /** [BaseRepository.delete] */
    override suspend fun delete(item: T): IO<Int> {
        return sideEffectBg { dao.delete(item) }
    }

    /** [BaseRepository.deleteAll] */
    override suspend fun deleteAll(items: List<T>): IO<Int> {
        return sideEffectBg { dao.deleteAll(items) }
    }

    /** [BaseRepository.eraseTable] */
    override suspend fun eraseTable(): IO<Unit> {
        return sideEffectBg { dao.eraseTable() }
    }

    /** [BaseRepository.getAll] */
    override suspend fun getAll(): List<T> {
        return onBackground { dao.getAll() }
    }

    /** [BaseRepository.flowable] */
    override fun flowable(): Flowable<List<T>> {
        return dao.flowable()
    }
}