package sample.util.develop.android.dagger.mvp.model

/**
 * by y on 2017/5/31.
 */

class MVPBean {
    var title: String = ""
    var titleImage: String = ""
    var slug: Int = 0
    lateinit var author: Author
    class Author {
        var profileUrl: String = ""
        var bio: String = ""
        var name: String = ""
    }
}
