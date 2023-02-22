package domain.usecase

import domain.DataInterface
import domain.models.RootModel

class GetDataUseCase(private val dataInterface: DataInterface) {

    fun getData(formatVariant: String): RootModel? {
        return dataInterface.getData(formatVariant)
    }

}
