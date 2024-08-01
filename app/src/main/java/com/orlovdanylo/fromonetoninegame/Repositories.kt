package com.orlovdanylo.fromonetoninegame

import android.content.Context
import androidx.room.Room
import com.orlovdanylo.fromonetoninegame.data.core.AppDatabase
import com.orlovdanylo.fromonetoninegame.data.core.ApplicationMigrations
import com.orlovdanylo.fromonetoninegame.data.game.GameRepositoryImpl
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticRepositoryImpl
import com.orlovdanylo.fromonetoninegame.domain.GameRepository
import com.orlovdanylo.fromonetoninegame.domain.InfoPagesRepository
import com.orlovdanylo.fromonetoninegame.data.info_pages.InfoPagesRepositoryImpl
import com.orlovdanylo.fromonetoninegame.domain.StatisticsRepository

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "main.db")
            .addMigrations(*ApplicationMigrations().migrations())
            .build()
    }

    val gameRepository: GameRepository by lazy {
        GameRepositoryImpl(database.gameDao())
    }

    val statisticsRepository: StatisticsRepository by lazy {
        StatisticRepositoryImpl(database.statisticsDao())
    }

    val infoPagesRepository: InfoPagesRepository by lazy {
        InfoPagesRepositoryImpl()
    }

    fun init(context: Context) {
        applicationContext = context
    }
}