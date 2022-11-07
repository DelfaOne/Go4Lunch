package com.fadel.go4lunch.data

import android.content.Context
import com.fadel.go4lunch.data.datasource.AutocompleteWebDataSource
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
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun provideFirebaseAUth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideGoogleRetrofit(): Retrofit {
        return Retrofit.Builder()
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
    }

    @Provides
    @Singleton
    fun provideNearByPlacesDataSource(
        googleRetrofit: Retrofit,
    ): NearbyPlacesWebDataSource {
        return googleRetrofit.create(NearbyPlacesWebDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideAutocompleteWebDataSource(
        googleRetrofit: Retrofit,
    ): AutocompleteWebDataSource {
        return googleRetrofit.create(AutocompleteWebDataSource::class.java)
    }

}