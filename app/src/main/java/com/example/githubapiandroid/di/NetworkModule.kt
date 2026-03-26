package com.example.githubapiandroid.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.feature.pulls.data.service.PullsService
import com.example.feature.repos.data.service.ReposService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.github.com/"
    private const val CACHE_CONTROL = "Cache-Control"
    private const val CACHE_VALUE_WITH_NETWORK = "public, max-age=" + 5
    private const val CACHE_VALUE_WITHOUT_NETWORK =
        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    @Provides
    fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(context.cacheDir, cacheSize.toLong())

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (isNetworkAvailable(context)) {
                    request.newBuilder().header(CACHE_CONTROL, CACHE_VALUE_WITH_NETWORK).build()
                } else {
                    request.newBuilder().header(CACHE_CONTROL, CACHE_VALUE_WITHOUT_NETWORK).build()
                }
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideReposService(retrofit: Retrofit): ReposService =
        retrofit.create(ReposService::class.java)

    @Provides
    fun providePullsService(retrofit: Retrofit): PullsService =
        retrofit.create(PullsService::class.java)
}
