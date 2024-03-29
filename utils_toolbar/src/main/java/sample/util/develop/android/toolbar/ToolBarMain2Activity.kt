package sample.util.develop.android.toolbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar

/**
 * by y.
 *
 * Description:
 */
class ToolBarMain2Activity : AppCompatActivity() {

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toolbar_activity_main2)
        //        toolbar.setOverflowIcon(null);
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_main_menu, menu)
        val item = menu.findItem(R.id.main_title)
        item.actionView?.setOnClickListener { onOptionsItemSelected(item) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = item.itemId
        if (i == R.id.main_title) {
            toolbar.post { toolbar.showOverflowMenu() }
        } else if (i == R.id.main_title_1 || i == R.id.main_title_2) {
            val item1 = toolbar.menu.findItem(R.id.main_title)
            val viewById = item1.actionView?.findViewById<AppCompatTextView>(R.id.actionTv)
            viewById?.text = item.title
            toolbar.menu.findItem(R.id.main_title).title = item.title
        }
        return super.onOptionsItemSelected(item)
    }
}
