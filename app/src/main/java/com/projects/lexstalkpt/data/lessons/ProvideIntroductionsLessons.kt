package com.projects.lexstalkpt.data.lessons

import android.content.res.AssetManager
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

class ProvideIntroductionsLessons @Inject constructor (private val assetsManager: AssetManager) {

    fun generateIntroduction(myKey: String, isReading: Boolean = false): List<String> {
        return try {
            val inputStream: InputStream = assetsManager.open(myKey)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val myText = String(buffer, Charset.forName("UTF-8"))
            separateStrings(myText, isReading)
        } catch (ex: IOException) {
            ex.printStackTrace()
            emptyList()
        }
    }

    private fun separateStrings(myText: String, isReading: Boolean): List<String> {
        return if (isReading) myText.split("\n\n\n") else myText.split("\n\n")
    }
}