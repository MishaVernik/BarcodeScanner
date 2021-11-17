package my.kpi.barcode25.apiManager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import my.kpi.barcode25.contracts.IRestApi
import my.kpi.barcode25.models.BarcodeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {


    fun getItems(): LiveData<String?> {
        val data = MutableLiveData<String?>()
        val retrofit = ServiceBuilder.buildService(IRestApi::class.java)
        retrofit?.getItems()?.enqueue(object : Callback<String?>{

            override fun onFailure(call: Call<String?>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }

    fun addUser(userData: BarcodeModel, onResult: (BarcodeModel?) -> Unit){
        val retrofit = ServiceBuilder.buildService(IRestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<BarcodeModel> {
                override fun onFailure(call: Call<BarcodeModel>, t: Throwable) {
                    Log.d("BODY", "t.message.toString()")
                    Log.d("BODY", t.message.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<BarcodeModel>, response: Response<BarcodeModel>) {
                    val addedUser = response.body()
                    Log.d("BODY", "addedUser.toString()")
                    Log.d("BODY", addedUser.toString())
                    onResult(addedUser)
                }
            }
        )
    }
}