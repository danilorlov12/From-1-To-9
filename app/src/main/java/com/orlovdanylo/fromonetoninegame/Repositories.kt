package com.orlovdanylo.fromonetoninegame

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.orlovdanylo.fromonetoninegame.data.PlayerRepositoryImpl
import com.orlovdanylo.fromonetoninegame.data.core.AppDatabase
import com.orlovdanylo.fromonetoninegame.data.core.ApplicationMigrations
import com.orlovdanylo.fromonetoninegame.data.game.GameRepositoryImpl
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticRepositoryImpl
import com.orlovdanylo.fromonetoninegame.domain.GameRepository
import com.orlovdanylo.fromonetoninegame.domain.InfoPagesRepository
import com.orlovdanylo.fromonetoninegame.data.info_pages.InfoPagesRepositoryImpl
import com.orlovdanylo.fromonetoninegame.domain.PlayerRepository
import com.orlovdanylo.fromonetoninegame.domain.StatisticsRepository

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "main.db")
            .addMigrations(*ApplicationMigrations().migrations())
            .build()
    }

    private val sharedPreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(applicationContext.packageName, Activity.MODE_PRIVATE)
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

    val playerRepository: PlayerRepository by lazy {
        PlayerRepositoryImpl(sharedPreferences)
    }

    fun init(context: Context) {
        applicationContext = context
    }
}