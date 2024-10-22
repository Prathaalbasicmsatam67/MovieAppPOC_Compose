package com.movieapppoc.profile.data.local

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert
    suspend fun createUser(userEntity: UserEntity)
}