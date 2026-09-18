package com.minorproject.cardscannerai.ai

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import kotlin.math.max
import kotlin.math.roundToInt

class ImagePreprocessor {
    fun enhanceForOcr(context: Context, uri: Uri): Uri {
        val bitmap = context.contentResolver.openInputStream(uri)?.use { input ->
            BitmapFactory.decodeStream(input)
        } ?: return uri

        val enhanced = normalizeForOcr(bitmap)
        val file = File.createTempFile("enhanced_card", ".png", context.cacheDir)
        FileOutputStream(file).use { outputStream ->
            enhanced.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }

        return file.toUri()
    }

    private fun normalizeForOcr(bitmap: Bitmap): Bitmap {
        val prepared = scaleDownIfNeeded(bitmap)
        val out = Bitmap.createBitmap(prepared.width, prepared.height, Bitmap.Config.ARGB_8888)

        for (x in 0 until prepared.width) {
            for (y in 0 until prepared.height) {
                val pixel = prepared.getPixel(x, y)
                val alpha = Color.alpha(pixel)
                val luminance = luminance(pixel)
                val contrasted = adjustContrast(luminance)
                out.setPixel(x, y, Color.argb(alpha, contrasted, contrasted, contrasted))
            }
        }

        return out
    }

    private fun scaleDownIfNeeded(bitmap: Bitmap): Bitmap {
        val longestSide = max(bitmap.width, bitmap.height)
        if (longestSide <= 2200) {
            return bitmap.copy(Bitmap.Config.ARGB_8888, false)
        }

        val scale = 2200f / longestSide.toFloat()
        val width = (bitmap.width * scale).roundToInt().coerceAtLeast(1)
        val height = (bitmap.height * scale).roundToInt().coerceAtLeast(1)
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun luminance(pixel: Int): Int {
        val red = Color.red(pixel)
        val green = Color.green(pixel)
        val blue = Color.blue(pixel)
        return (0.299 * red + 0.587 * green + 0.114 * blue).roundToInt().coerceIn(0, 255)
    }

    private fun adjustContrast(value: Int): Int {
        val contrasted = (((value / 255.0 - 0.5) * 1.45) + 0.5) * 255.0
        return contrasted.roundToInt().coerceIn(0, 255)
    }
}
