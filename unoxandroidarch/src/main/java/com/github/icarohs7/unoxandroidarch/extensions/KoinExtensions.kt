package com.github.icarohs7.unoxandroidarch.extensions

import org.koin.core.parameter.ParameterList
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext

/**
 * Add the given definition to the DI container
 * as a singleton
 */
inline fun <reified T : Any> registerSingletonInDiContainer(noinline definition: (ParameterList) -> T) {
    StandAloneContext.loadKoinModules(module { single(override = true, definition = definition) })
}

/**
 * Add the given definition to the DI container
 * as a factory
 */
inline fun <reified T : Any> registerFactoryInDiContainer(noinline definition: (ParameterList) -> T) {
    StandAloneContext.loadKoinModules(module { factory(override = true, definition = definition) })
}