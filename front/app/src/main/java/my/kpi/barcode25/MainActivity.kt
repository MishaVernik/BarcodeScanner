package my.kpi.barcode25

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.Writer
import com.google.zxing.common.BitMatrix
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import my.kpi.barcode25.apiManager.RestApiService
import my.kpi.barcode25.models.BarcodeModel
import java.util.*


class MainActivity : AppCompatActivity() {

    private var editTextProductId: EditText? = null
    private var buttonGenerate: Button? = null
    private var buttonNext: Button? = null
    private var buttonScan:Button? = null
    private var imageViewResult: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }
    private fun initView() {
        editTextProductId = findViewById(R.id.editTextProductId)
        imageViewResult = findViewById(R.id.imageViewResult)
        buttonNext = findViewById(R.id.buttonNext)
        buttonGenerate = findViewById(R.id.buttonGenerate)

        buttonGenerate?.setOnClickListener(View.OnClickListener { view ->
            buttonGenerate_onClick(
                view
            )
        })
        buttonNext?.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, LogsActivity::class.java)
            startActivity(intent);
        }

        buttonScan = findViewById(R.id.buttonScan)
        buttonScan?.setOnClickListener(View.OnClickListener { view -> buttonScan_onClick(view) })
    }

    fun append(arr: Array<Int>, element: Int): Array<Int> {
        val list: MutableList<Int> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }


    fun addBarcodeToLogs(name: String) {
        val apiService = RestApiService()
        val userInfo = BarcodeModel(
            name = name
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

    private fun industrialCode25(productId: String): Array<Int> {
       // Toast.makeText(applicationContext, "HERE1", Toast.LENGTH_LONG).show()
        var matrix = arrayOf(
            intArrayOf(0, 0, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 1),
            intArrayOf(0, 1, 0, 0, 1),
            intArrayOf(1, 1, 0, 0, 0),
            intArrayOf(0, 0, 1, 0, 1),
            intArrayOf(1, 0, 1, 0, 0),
            intArrayOf(0, 1, 1, 0, 0),
            intArrayOf(0, 0, 0, 1, 1),
            intArrayOf(1, 0, 0, 1, 0),
            intArrayOf(0, 1, 0, 1, 0),
        )
        var nums = arrayOf<Int>(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        for (i in 0..60){
            nums = append(nums, 0)
        }
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)

        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)

        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)


        for (i in 0..productId.length - 1){
            for (j in matrix[productId[i].toString().toInt()].indices){
                if (matrix[productId[i].toString().toInt()][j] == 1){
                    nums = append(nums, 1)
                    nums = append(nums, 1)
                    nums = append(nums, 1)
                    nums = append(nums, 1)
                }else if (matrix[productId[i].toString().toInt()][j] == 0){
                    nums = append(nums, 1)
                    nums = append(nums, 1)
                }
                nums = append(nums, 0)
                nums = append(nums, 0)
                nums = append(nums, 0)
                nums = append(nums, 0)
            }
        }
        Toast.makeText(applicationContext, "HERE 2", Toast.LENGTH_LONG).show()
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)

        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)


        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 1)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)
        nums = append(nums, 0)



        return nums
    }

    private fun buttonGenerate_onClick(view: View) {
        Log.d("HERE", "MESSSSSSA")

        try {
            val productId = editTextProductId!!.text.toString()

            // populates generated logs
            addBarcodeToLogs(productId)

            val hintMap: Hashtable<EncodeHintType, ErrorCorrectionLevel> = Hashtable<EncodeHintType, ErrorCorrectionLevel>()
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L)
            val codeWriter: Writer
            //codeWriter = ITFWriter()
            //val byteMatrix: BitMatrix = codeWriter.encode(productId, BarcodeFormat.ITF, 400, 200, hintMap)

            codeWriter = Code128Writer()
            val byteMatrix: BitMatrix = codeWriter.encode(
                productId,
                BarcodeFormat.CODE_128,
                400,
                200,
                hintMap
            )
            val arrCode25 = industrialCode25(productId)

            val width = byteMatrix.width
            val height = byteMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            for (i in 0 until width) {
                for (j in 0 until height) {
                    //BarCode25
                    //bitmap.setPixel(i, j, if (i < arrCode25.size && arrCode25[i] == 1) Color.BLACK else Color.WHITE)
                    //ITF
                    bitmap.setPixel(i, j, if (byteMatrix[i, j]) Color.BLACK else Color.WHITE)
                }
            }
            imageViewResult!!.setImageBitmap(bitmap)
        } catch (e: Exception) {
            //Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }
    private fun buttonScan_onClick(view: View) {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        intentIntegrator.setCameraId(0)
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            val productId = intentResult.contents
            Toast.makeText(applicationContext, productId, Toast.LENGTH_LONG).show()
        }
    }
}