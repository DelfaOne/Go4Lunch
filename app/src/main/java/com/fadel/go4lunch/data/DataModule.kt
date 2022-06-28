package com.fadel.go4lunch.data

import android.content.Context
import com.fadel.go4lunch.data.datasource.NearbyPlacesWebDataSource
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun provideFirebaseAUth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideNearByPlacesDataSource(): NearbyPlacesWebDataSource {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BASIC)
                        }
                    )
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NearbyPlacesWebDataSource::class.java)

        //TODO HttpClient Provider
    }

}