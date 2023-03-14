package domain.usecase

import domain.DataInterface
import domain.models.RootModel

class GetDataUseCase(private val dataInterface: DataInterface) {

    // лучше привыкать именовать метод execute()
    // потому что usecase содержит всегда один публичный метод 
    // а суть ясна из названия
    fun getData(formatVariant: String): RootModel? {
        return dataInterface.getData(formatVariant)
    }

}
