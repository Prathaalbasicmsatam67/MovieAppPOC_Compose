package com.movieapppoc.profile.domain.repository

interface UserRepository {

    suspend fun create()

    suspend fun delete()
}