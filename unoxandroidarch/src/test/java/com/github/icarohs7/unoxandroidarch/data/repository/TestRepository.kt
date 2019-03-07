package com.github.icarohs7.unoxandroidarch.data.repository

import com.github.icarohs7.unoxandroidarch.Injector
import com.github.icarohs7.unoxandroidarch.data.db.TestDao
import com.github.icarohs7.unoxandroidarch.data.entities.TestClass
import org.koin.standalone.get

class TestRepository : BaseRepositoryDaoAdapter<TestClass, TestDao>(Injector.get())