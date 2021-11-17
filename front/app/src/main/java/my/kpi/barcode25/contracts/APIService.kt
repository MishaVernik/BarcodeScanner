package my.kpi.barcode25.contracts

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @POST("/api/v1/create")
    suspend fun createPost(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/v1/create")
    suspend fun getItems(@Body requestBody: RequestBody): Response<ResponseBody>
}