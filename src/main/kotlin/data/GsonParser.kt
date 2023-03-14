package data

import com.google.gson.Gson
import domain.models.RootModel
import utils.printlnError
import java.io.FileReader

class GsonParser {
    fun parse(parseFile: String): RootModel? {
        return try {
            val reader: FileReader = FileReader(parseFile)
            val rootModel: RootModel? =  Gson().fromJson<RootModel>(reader, RootModel::class.java)
            //println(rootModel.toString())
            rootModel
        } catch (e: Exception) {
            // Лучше отсуда не делать вывод, потому что мы уже далеко от UI
            // Нам нужно возвращать результат работы лишь, а затем обрабатывать его
            // через домен в UI, тоже самое для остальных
            printlnError(e.toString())
            null
        }
    }
}
