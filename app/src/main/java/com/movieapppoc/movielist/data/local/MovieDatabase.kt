package com.movieapppoc.movielist.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.movieapppoc.movielist.data.local.MovieDatabase.Companion.DATABASE_VERSION
import com.movieapppoc.profile.data.local.UserDao
import com.movieapppoc.profile.data.local.UserEntity

@Database(
    entities = [MovieEntity::class, UserEntity::class],
    version = DATABASE_VERSION
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "moviesdb.db"
        const val DATABASE_VERSION = 1
    }
}