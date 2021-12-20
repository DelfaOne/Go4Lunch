package com.fadel.go4lunch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        //TODO()
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
}