package my.kpi.barcode25.apiManager

import my.kpi.barcode25.contracts.APIService
import my.kpi.barcode25.models.BarcodeModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class  ApiManager : APIService{
    fun addDummyUser() {
        val apiService = RestApiService()
        val userInfo = BarcodeModel(
            name = "test"
        )

        apiService.addUser(userInfo) {
            if (it?.name != null) {
                // it = newly added user parsed as response
                // it?.id = newly added user ID
            } else {
                // Timber.d("Error registering new user")
            }
        }
    }

    override suspend fun createPost(requestBody: RequestBody): Response<ResponseBody> {
        TODO("Not yet implemented")
    }

    override suspend fun getItems(requestBody: RequestBody): Response<ResponseBody> {
        TODO("Not yet implemented")
    }

}