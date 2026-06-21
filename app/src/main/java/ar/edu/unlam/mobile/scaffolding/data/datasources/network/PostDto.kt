package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Int,

    @SerializedName("message") val message: String,

    @SerializedName("parent_id") val parentId: Int,

    @SerializedName("author") val author: String,

    @SerializedName("avatar_url") val avatarUrl: String,

    @SerializedName("likes") val likes: Int,

    @SerializedName("liked") val liked: Boolean,

    @SerializedName("date") val date: String,
)
