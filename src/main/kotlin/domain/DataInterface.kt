package domain

import domain.models.RootModel

interface DataInterface {
    //fun getJsonData(): RootModel?
    //fun getXmlData(): RootModel?

    fun getData(formatVariant: String): RootModel?
}


