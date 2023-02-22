package data

import domain.models.NewsModel
import domain.models.RootModel
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.SAXException
import utils.TAG_LOCATION
import utils.TAG_NAME
import utils.TAG_NEWS
import utils.printlnError
import java.io.IOException
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class XmlParser() {

    fun buildDocument(parseFile: String): Document? {
        val builderFactory = DocumentBuilderFactory.newInstance()
        return try {
            val documentBuilder = builderFactory.newDocumentBuilder()
            val parseDoc = documentBuilder.parse(parseFile)
            println("Преобразовали файл $parseFile в документ.")
            parseDoc
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: SAXException) {
            e.printStackTrace()
            null
        }
    }

    fun parseXML(doc: Document): RootModel? {
        //val doc = buildDocument(FILE_NAME_XML)
        val rootNode= doc.firstChild ?: return null
        //println("rootNode = ${rootNode.nodeName}")

        var locationName: String = ""
        var mName: String = ""
        var newsModelList = mutableListOf<NewsModel>()

        var newsNode: Node? = null

        val rootNodeChildList = rootNode.childNodes
        for (i in 0 until rootNodeChildList.length) {
            if (rootNodeChildList.item(i).nodeType != Node.ELEMENT_NODE) {
                continue
            } else {
                //println("rootNodeChildList-$i = ${rootNodeChildList.item(i).nodeName}")
                when(rootNodeChildList.item(i).nodeName) {
                    TAG_LOCATION -> {
                        locationName = rootNodeChildList.item(i).textContent
                        //println("location = $locationName")
                    }
                    TAG_NAME -> {
                        mName = rootNodeChildList.item(i).textContent
                        //println("mName = $mName")
                    }
                    TAG_NEWS -> {
                        newsNode = rootNodeChildList.item(i)
                    }
                }
            }
        }

        if (newsNode != null) {
            //val newsList: List<News> = mutableListOf<News>()
            val newsNodeChildList = newsNode.childNodes
            for (i in 0 until newsNodeChildList.length) {
                if (newsNodeChildList.item(i).nodeType != Node.ELEMENT_NODE) {
                    continue
                }
                //println("newsNodeChildList-$i = ${newsNodeChildList.item(i).nodeName}")
                if (!newsNodeChildList.item(i).nodeName.equals("element")) {
                    continue
                }
                var id: String = ""
                var title: String = ""
                var date: String = ""
                var description: String = ""
                var visible: String = ""
                var keywords = mutableListOf<String>()

                val elementNodeList = newsNodeChildList.item(i).childNodes
                for (j in 0 until elementNodeList.length) {
                    if (elementNodeList.item(j).nodeType != Node.ELEMENT_NODE) {
                        continue
                    }
                    //println("elementNodeList-$j = ${elementNodeList.item(j).nodeName}")
                    when(elementNodeList.item(j).nodeName) {
                        "date" -> {
                            date = elementNodeList.item(j).textContent  //!!!!!!!
                            //println("date = $date")
                        }
                        "description" -> {
                            description = elementNodeList.item(j).textContent  //!!!!!!!
                            //println("description = $description")
                        }
                        "id" -> {
                            id = elementNodeList.item(j).textContent  //!!!!!!!
                            //println("id = $id")
                        }
                        "keywords" -> {
                            val keywordsNodeList = elementNodeList.item(j).childNodes
                            for (k in 0 until keywordsNodeList.length) {
                                if (keywordsNodeList.item(k).nodeType != Node.ELEMENT_NODE) {
                                    continue
                                }
                                if (keywordsNodeList.item(k).nodeName.equals("element")) {
                                    keywords.add(keywordsNodeList.item(k).textContent)
                                }
                            }
                        }
                        "title" -> {
                            title = elementNodeList.item(j).textContent  //!!!!!!!
                            //println("title = $title")
                        }
                        "visible" -> {
                            visible = elementNodeList.item(j).textContent  //!!!!!!!
                            //println("visible = $visible")
                        }
                    }
                }

                val newsModel = NewsModel(id, title, date, description, visible, keywords)
                newsModelList.add(newsModel)

                //println("keywords.toString() = " + keywords.toString())
            }
        } else {
            printlnError("Ошибка разбора узла News")
            return null
        }
        return if (locationName != "" && mName != "" && newsModelList.size > 0) {
            return RootModel(location = locationName, name = mName, news = newsModelList)
        } else {
            null
        }
        //return RootModel(location = locationName, name = mName, newsModels = newsModelList)
    }
}