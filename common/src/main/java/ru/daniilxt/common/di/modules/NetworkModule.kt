package ru.daniilxt.common.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.daniilxt.common.BuildConfig
import ru.daniilxt.common.di.scope.ApplicationScope
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Provides
    @ApplicationScope
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @ApplicationScope
    internal fun provideRestInterceptor(): Interceptor =
        Interceptor { chain ->
            val original = chain.request()
            val accessToken =
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYW5paWwtZmlyc292QG1haWwucnUiLCJpYXQiOjE2NTM4NDAxNzMsImV4cCI6MTY1Mzg1ODE3M30.M_2tjbaT8YgHLov2M9_S_y9oJA1wtmcMYTEABoynxcY"
            val requestBuilder = original.newBuilder()
            if (!accessToken.isNullOrEmpty() && !original.url.toString().contains("auth")) {
                requestBuilder.addHeader(AUTHORIZATION, BEARER + accessToken)
            }
            val request = requestBuilder.build()
            val response = chain.proceed(request)
            response
        }

    @Provides
    @ApplicationScope
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        restInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(restInterceptor)
            .addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    companion object {
        private val TAG = NetworkModule::class.simpleName
        private const val BEARER = "Bearer "
        private const val AUTHORIZATION = "Authorization"
    }
}
