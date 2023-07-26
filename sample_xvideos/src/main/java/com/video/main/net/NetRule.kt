package com.video.main.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.regex.Pattern

const val PREV_START = "video_related="
const val PREV_END = ";window.wpn_categories"
const val PREV_REGEX = "video_related="

const val IMAGE_SUFFIX = ".THUMBNUM.jpg"

const val IMAGE_NEW_SUFFIX = ".24.jpg"

const val URL_LOW = "setVideoUrlLow('"
const val URL_HIGH = "setVideoUrlHigh('"
const val URL_HLS = "setVideoHLS('"
const val URL_THUMB = "setThumbUrl('"
const val URL_THUMB169 = "setThumbUrl169('"
const val URL_END = "')"

private fun Element.imageRule(): String {
    val attr = select("div.thumb").select("img[data-src]").attr("data-src")
    if (attr.endsWith(IMAGE_SUFFIX)) {
        return attr.replace(IMAGE_SUFFIX, IMAGE_NEW_SUFFIX)
    }
    return attr
}

fun Document.listRule(): VideoListEntity {
    val tagEntity = ArrayList<TagEntity>()
    val tagsElements = select("div#search-associates").select("a[href]")
    for (tagsElement in tagsElements) {
        tagEntity.add(TagEntity(tagsElement.attr("abs:href"), tagsElement.text()))
    }
    val thubmInsideElements = select("div.thumb-block")
    val listEntity = ArrayList<ListBaseEntity>()
    for (element in thubmInsideElements) {
        listEntity.add(
            ListBaseEntity(
                element.imageRule(),
                element.select("div.thumb-under").select("a[title]").attr("title"),
                element.select("div.thumb").select("a[href]").attr("abs:href")
            )
        )
    }
    return VideoListEntity(tagEntity, listEntity)
}

fun Document.detailRule(): VideoDetailEntity {
    val html = this.html()
    val videoDetailUrlEntity = UrlEntity()
    videoDetailUrlEntity.title = title()
    val pattern = Pattern.compile("set([^(]+)\\(([\"'])(http.+?)\\2\\)")
    val matcher = pattern.matcher(html)
    if (matcher.find()) {
        matcher.reset()
        while (matcher.find()) {
            val group = matcher.group(0)
            group?.let {
                if (it.contains(URL_LOW)) {
                    videoDetailUrlEntity.lowUrl = it.replace(URL_LOW, "").replace(URL_END, "")
                }
                if (it.contains(URL_HIGH)) {
                    videoDetailUrlEntity.highUrl = it.replace(URL_HIGH, "").replace(URL_END, "")
                }
                if (it.contains(URL_HLS)) {
                    videoDetailUrlEntity.hlsUrl = it.replace(URL_HLS, "").replace(URL_END, "")
                }
                if (it.contains(URL_THUMB)) {
                    videoDetailUrlEntity.thumbUrl = it.replace(URL_THUMB, "").replace(URL_END, "")
                }
                if (it.contains(URL_THUMB169)) {
                    videoDetailUrlEntity.thumbUrl169 =
                        it.replace(URL_THUMB169, "").replace(URL_END, "")
                }
            }
        }
    }

    var prevList = ArrayList<PrevEntity>()
    val startIndexOf = html.indexOf(PREV_START)
    val lastIndexOf = html.lastIndexOf(PREV_END)
    if (lastIndexOf != -1 && startIndexOf != -1) {
        prevList = try {
            Gson().fromJson(
                html.substring(startIndexOf, lastIndexOf).replace(PREV_REGEX, ""),
                object : TypeToken<ArrayList<PrevEntity>>() {}.type
            )
        } catch (e: Exception) {
            ArrayList()
        }
    }
    val tagList = ArrayList<TagEntity>()
    val tagsElements = select("li.sub-list").select("a[href]")
    for (tagsElement in tagsElements) {
        if (Net.XVS_LANG_TAGS == tagsElement.attr("abs:href")) {
            continue
        }
        tagList.add(TagEntity(tagsElement.attr("abs:href"), tagsElement.text()))
    }
    return VideoDetailEntity(tagList, prevList, videoDetailUrlEntity)
}