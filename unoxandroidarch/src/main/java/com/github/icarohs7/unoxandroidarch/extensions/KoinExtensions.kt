package com.github.icarohs7.unoxandroidarch.extensions

import org.koin.core.context.loadKoinModules
import org.koin.core.definition.Definition
import org.koin.dsl.module

/**
 * Add the given definition to the DI container
 * as a singleton
 */
inline fun <reified T : Any> registerSingletonInDiContainer(noinline definition: Definition<T>) {
    loadKoinModules(module { single(override = true, definition = definition) })
}

/**
 * Add the given definition to the DI container
 * as a factory
 */
inline fun <reified T : Any> registerFactoryInDiContainer(noinline definition: Definition<T>) {
    loadKoinModules(module { factory(override = true, definition = definition) })
}