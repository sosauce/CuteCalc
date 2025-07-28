package com.sosauce.cutecalc.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sosauce.cutecalc.domain.model.Calculation
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    suspend fun insertCalculation(calculation: Calculation)

    @Delete
    suspend fun deleteCalculation(calculation: Calculation)

    @Query("DELETE FROM calculation")
    suspend fun deleteAllCalculations()

    @Query("SELECT * FROM calculation")
    fun getAllCalculations(): Flow<List<Calculation>>
}