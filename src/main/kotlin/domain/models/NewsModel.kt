package domain.models


data class NewsModel (
    val id: String,
    val title: String,
    val date: String,
    val description: String,
    val visible: String,
    val keywords: List<String>
)

