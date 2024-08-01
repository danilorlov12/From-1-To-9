package com.orlovdanylo.fromonetoninegame.data.core

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class ApplicationMigrations {

    private val migrationFrom1To2 = object : Migration(1, 2) {
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

    fun migrations(): Array<Migration> {
        return arrayOf(migrationFrom1To2)
    }
}