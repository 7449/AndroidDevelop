package com.kotlin.x.sample

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.kotlin.x.LogW

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val testFragment = TestFragment()
        supportFragmentManager.beginTransaction().add(android.R.id.content, testFragment).commit()
    }

    class TestFragment : Fragment() {
        override fun onAttach(context: Context?) {
            super.onAttach(context)
            LogW()
        }
    }
}
