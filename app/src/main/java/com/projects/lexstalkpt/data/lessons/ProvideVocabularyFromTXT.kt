package com.projects.lexstalkpt.data.lessons

import android.content.res.AssetManager
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

class ProvideVocabularyFromTXT @Inject constructor (private val assetsManager: AssetManager) {

    fun generateLists(myKey: String): List<List<String>> {
        return try {
            val inputStream: InputStream = assetsManager.open(myKey)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val myText = String(buffer, Charset.forName("UTF-8"))
            separateStrings(myText)
        } catch (ex: IOException) {
            ex.printStackTrace()
            emptyList()
        }
    }

    private fun separateStrings(myText: String): List<List<String>> {
        val myVerbs = myText.split("\n")
        return separateLines(myVerbs)
    }

    private fun separateLines(myVerbs: List<String>): List<List<String>> {
        val myWords: MutableList<List<String>> = mutableListOf()
        myVerbs.forEach { myLine ->
            myWords.add(myLine.split("-"))
        }
        return myWords
    }
}