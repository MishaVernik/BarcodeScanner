package my.kpi.barcode25.contracts

import my.kpi.barcode25.models.BarcodeModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface IRestApi {

    @Headers("Content-Type: application/json",
        "x-functions-key: cm2B8pKMb8b5WKE4oB1GSxLVPospib/ToSUiUx6w4vgI7saUeLuh3Q==")
    @POST("api/PostGeneratedItem")
    fun addUser(@Body userData: BarcodeModel): Call<BarcodeModel>

    @Headers("Content-Type: application/json","x-functions-key: cm2B8pKMb8b5WKE4oB1GSxLVPospib/ToSUiUx6w4vgI7saUeLuh3Q==")
    @GET("api/GetListOfGeneratedItems")
    fun getItems() : Call<String?>
}