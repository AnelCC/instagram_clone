package com.anelcc.instagram.data

data class UserData (
    var userId: String? = null,
    var name: String? = null,
    var username: String? = null,
    var imageURL: String? = null,
    var bio: String? = null,
    var following: List<String>? = null,
    ) {
    fun toMap() = mapOf(
        "userID" to userId,
        "name" to name,
        "username" to username,
        "imageUrl" to imageURL,
        "bio" to bio,
        "following" to following
    )
}