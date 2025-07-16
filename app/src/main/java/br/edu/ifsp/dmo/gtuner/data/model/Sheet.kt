package br.edu.ifsp.dmo.gtuner.data.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.util.UUID

class Sheet (
    val id: String,
    val name: String,
    val url: String?,
    val author: String,
    val arrangment: String,
    val bitmap: Bitmap?
) {
    companion object {
        fun create_new_sheet(
            url: String?,
            name: String,
            author: String,
            arrangment: String,
            bitmap: Bitmap?
        ): Sheet {
            return Sheet(
                url = url,
                id = generate_sheet_id(),
                name = name,
                author = author,
                arrangment = arrangment,
                bitmap = bitmap
            )
        }

        fun generate_sheet_id() : String {
            return "C" + UUID.randomUUID().toString().replace("-", "").take(10)
        }

        fun BitMapToString(bitmap: Bitmap?): String {
            if (bitmap != null) {
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val b = baos.toByteArray()
                return Base64.encodeToString(b, Base64.DEFAULT)
            } else {
                return "0"
            }
        }

        fun StringToBitmap(string: String?): Bitmap? {
            if (string != null) {
                val imageBytes = Base64.decode(string, 0)
                return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            } else {
                return null
            }
        }
    }

    override fun toString(): String {
        return id + " " + name + " " + url + " " + author + " " + arrangment + " " + bitmap
    }
}