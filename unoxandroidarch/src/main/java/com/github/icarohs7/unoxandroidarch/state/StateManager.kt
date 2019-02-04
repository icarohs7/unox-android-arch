package com.github.icarohs7.unoxandroidarch.state

/**
 * Interface describing a container managing a
 * instance of a substate of the application
 * @param T The type representing the State of The Application
 * @param O The type representing the observable used to emit new instances of the state
 */
interface StateManager<T, O> {
    val observable: O

    /** Return the last valid state of the application */
    suspend fun lastValue(): T

    /**
     * Apply the reducer to the state and
     * use the new state created as the
     * current state of the application
     */
    fun reduce(reducer: Reducer<T>)

    /**
     * Apply the reducer to the state
     * and use the new state created as
     * the current state of the application,
     * also invoke the callback when the
     * reducer is applied
     */
    fun reduceWithCallback(callback: () -> Unit, reducer: Reducer<T>)

    /**
     * Use the latest state without
     * changing it
     */
    fun use(action: Action<T>)

    companion object
}