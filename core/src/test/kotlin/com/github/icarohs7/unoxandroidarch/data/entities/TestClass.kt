package com.github.icarohs7.unoxandroidarch.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class TestClass(@PrimaryKey val id: Int = 0, val message: String)