package com.movieapppoc.profile.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_table")
data class UserEntity(
    @PrimaryKey
    val id : Int,
    val name : String
)