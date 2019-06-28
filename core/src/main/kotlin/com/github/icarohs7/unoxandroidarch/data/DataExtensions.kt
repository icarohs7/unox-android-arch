package com.github.icarohs7.unoxandroidarch.data

import arrow.core.Tuple2
import arrow.core.Tuple3
import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import com.github.icarohs7.unoxandroidarch.data.db.BaseDao
import com.github.icarohs7.unoxcore.extensions.plus
import io.reactivex.Flowable

/**
 * [Flowable.combineLatest] using
 * the flowables of 2 repositories
 */
fun <A, B> combineDaoFlowables(
        r1: BaseDao<A>,
        r2: BaseDao<B>
): Flowable<Tuple2<List<A>, List<B>>> {
    return f(r1) + f(r2)
}

/**
 * [Flowable.combineLatest] using
 * the flowables of 3 repositories
 */
fun <A, B, C> combineDaoFlowables(
        r1: BaseDao<A>,
        r2: BaseDao<B>,
        r3: BaseDao<C>
): Flowable<Tuple3<List<A>, List<B>, List<C>>> {
    return f(r1) + f(r2) + f(r3)
}

/**
 * [Flowable.combineLatest] using
 * the flowables of 4 repositories
 */
fun <A, B, C, D> combineDaoFlowables(
        r1: BaseDao<A>,
        r2: BaseDao<B>,
        r3: BaseDao<C>,
        r4: BaseDao<D>
): Flowable<Tuple4<List<A>, List<B>, List<C>, List<D>>> {
    return f(r1) + f(r2) + f(r3) + f(r4)
}

/**
 * [Flowable.combineLatest] using
 * the flowables of 5 repositories
 */
fun <A, B, C, D, E> combineDaoFlowables(
        r1: BaseDao<A>,
        r2: BaseDao<B>,
        r3: BaseDao<C>,
        r4: BaseDao<D>,
        r5: BaseDao<E>
): Flowable<Tuple5<List<A>, List<B>, List<C>, List<D>, List<E>>> {
    return f(r1) + f(r2) + f(r3) + f(r4) + f(r5)
}

/**
 * [Flowable.combineLatest] using
 * the flowables of 6 repositories
 */
fun <A, B, C, D, E, F> combineDaoFlowables(
        r1: BaseDao<A>,
        r2: BaseDao<B>,
        r3: BaseDao<C>,
        r4: BaseDao<D>,
        r5: BaseDao<E>,
        r6: BaseDao<F>
): Flowable<Tuple6<List<A>, List<B>, List<C>, List<D>, List<E>, List<F>>> {
    return f(r1) + f(r2) + f(r3) + f(r4) + f(r5) + f(r6)
}

/**
 * Short hand syntax to get the flowable of the
 * given repository
 */
private fun <T> f(dao: BaseDao<T>): Flowable<List<T>> = dao.flowable()