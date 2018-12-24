package com.bilibilirecommend.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseFragment : Fragment() {

    var mBundle: Bundle? = null

    private var backHandledInterface: BackHandledInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity !is BackHandledInterface) {
            throw ClassCastException("Hosting Activity must implement BackHandledInterface")
        } else {
            this.backHandledInterface = activity as BackHandledInterface?
        }
        mBundle = arguments
    }

    override fun onStart() {
        super.onStart()
        backHandledInterface!!.setSelectedFragment(this)
    }

    //getLayoutInflater(savedInstanceState).inflate(fragment, null);

    abstract fun onBackPressed(): Boolean

    fun isVisibility(view: View): Boolean {
        return view.visibility == View.GONE
    }

    interface BackHandledInterface {
        fun setSelectedFragment(selectedFragment: BaseFragment)
    }
}
