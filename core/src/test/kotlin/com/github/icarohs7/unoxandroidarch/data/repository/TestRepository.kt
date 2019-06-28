package com.github.icarohs7.unoxandroidarch.data.repository

import com.github.icarohs7.unoxandroidarch.Injector
import com.github.icarohs7.unoxandroidarch.data.db.TestClassDao
import org.koin.core.get

class TestRepository : TestClassDao by Injector.get()