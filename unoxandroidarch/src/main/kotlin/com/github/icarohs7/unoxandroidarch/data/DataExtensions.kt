package com.github.icarohs7.unoxandroidarch.data

import arrow.core.Tuple2
import arrow.core.Tuple3
import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import com.github.icarohs7.unoxandroidarch.data.repository.BaseRepository
import com.github.icarohs7.unoxcore.extensions.plus
import io.reactivex.Flowable

/**
 * [Flowable.combineLatest] using
 * the flowables of 2 repositories
 */
fun <A, B> combineRepositoryFlowables(
        r1: BaseRepository<A>,
        r2: BaseRepository<B>
): Flowable<Tuple2<List<A>, List<B>>> {
    return f(r1) + f(r2)
}

/**
 * [Flowable.combineLatest] using
 * the flowables of 3 repositories
 */
fun <A, B, C> combineRepositoryFlowables(
        r1: BaseRepository<A>,
        r2: BaseRepository<B>,
        r3: BaseRepository<C>
): Flowable<Tuple3<List<A>, List<B>, List<C>>> {
    return f(r1) + f(r2) + f(r3)
}

/**
 * [Flowable.combineLatest] using
 * the flowables of 4 repositories
 */
fun <A, B, C, D> combineRepositoryFlowables(
        r1: BaseRepository<A>,
        r2: BaseRepository<B>,
        r3: BaseRepository<C>,
        r4: BaseRepository<D>
): Flowable<Tuple4<List<A>, List<B>, List<C>, List<D>>> {
    return f(r1) + f(r2) + f(r3) + f(r4)
}

/**
 * [Flowable.combineLatest] using
 * the flowables of 5 repositories
 */
fun <A, B, C, D, E> combineRepositoryFlowables(
        r1: BaseRepository<A>,
        r2: BaseRepository<B>,
        r3: BaseRepository<C>,
        r4: BaseRepository<D>,
        r5: BaseRepository<E>
): Flowable<Tuple5<List<A>, List<B>, List<C>, List<D>, List<E>>> {
    return f(r1) + f(r2) + f(r3) + f(r4) + f(r5)
}

/**
 * [Flowable.combineLatest] using
 * the flowables of 6 repositories
 */
fun <A, B, C, D, E, F> combineRepositoryFlowables(
        r1: BaseRepository<A>,
        r2: BaseRepository<B>,
        r3: BaseRepository<C>,
        r4: BaseRepository<D>,
        r5: BaseRepository<E>,
        r6: BaseRepository<F>
): Flowable<Tuple6<List<A>, List<B>, List<C>, List<D>, List<E>, List<F>>> {
    return f(r1) + f(r2) + f(r3) + f(r4) + f(r5) + f(r6)
}

/**
 * Short hand syntax to get the flowable of the
 * given repository
 */
private fun <T> f(repository: BaseRepository<T>): Flowable<List<T>> = repository.flowable()