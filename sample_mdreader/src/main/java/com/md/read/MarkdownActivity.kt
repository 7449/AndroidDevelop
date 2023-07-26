package com.md.read

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import br.tiagohm.markdownview.MarkdownView
import br.tiagohm.markdownview.css.styles.Github

class MarkdownActivity : AppCompatActivity() {

    private val mStyle = Github()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.extras?.getString(NAME, "name") ?: ""
        val finderName = intent.extras?.getString(FINDER, "finder") ?: ""
        title = name
        val markdownView = MarkdownView(this)
        markdownView.addStyleSheet(mStyle)
        markdownView.loadMarkdownFromAsset(if (TextUtils.isEmpty(finderName)) name else "$finderName/$name")
        setContentView(markdownView)
    }

}