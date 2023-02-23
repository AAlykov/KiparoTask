package data

import utils.*
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class DownloadData(private val path: String, private val downloadFile: String) {

    fun downloadData(): Boolean {
        println("Скачиваем $path ...")

        val url = URL(path)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.doInput = true
        httpURLConnection.doOutput = false
        val responseCode = httpURLConnection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = httpURLConnection.inputStream.bufferedReader().use {
                it.readText()
            }
            when(path) {
                HTTP_XML -> {
                    File(downloadFile).writeText(response)
                    printlnSuccess ("Скачали в $downloadFile ...")
                }
                HTTP_JSON -> {
                    File(downloadFile).writeText(response)
                    printlnSuccess ("Скачали в $downloadFile ...")
                }
                else -> {
                    println("Неизвестный путь $path для скачивания")
                    return false
                }
            }

        } else {
            printlnError ("HTTP_URL_CONNECTION_ERROR $responseCode")
            return false
        }
        return true
    }
}