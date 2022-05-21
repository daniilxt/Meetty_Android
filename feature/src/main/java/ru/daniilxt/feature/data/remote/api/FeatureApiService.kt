package ru.daniilxt.feature.data.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import ru.daniilxt.feature.data.remote.model.EducationInstituteResponse

interface FeatureApiService {
    @GET("/api/v1/regsteps/edu")
    fun getEducationInstitutes(): Single<Response<List<EducationInstituteResponse>>>
}