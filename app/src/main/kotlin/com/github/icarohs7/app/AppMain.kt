package com.github.icarohs7.app

import android.app.Application
import com.github.icarohs7.app.data.db.AppDatabase
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import com.github.icarohs7.unoxandroidarch.data.db.buildDatabase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class AppMain : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(onCreateKoinModules())
        }
        UnoxAndroidArch.init()
    }

    private fun onCreateKoinModules(): List<Module> = listOf(module {
        val database = buildDatabase<AppDatabase>(dbFileName = "app.db").build()

        single { database }
        single { database.personDao() }
    })
}