package utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
const val CONSOLE_COLOR_ERROR = "\u001b[31m"
const val CONSOLE_COLOR_SUCCESS = "\u001B[32m"
const val CONSOLE_COLOR_RESET = "\u001b[0m"

const val FILE_NAME_XML = "xml.xml"
const val FILE_NAME_JSON = "json.json"
const val TAG_LOCATION = "location"
const val TAG_NAME = "name"
const val TAG_NEWS = "news"

const val HTTP_XML = "https://api2.kiparo.com/static/it_news.xml"
const val HTTP_JSON = "https://api2.kiparo.com/static/it_news.json"


fun printlnError(str: String) {
    println(CONSOLE_COLOR_ERROR + str + CONSOLE_COLOR_RESET)
    println("$CONSOLE_COLOR_ERROR---------------------------------------------------$CONSOLE_COLOR_RESET")
}

fun printlnSuccess(str: String) {
    println(CONSOLE_COLOR_SUCCESS + str + CONSOLE_COLOR_RESET)
}

fun beautifulDateFun(zonedDateTime: ZonedDateTime): String {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss Z")
    val date = zonedDateTime.format(dateFormatter)
    return date.toString()
}


