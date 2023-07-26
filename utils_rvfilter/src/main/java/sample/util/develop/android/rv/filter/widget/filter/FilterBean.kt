package sample.util.develop.android.rv.filter.widget.filter

class FilterBean {
    var type: Int = 0
    var titleType: String = ""
    var content: String = ""
    var isSelect: Boolean = false

    constructor(type: Int) {
        this.type = type
    }

    constructor(type: Int, titleType: String) {
        this.type = type
        this.titleType = titleType
    }

    constructor(type: Int, titleType: String, content: String) {
        this.type = type
        this.titleType = titleType
        this.content = content
    }

    constructor(type: Int, titleType: String, content: String, isSelect: Boolean) {
        this.type = type
        this.titleType = titleType
        this.content = content
        this.isSelect = isSelect
    }
}
