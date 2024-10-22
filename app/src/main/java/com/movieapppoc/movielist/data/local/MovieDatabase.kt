package com.movieapppoc.movielist.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.movieapppoc.profile.data.local.UserDao
import com.movieapppoc.profile.data.local.UserEntity

@Database(
    entities = [MovieEntity::class, UserEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    abstract val userDao: UserDao
}