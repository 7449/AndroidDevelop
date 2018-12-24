package sample.view.develop.android.dotted.line

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.dotted_line_activity_by_naive.*

class DottedLineByNaiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dotted_line_activity_by_naive)
        val dlAnimView = DLAnimView(this)
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        rel.addView(dlAnimView, layoutParams)
    }
}
