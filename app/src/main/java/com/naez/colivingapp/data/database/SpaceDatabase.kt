package com.naez.colivingapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naez.colivingapp.data.database.dao.SpaceDao
import com.naez.colivingapp.data.database.entities.Space

@Database(entities = [Space::class], version = 1)
abstract class SpaceDatabase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            SpaceDatabase::class.java,
            "coliving-db"
        ).build()
    }

    abstract fun spaceDao(): SpaceDao
}