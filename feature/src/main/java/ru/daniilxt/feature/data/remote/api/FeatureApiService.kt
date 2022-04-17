package ru.daniilxt.feature.data.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.daniilxt.feature.domain.model.UserDialog

interface FeatureApiService {
    @GET("/api/dialogs/{id}")
    fun getEvents(@Path("id") id: Long): Single<Response<UserDialog>>
}
