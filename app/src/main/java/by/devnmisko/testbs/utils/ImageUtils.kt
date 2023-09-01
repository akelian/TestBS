package by.devnmisko.testbs.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.camera.core.ImageProxy
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

fun Bitmap.bitmapToString(): String {
    val byteArray = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, byteArray)

    return Base64.encodeToString(byteArray.toByteArray(), Base64.DEFAULT)
}

fun String.stringToBitmap(): Bitmap? {
    val bytes = Base64.decode(this, Base64.DEFAULT)

    return try {
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    } catch (exception: Exception) {
        Timber.e(exception.message.toString())
        null
    }
}

fun ImageProxy.imageProxyToBitmap(): Bitmap {
    val planeProxy = this.planes[0]
    val buffer: ByteBuffer = planeProxy.buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}