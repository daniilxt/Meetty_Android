package ru.daniilxt.feature.domain.model

data class SimpleUserInfo(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val avatarUri: String = "https://sun9-west.userapi.com/sun9-50/s/v1/if2/NTo9XA_NKv1OIl1J12FYJcNpKHPOLmWblmIFWS-BymD33CRS8GCybjFlLan2qbN-QldyPhvc5_aaq9sFhqR22K9D.jpg?size=604x453&quality=95&type=album",
    val sex: String = ""
) {
    fun getFullUserName() = "$firstName $lastName"
}
