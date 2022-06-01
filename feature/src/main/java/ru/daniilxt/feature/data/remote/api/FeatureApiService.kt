package ru.daniilxt.feature.data.remote.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.daniilxt.feature.data.remote.model.body.RegistrationInfoBody
import ru.daniilxt.feature.data.remote.model.response.EducationInstituteResponse
import ru.daniilxt.feature.data.remote.model.response.ProfessionalInterestResponse
import ru.daniilxt.feature.data.remote.model.response.TokensResponse
import ru.daniilxt.feature.data.remote.model.response.UserProfileInfoResponse

interface FeatureApiService {
    @GET("/api/v1/regsteps/edu")
    fun getEducationInstitutes(): Single<Response<List<EducationInstituteResponse>>>

    @GET("/api/v1/regsteps/interests")
    fun getProfessionalInterests(): Single<Response<List<ProfessionalInterestResponse>>>

    @POST("/api/v1/auth/registration")
    fun sendRegistrationInfo(
        @Body registrationInfoBody: RegistrationInfoBody
    ): Single<Response<TokensResponse>>

    @GET("/api/v1/users/match")
    fun getUsersProfileInfo(): Single<Response<List<UserProfileInfoResponse>>>

    @GET("/api/v1/profile/my")
    fun getUserProfileInfo(): Single<Response<UserProfileInfoResponse>>

    @GET("/api/v1/profile/{id}")
    fun getUserProfileInfoById(@Path("id") userId: Long): Single<Response<UserProfileInfoResponse>>

    @POST("/api/v1/auth/login")
    fun auth(@Body loginCredentialsBody: LoginCredentialsBody): Single<Response<TokensResponse>>
}
