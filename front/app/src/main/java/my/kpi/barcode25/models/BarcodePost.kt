package my.kpi.barcode25.models

data class BarcodePost(
    var title: String,
    var body: String,
    var image: String
){
    override fun toString(): String {
        return "BarcodePost(title='$title', image='$image')"
    }
}