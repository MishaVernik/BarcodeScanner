package my.kpi.barcode25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_logs.*
import my.kpi.barcode25.apiManager.ServiceBuilder
import my.kpi.barcode25.contracts.IRestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogsActivity : AppCompatActivity() {

    private lateinit var blogAdapter: BarcodeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet(){

        val data = DataSource.getPosts()
        Toast.makeText(applicationContext, if (data.size > 0) data[0].body else "Empty", Toast.LENGTH_LONG).show()
        blogAdapter.submitList(data)

    }

    private fun initRecyclerView(){

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@LogsActivity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            blogAdapter = BarcodeRecyclerAdapter()
            adapter = blogAdapter
        }
    }
}