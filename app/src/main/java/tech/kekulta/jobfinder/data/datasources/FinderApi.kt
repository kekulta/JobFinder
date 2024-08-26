package tech.kekulta.jobfinder.data.datasources

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import kotlinx.coroutines.flow.Flow
import tech.kekulta.jobfinder.data.models.NetworkResponseDto

interface FinderApi {
    companion object {
        const val BASE_URL = "https://drive.usercontent.google.com/"
        const val ID = "1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r"
        const val EXPORT = "download"
    }

    @GET("u/0/uc")
    fun getRecommendations(
        @Query("id") id: String, @Query("export") export: String
    ): Flow<NetworkResponseDto>
}