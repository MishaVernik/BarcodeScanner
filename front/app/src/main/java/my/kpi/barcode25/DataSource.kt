package my.kpi.barcode25

import android.os.StrictMode
import android.util.Log
import my.kpi.barcode25.apiManager.ServiceBuilder
import my.kpi.barcode25.contracts.IRestApi
import my.kpi.barcode25.models.BarcodePost
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class DataSource{
    fun sendPostRequest(userName: String, password: String) {

        var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(
            userName,
            "UTF-8"
        )
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(
            password,
            "UTF-8"
        )
        val mURL = URL("<Your API Link>")

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "POST"

            val wr = OutputStreamWriter(getOutputStream());
            wr.write(reqParam);
            wr.flush();

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                println("Response : $response")
            }
        }
    }


    companion object{
         var lst: ArrayList<BarcodePost> =  ArrayList<BarcodePost>()

        fun getPosts(): ArrayList<BarcodePost>{


            createDataSet(getItems())
            Log.d("BODY", "lst.size.toString()")
            Log.d("BODY", lst.size.toString())
            Log.d("BODY", "lst.size.toString()")
            return lst
        }

        fun getItems(): String? {

            var data:String? = ""
            val retrofit = ServiceBuilder.buildService(IRestApi::class.java)
            try {
                data= retrofit?.getItems()?.execute().body()
            }catch (e: Exception){

                Log.d("BODY", "ERROR")
                Log.d("BODY", e.message.toString())
                Log.d("BODY", e.toString())
            }




            Log.d("BODY", "data")
            Log.d("BODY", data)
            Log.d("BODY", "data")
            return data
        }
        fun createDataSet(items: String?): ArrayList<BarcodePost>{

            var lines = items?.lines()

            val list = ArrayList<BarcodePost>()

            Log.d("BODY", items.toString())
            lines?.forEach {
                list.add(
                    BarcodePost(
                        it,
                        "",
                        "https://raw.githubusercontent.com/mitchtabian/Blog-Images/master/digital_ocean.png",
                    )
                )

            }
            // assign all created posts
            lst = list
            return list
        }
    }
}