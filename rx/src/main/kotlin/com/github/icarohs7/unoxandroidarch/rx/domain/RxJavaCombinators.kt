package com.github.icarohs7.unoxandroidarch.rx.domain

import arrow.core.Tuple2
import arrow.core.Tuple3
import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction

/**
 * Combine 2 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
operator fun <A, B> Flowable<A>.plus(
        b: Flowable<B>
): Flowable<Tuple2<A, B>> {
    return Flowable.combineLatest(this, b, BiFunction { t1, t2 ->
        Tuple2(t1, t2)
    })
}

/**
 * Combine 3 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
@JvmName("plus2")
operator fun <A, B, C> Flowable<Tuple2<A, B>>.plus(
        c: Flowable<C>
): Flowable<Tuple3<A, B, C>> {
    return Flowable.combineLatest(this, c, BiFunction { t1, t2 ->
        Tuple3(t1.a, t1.b, t2)
    })
}

/**
 * Combine 4 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
@JvmName("plus3")
operator fun <A, B, C, D> Flowable<Tuple3<A, B, C>>.plus(
        d: Flowable<D>
): Flowable<Tuple4<A, B, C, D>> {
    return Flowable.combineLatest(this, d, BiFunction { t1, t2 ->
        Tuple4(t1.a, t1.b, t1.c, t2)
    })
}

/**
 * Combine 5 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
@JvmName("plus4")
operator fun <A, B, C, D, E> Flowable<Tuple4<A, B, C, D>>.plus(
        e: Flowable<E>
): Flowable<Tuple5<A, B, C, D, E>> {
    return Flowable.combineLatest(this, e, BiFunction { t1, t2 ->
        Tuple5(t1.a, t1.b, t1.c, t1.d, t2)
    })
}

/**
 * Combine 6 Flowables into a Tuple using
 * [Flowable.combineLatest]
 */
@JvmName("plus5")
operator fun <A, B, C, D, E, F> Flowable<Tuple5<A, B, C, D, E>>.plus(
        f: Flowable<F>
): Flowable<Tuple6<A, B, C, D, E, F>> {
    return Flowable.combineLatest(this, f, BiFunction { t1, t2 ->
        Tuple6(t1.a, t1.b, t1.c, t1.d, t1.e, t2)
    })
}