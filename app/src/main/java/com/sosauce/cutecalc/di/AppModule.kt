package com.sosauce.cutecalc.di

import androidx.room.Room
import com.sosauce.cutecalc.history.HistoryDatabase
import com.sosauce.cutecalc.history.HistoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = HistoryDatabase::class.java,
            name = "history.db"
        ).build().dao
    }
    viewModel {
        HistoryViewModel(get())
    }
}