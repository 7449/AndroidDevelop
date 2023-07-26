@file:Suppress("NOTHING_TO_INLINE")

package com.video.main.net

import androidx.annotation.Keep

object VideoUiType {
    const val LANG = 0
    const val TAGS = 1
}

@Keep
data class ListBaseEntity(val image: String = "", val title: String = "", val url: String = "")

@Keep
data class TagEntity(val url: String = "", val title: String = "")

@Keep
data class PrevEntity(
    val id: Long = 0,
    val u: String = "",
    val i: String = "",
    val tf: String = "",
    val t: String = "",
    val d: String = "",
    val r: String = "",
    val n: String = "",
    val pu: String = "",
    val pn: String = ""
)

@Keep
data class UrlEntity(
    var lowUrl: String = "",
    var highUrl: String = "",
    var hlsUrl: String = "",
    var thumbUrl: String = "",
    var thumbUrl169: String = "",
    var title: String = ""
)

@Keep
data class VideoDetailEntity(
    val tagEntity: List<TagEntity>,
    val prevEntity: List<PrevEntity>,
    val urlEntity: UrlEntity
)

@Keep
data class VideoListEntity(val tagEntity: List<TagEntity>, val listEntity: List<ListBaseEntity>)

@Keep
data class AssetsUrlBean(val type: String = "", val url: String = "")

inline fun UrlEntity.playUrl(index: Int): String {
    return when (index) {
        0 -> lowUrl
        1 -> highUrl
        else -> hlsUrl
    }
}

inline fun UrlEntity.urlString(): String? {
    if (highUrl.isNotEmpty()) {
        return highUrl
    }
    if (lowUrl.isNotEmpty()) {
        return lowUrl
    }
    return null
}