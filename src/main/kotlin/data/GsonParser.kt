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
            printlnError(e.toString())
            null
        }
    }
}