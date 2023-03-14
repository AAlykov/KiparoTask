package view_console

import data.RepositoryImpl
import domain.models.RootModel
import domain.usecase.GetDataUseCase
import utils.*
import java.io.File

import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val repositoryImpl = RepositoryImpl()
    val fileXml = File(FILE_NAME_XML)
    val fileJson = File(FILE_NAME_JSON)

    //val httpURLConnection = MyHttpURLConnector()

    var formatVariant: String
    while (true) {
        //Павел: Все тексты лучше вынести в константы, учитывая, что они также часто повторяются
        //Например internal const PRESSED_1 = "Нажали 1"
        println("Главное меню")
        println("Для скачивания данных или выхода из программы нажмите соответсвующую клавишу:\n 1 - JSON-формат\n 2 - XML-формат\n 3 - выход")
        formatVariant = readln()
        when (formatVariant) {

            "1" ->  {
                println("Нажали 1")
                //fun1_json(repositoryImpl)
                val getDataUseCase = GetDataUseCase(repositoryImpl)
                val rootModel: RootModel? = getDataUseCase.getData(HTTP_JSON)
                if (rootModel != null) {
                    // Павел: Этот блок можно вынести в отсдельную функцию, он повторяется 
                    println("Объект готов для работы:")
                    println(rootModel.toString())
                    workWithRootModel(rootModel)
                } else {
                    printlnError("Не удалось получить объект")
                }
            }

            "2" -> {
                println("Нажали 2")
                val getDataUseCase = GetDataUseCase(repositoryImpl)
                val rootModel: RootModel? = getDataUseCase.getData(HTTP_XML)
                if (rootModel != null) {
                    println("Объект готов для работы:")
                    println(rootModel.toString())
                    workWithRootModel(rootModel)
                } else {
                    printlnError("Не удалось получить объект")
                }
            }

            "3" -> {
                println("Нажали 3")
                println("Удаляем временные файлы...")
                if (fileXml.exists())
                    fileXml.delete()
                if (fileJson.exists())
                    fileJson.delete()
                println("Выходим из программы...")
                exitProcess(0)
            }
            else -> printlnError("Ошибка выбора")
        }
    }
}

fun workWithRootModel(rootModel: RootModel) {

    val rootCity = rootModel.location
    val rootName = rootModel.name
    val newsModels = rootModel.news

    //val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z")
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")

    val mListOfShowNews = mutableListOf<ShowNews>()
    newsModels.forEach {
        //mListOfShowNews.add(it)
        val date = it.date
        try {
            val resultOfParsing = OffsetDateTime.parse(date, dateFormatter)
            mListOfShowNews.add(ShowNews(it.id, it.title, resultOfParsing.toZonedDateTime(), it.description, it.visible, it.keywords))
        } catch (e: Exception) {
            printlnError("Ошибка разбора id=${it.id} title=${it.title} desc=${it.description} \n $e")
        }
    }

    var keywordVariant: String
    while (true) {
        println("Для вывода новостей или выхода из меню нажмите соответствующую клавишу:\n 1 - вывод всех новостей\n 2 - поиск по ключевому слову\n 3 - выход")
        keywordVariant = readln()
        when (keywordVariant) {
            "1" -> {
                println("Нажали 1")
                println("Все новости: $rootCity")
                mListOfShowNews.sortBy { it.zonedDateTime }
                mListOfShowNews.forEach {
                    val beautifulDate = beautifulDateFun(it.zonedDateTime)
                    println ("$beautifulDate\n"
                            +"${it.title}\n"
                            + it.description)
                    println("----------------------------------------------------------------------")
                }
            }
            "2" -> {
                println("Нажали 2")
                println("Введите слово для поиска:")
                val wordVariant = readln()
                mListOfShowNews.sortBy { it.zonedDateTime }
                var count = 0
                mListOfShowNews.forEach {
                    if (it.keywords.contains(wordVariant)) {
                        val beautifulDate = beautifulDateFun(it.zonedDateTime)
                        println("$beautifulDate\n"
                                + "${it.title}\n"
                                + it.description)
                        println("----------------------------------------------------------------------")
                        count++
                    }
                }
                if (count == 0)
                    printlnError("Не найдено ни одной новости.")
            }
            "3" -> {
                println("Нажали 3")
                println("Выходим из меню работы с объектом...")
                println("----------------------------------------------------------------------")
                break
            }
            else -> printlnError("Ошибка выбора")
            }
    }


}



