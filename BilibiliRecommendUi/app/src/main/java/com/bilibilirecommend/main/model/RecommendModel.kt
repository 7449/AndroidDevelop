package com.bilibilirecommend.main.model

import com.google.gson.annotations.SerializedName

class RecommendModel {

    var code: Int = 0
    lateinit var result: MutableList<ResultBean>

    class ResultBean {

        var type: String = ""
        lateinit var head: HeadBean
        lateinit var body: MutableList<BodyBean>

        class HeadBean {
            var param: String = ""
            @SerializedName("goto")
            var gotoX: String = ""
            var style: String = ""
            var title: String = ""
            var count: Int = 0
        }

        class BodyBean {
            var title: String = ""
            var style: String = ""
            var cover: String = ""
            var param: String = ""
            @SerializedName("goto")
            var gotoX: String = ""
            var width: Int = 0
            var height: Int = 0
            var play: String = ""
            var danmaku: String = ""
            var area: String = ""
            var up: String = ""
            var online: Int = 0
            var desc1: String = ""
            var status: Int = 0
        }
    }
}
