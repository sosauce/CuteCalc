package com.sosauce.cutecalc.domain.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sosauce.cutecalc.domain.model.Calculation

@Database(
    entities = [Calculation::class],
    version = 1
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract val dao: HistoryDao
}