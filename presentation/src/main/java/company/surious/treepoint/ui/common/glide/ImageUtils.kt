package company.surious.treepoint.ui.common.glide

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream


object ImageUtils {
    private const val MAX_SIZE_PX = 1000

    fun encodeImage(contentResolver: ContentResolver, uri: Uri): String {
        val originalBitmap = readBitmap(contentResolver, uri)
        val compressedBitmap = compressBitmap(originalBitmap)
        val outputStream = ByteArrayOutputStream()
        compressedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val bytes: ByteArray = outputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun readBitmap(contentResolver: ContentResolver, uri: Uri) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    contentResolver,
                    uri
                )
            )
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }

    private fun compressBitmap(bitmap: Bitmap): Bitmap {
        val compressedBound = calculateCompressedBitmapBounds(bitmap)
        return Bitmap.createScaledBitmap(
            bitmap,
            compressedBound.first,
            compressedBound.second,
            true
        )
    }

    private fun calculateCompressedBitmapBounds(bitmap: Bitmap): Pair<Int, Int> {
        with(bitmap) {
            val isAlbum = width >= height
            val maxLength =
                if (isAlbum) {
                    width
                } else {
                    height
                }
            val reduction = MAX_SIZE_PX / maxLength.toFloat()
            return if (reduction < 1) {
                if (isAlbum) {
                    Pair(MAX_SIZE_PX, (height * reduction).toInt())
                } else {
                    Pair((width * reduction).toInt(), MAX_SIZE_PX)
                }
            } else {
                Pair(width, height)
            }
        }
    }

}