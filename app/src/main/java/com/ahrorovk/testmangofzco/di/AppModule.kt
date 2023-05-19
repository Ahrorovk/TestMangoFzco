package com.ahrorovk.testmangofzco.di

import android.content.Context
import com.ahrorovk.testmangofzco.core.Constants
import com.ahrorovk.testmangofzco.data.ApplicationApi
import com.ahrorovk.testmangofzco.data.local.DataStoreManager
import com.ahrorovk.testmangofzco.data.repository.TestMangoRepositoryImpl
import com.ahrorovk.testmangofzco.domain.repository.TestMangoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthenticationApi():ApplicationApi {
        return Retrofit.Builder()
            .baseUrl(Constants.TEST_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(ApplicationApi::class.java)
    }
    @Provides
    @Singleton
    fun provideAppRepository(api: ApplicationApi): TestMangoRepository =
        TestMangoRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context
    ) = DataStoreManager(context)
}
