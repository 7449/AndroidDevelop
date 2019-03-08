@file:Suppress("FunctionName")

package com.develop.test

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize

@Parcelize
class TestEntity(private var title: String) : Parcelable

fun Any.Log(message: Any) {
    android.util.Log.d(MainActivity.TAG, message.toString())
}

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val KEY = "MainActivity"
    }

    private lateinit var testList: ArrayList<TestEntity>
    private lateinit var fragment1: TestFragment1
    private lateinit var fragment2: TestFragment2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testList = ArrayList()
        for (i in 0..5) {
            testList.add(TestEntity(i.toString()))
        }

        Log(testList.size)

        fragment1 = TestFragment1.newInstance(Bundle().apply { putParcelableArrayList(MainActivity.KEY, testList) })
        fragment2 = TestFragment2.newInstance(Bundle().apply { putParcelableArrayList(MainActivity.KEY, testList) })

        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragment1,
                fragment1,
                "TestFragment1"
            )
            .add(
                R.id.fragment2,
                fragment2,
                "TestFragment2"
            )
            .commitAllowingStateLoss()

    }

    fun LogMessage() {
        Log("new MainActivity :$testList")
        fragment1.LogMessage()
        fragment2.LogMessage()
    }
}


class TestFragment1 : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle): TestFragment1 = TestFragment1().apply { arguments = bundle }
    }

    lateinit var test1Entity: ArrayList<TestEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test1Entity = arguments?.getParcelableArrayList<TestEntity>(MainActivity.KEY) ?: ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.test_fragment_1, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log("init TestFragment1 :$test1Entity")
        view?.setOnClickListener {
            test1Entity.removeAt(0)
            if (activity is MainActivity) {
                (activity as MainActivity).LogMessage()
            }
        }
    }

    fun LogMessage() {
        Log("new TestFragment1 :$test1Entity")
    }

}

class TestFragment2 : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle): TestFragment2 = TestFragment2().apply { arguments = bundle }
    }

    lateinit var test2Entity: ArrayList<TestEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test2Entity = arguments?.getParcelableArrayList<TestEntity>(MainActivity.KEY) ?: ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.test_fragment_2, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log("init TestFragment2 :$test2Entity")
        view?.setOnClickListener {
            if (activity is MainActivity) {
                (activity as MainActivity).LogMessage()
            }
        }
    }


    fun LogMessage() {
        Log("new TestFragment2 :$test2Entity")
    }

}