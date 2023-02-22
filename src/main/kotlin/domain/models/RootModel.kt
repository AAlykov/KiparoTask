package domain.models

data class RootModel (
    val location: String,
    val name: String,
    val news: List<NewsModel>
)