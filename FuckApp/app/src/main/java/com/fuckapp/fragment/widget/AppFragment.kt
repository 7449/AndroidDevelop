package com.fuckapp.fragment.widget

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.fuckapp.R
import com.fuckapp.fragment.model.AppModel
import com.fuckapp.fragment.presenter.AppPresenterImpl
import com.fuckapp.fragment.view.AppView
import com.fuckapp.utils.Constant
import com.fuckapp.utils.RootUtils
import kotlinx.android.synthetic.main.fragment_appinfo.*

/**
 * by y on 2016/10/31
 */

class AppFragment : Fragment(), AppView, AppAdapter.OnItemClickListener, RootUtils.RootUtilsInterface {
    companion object {
        private const val FRAGMENT_TYPE = "type"
        fun start(type: Int): AppFragment {
            val fragment = AppFragment()
            val bundle = Bundle()
            bundle.putInt(FRAGMENT_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var type = -1
    private lateinit var appAdapter: AppAdapter
    private lateinit var presenter: AppPresenterImpl
    private lateinit var mActivity: Activity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments
        if (arguments != null) {
            type = arguments.getInt(FRAGMENT_TYPE)
        }
        if (type == Constant.HIDE_APP) {
            setHasOptionsMenu(false)
        } else {
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appinfo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (type == -1) {
            return
        }
        appAdapter = AppAdapter(type)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        recyclerView.adapter = appAdapter
        appAdapter.setOnItemClickListener(this)
        presenter = AppPresenterImpl(this)
        presenter.startApp(type)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_clear -> appAdapter.resetCheckbox()
            R.id.menu_start -> RootUtils.execShell(appAdapter.tempList, this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun removeAllAdapter() {
        appAdapter.removeAll()
    }

    override fun setAppInfo(appInfo: List<AppModel>) {
        appAdapter.refreshUI(appInfo)
    }

    override fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    override fun obtainSuccess() {
        Snackbar.make(mActivity.findViewById(R.id.fragment), getString(R.string.obtain_app_success), Snackbar.LENGTH_SHORT).show()
    }

    override fun obtainError() {
        Snackbar.make(mActivity.findViewById(R.id.fragment), getString(R.string.obtain_app_error), Snackbar.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int, appInfo: AppModel) {
        when (type) {
            Constant.HIDE_APP -> if (RootUtils.execShell(RootUtils.ADB_COMMAND_UN_HIDE + appInfo.pkgName)) {
                Snackbar.make(mActivity.findViewById(R.id.fragment), getString(R.string.exec_shell_unhide_success), Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(mActivity.findViewById(R.id.fragment), getString(R.string.exec_shell_unhide_error), Snackbar.LENGTH_SHORT).show()

            }
            else -> appAdapter.refreshCheckBox(position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun execShellSuccess() {
        Snackbar.make(mActivity.findViewById(R.id.fragment), getString(R.string.exec_shell_success), Snackbar.LENGTH_SHORT).show()
        appAdapter.clearTempList()
        appAdapter.resetCheckbox()
    }

    override fun execShellError() {
        Snackbar.make(mActivity.findViewById(R.id.fragment), getString(R.string.exec_shell_error), Snackbar.LENGTH_SHORT).show()
        appAdapter.clearTempList()
        appAdapter.resetCheckbox()
    }
}
