package com.sosauce.cutecalc.history

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Calculation::class],
    version = 1
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract val dao: HistoryDao
}