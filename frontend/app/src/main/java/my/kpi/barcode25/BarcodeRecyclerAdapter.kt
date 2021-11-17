package my.kpi.barcode25

import android.graphics.Bitmap
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.Writer
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import my.kpi.barcode25.models.BarcodePost
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.example_item.view.*
import java.util.*

class BarcodeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val TAG: String = "AppDebug"

    private var items: List<BarcodePost> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BarcodeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is BarcodeViewHolder -> {
                holder.bind(items.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: List<BarcodePost>){
        items = blogList
    }


    class BarcodeViewHolder
    constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val blog_image = itemView.blog_image
        val blog_title = itemView.blog_title

        fun createBarCode(productId: String?) : Bitmap{
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

            return bitmap
        }
        fun bind(blogPost: BarcodePost){

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

//            Glide.with(itemView.context)
//                .applyDefaultRequestOptions(requestOptions)
//                .load(blogPost.image)
//                .into(blog_image)
            val productId = blogPost.title.split("\\s".toRegex())[1]

            val string = productId
            var numeric = true

            numeric = string.matches("-?\\d+(\\.\\d+)?".toRegex())

            if (numeric)
                blog_image.setImageBitmap(createBarCode(productId))
            else
                println("$string is not a number")


            blog_title.setText(blogPost.title)


        }

    }
}