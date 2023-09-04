package by.devnmisko.testbs.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.camera.core.ImageProxy
import by.devnmisko.testbs.utils.Const.DEFAULT_IMAGE_SIZE
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

fun Bitmap.bitmapToString(): String {
    val byteArray = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, byteArray)

    return Base64.encodeToString(byteArray.toByteArray(), Base64.DEFAULT)
}

fun ImageProxy.imageProxyToBitmap(): Bitmap {
    val planeProxy = this.planes[0]
    val buffer: ByteBuffer = planeProxy.buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    return bitmap.resizeBitmap(DEFAULT_IMAGE_SIZE)
}

fun Bitmap.resizeBitmap(size: Int): Bitmap {
    return if (size > 0) {
        val width = width
        val height = height
        val ratioBitmap = width.toFloat() / height.toFloat()
        var finalWidth = size
        var finalHeight = size
        if (ratioBitmap < 1) {
            finalWidth = (size.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (size.toFloat() / ratioBitmap).toInt()
        }
        Bitmap.createScaledBitmap(this, finalWidth, finalHeight, true)
    } else {
        this
    }
}