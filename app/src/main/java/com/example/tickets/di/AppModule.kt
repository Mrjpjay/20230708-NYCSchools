package com.example.tickets.di

import com.example.tickets.repo.SchoolsRepoImpl
import com.example.tickets.repo.SchoolRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesTickersRepository(): SchoolRepository{
        return SchoolsRepoImpl()
    }
}