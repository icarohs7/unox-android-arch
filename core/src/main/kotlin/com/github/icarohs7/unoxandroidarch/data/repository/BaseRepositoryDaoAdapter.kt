package com.github.icarohs7.unoxandroidarch.data.repository

import arrow.core.Try
import com.github.icarohs7.unoxandroidarch.data.db.BaseDao
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import com.github.icarohs7.unoxcore.tryBg
import io.reactivex.Flowable

/**
 * Class implementing the methods from the [BaseRepository] wrapping
 * the corresponding dao methods in a background coroutine
 */
abstract class BaseRepositoryDaoAdapter<T, DAO : BaseDao<T>>(val dao: DAO) : BaseRepository<T> {

    /** [BaseRepository.insert] */
    override suspend fun insert(item: T): Try<Long> {
        return tryBg { dao.insert(item) }
    }

    /** [BaseRepository.insertAll] */
    override suspend fun insertAll(items: List<T>): Try<List<Long>> {
        return tryBg { dao.insertAll(items) }
    }

    /** [BaseRepository.update] */
    override suspend fun update(item: T): Try<Int> {
        return tryBg { dao.update(item) }
    }

    /** [BaseRepository.updateAll] */
    override suspend fun updateAll(items: List<T>): Try<Int> {
        return tryBg { dao.updateAll(items) }
    }

    /** [BaseRepository.delete] */
    override suspend fun delete(item: T): Try<Int> {
        return tryBg { dao.delete(item) }
    }

    /** [BaseRepository.deleteAll] */
    override suspend fun deleteAll(items: List<T>): Try<Int> {
        return tryBg { dao.deleteAll(items) }
    }

    /** [BaseRepository.eraseTable] */
    override suspend fun eraseTable(): Try<Unit> {
        return tryBg { dao.eraseTable() }
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