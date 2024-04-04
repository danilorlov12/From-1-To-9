package com.orlovdanylo.fromonetoninegame.data.core

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class StatisticsMigration : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `statistics` " +
                    "(`id` INTEGER NOT NULL, " +
                    "`gamesPlayed` INTEGER, " +
                    "`gamesFinished` INTEGER," +
                    "`bestTime` TEXT, " +
                    "`bestPairs` INTEGER, " +
                    "PRIMARY KEY(`id`))"
        )
    }
}