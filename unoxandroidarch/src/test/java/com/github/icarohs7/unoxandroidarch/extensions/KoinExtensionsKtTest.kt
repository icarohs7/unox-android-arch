package com.github.icarohs7.unoxandroidarch.extensions

import com.github.icarohs7.unoxandroidarch.Injector
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.get
import org.koin.dsl.module
import org.koin.test.KoinTest
import se.lovef.assert.v1.shouldEqual

class KoinExtensionsKtTest : KoinTest {
    @Before
    fun setUp() {
        var count = 0.0
        startKoin {
            modules(module {
                single { 1532 }
                factory { ++count }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should override singleton definition`() {
        Injector.get<Int>() shouldEqual 1532

        registerSingletonInDiContainer { 42 }
        Injector.get<Int>() shouldEqual 42

        registerSingletonInDiContainer { -10 }
        Injector.get<Int>() shouldEqual -10

        registerSingletonInDiContainer { 20 }
        Injector.get<Int>() shouldEqual 20
    }

    @Test
    fun `should override factory definition`() {
        Injector.get<Double>() shouldEqual 1.0
        Injector.get<Double>() shouldEqual 2.0
        Injector.get<Double>() shouldEqual 3.0

        registerFactoryInDiContainer { 15.32 }
        Injector.get<Double>() shouldEqual 15.32
        Injector.get<Double>() shouldEqual 15.32
        Injector.get<Double>() shouldEqual 15.32

        var count2 = -3.0
        registerFactoryInDiContainer { ++count2 }
        Injector.get<Double>() shouldEqual -2.0
        Injector.get<Double>() shouldEqual -1.0
        Injector.get<Double>() shouldEqual 0.0
    }
}