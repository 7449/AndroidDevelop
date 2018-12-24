package com.bilibilirecommend.main.model

class RecommendCompat {
    companion object {
        const val TYPE_BANNER = -1
        const val TYPE_HEADER = 0
        const val TYPE_FOOTER = 1
        const val TYPE_ITEM = 2
        const val TYPE_WEB_LINK = 3
        const val TYPE_ACTIVITY = 4
    }

    var position: Int = 0
    var newPosition: Int = 0
    var type: Int = 0
    var itemPosition: Int = 0

    constructor(position: Int, type: Int, newPosition: Int) {
        this.position = position
        this.type = type
        this.newPosition = newPosition
    }

    constructor(position: Int, type: Int, newPosition: Int, itemPosition: Int) {
        this.position = position
        this.newPosition = newPosition
        this.type = type
        this.itemPosition = itemPosition
    }

}
