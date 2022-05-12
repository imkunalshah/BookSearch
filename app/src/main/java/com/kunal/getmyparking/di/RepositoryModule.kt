package com.kunal.getmyparking.di

import com.kunal.getmyparking.data.network.apis.BooksApi
import com.kunal.getmyparking.data.repositories.BooksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBooksRepository(
        api: BooksApi,
    ): BooksRepository {
        return BooksRepository(api)
    }

}