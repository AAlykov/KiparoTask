package data

import domain.DataInterface
import domain.models.RootModel
import kotlinx.coroutines.runBlocking
import utils.*



class RepositoryImpl: DataInterface {

    override fun getData(formatVariant: String): RootModel? {
        when (formatVariant) {
            HTTP_JSON ->  {
                downloadData(HTTP_JSON, FILE_NAME_JSON)
                return GsonParser().parse(FILE_NAME_JSON)
            }
            HTTP_XML ->  {
                downloadData(HTTP_XML, FILE_NAME_XML)
                val xmlParser = XmlParser()
                val doc = xmlParser.buildDocument(FILE_NAME_XML)
                return if (doc != null) {
                    xmlParser.parseXML(doc)
                } else {
                    null
                }
            }
            else -> printlnError("Неизвестный формат $formatVariant")
        }
        return null
    }

    private fun downloadData(path: String, downloadFile: String ) {

        runBlocking {
            DownloadData(path, downloadFile).downloadData()
        }
    }
}