package greendao.develop.android.multi.table

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MultiTableApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
    }
}
