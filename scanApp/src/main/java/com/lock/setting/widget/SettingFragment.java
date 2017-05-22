package com.lock.setting.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.lock.R;
import com.lock.main.Constant;
import com.lock.setting.model.SettingBean;
import com.lock.setting.presenter.SettingPresenter;
import com.lock.setting.presenter.SettingPresenterImpl;
import com.lock.setting.view.SettingView;
import com.lock.setting.widget.dialog.AlterPassWordDialog;
import com.lock.setting.widget.dialog.DeleteAppDialog;
import com.lock.setting.widget.dialog.ExitAppDialog;

import java.util.ArrayList;
import java.util.List;

import framework.base.BaseFragment;
import framework.base.BaseRVAdapter;
import framework.sql.greendao.GreenDaoDbUtils;
import framework.utils.AppUtils;
import framework.utils.PackageUtils;
import framework.utils.SPUtils;
import framework.utils.UIUtils;

/**
 * by y on 2017/2/15
 */

public class SettingFragment extends BaseFragment
        implements SettingView,
        BaseRVAdapter.OnItemClickListener<SettingBean>,
        ExitAppDialog.ExitAppListener,
        AlterPassWordDialog.AlterPassWordListener {

    private RecyclerView mRecyclerView;
    private SettingPresenter mPresenter;
    private ExitInterface mExitInterface = null;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mExitInterface = (ExitInterface) context;
    }

    @Override
    protected void initById() {
        mRecyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        mPresenter = new SettingPresenterImpl(this);
        List<SettingBean> list = new ArrayList<>();
        list.add(new SettingBean(getString(R.string.setting_clear_setting)));
        list.add(new SettingBean(getString(R.string.setting_delete_app_name)));
        list.add(new SettingBean(getString(R.string.setting_alter_pass_word)));
        list.add(new SettingBean(getString(R.string.setting_exit)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SettingAdapter adapter = new SettingAdapter(list);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }


    @Override
    public void onItemClick(int position, SettingBean appInfo) {
        mPresenter.itemClick(position);
    }

    @Override
    public void exitApp() {
        ExitAppDialog.newInstance().show(getChildFragmentManager(), Constant.FRAGMENT_TAG);
    }

    @Override
    public void alterPassWord() {
        AlterPassWordDialog.newInstance().show(getChildFragmentManager(), Constant.FRAGMENT_TAG);
    }

    @Override
    public void deleteApp() {
        if (AppUtils.isEmpty(GreenDaoDbUtils.getNormalAll())) {
            UIUtils.SnackBar(getView(R.id.rootView), getString(R.string.setting_delete_app_empty));
        } else {
            DeleteAppDialog.newInstance().show(getChildFragmentManager(), Constant.FRAGMENT_TAG);
        }
    }

    @Override
    public void clearSetting() {
        PackageUtils.startSettingApp(getActivity(), Constant.APP_PACKAGE_NAME);
    }

    @Override
    public void onExitApp() {
        if (mExitInterface != null) {
            mExitInterface.exit();
        }
    }

    @Override
    public void onAlterPassWord(String newPassWord) {
        if (TextUtils.isEmpty(newPassWord)) {
            UIUtils.SnackBar(getView(R.id.rootView), getString(R.string.setting_alter_pass_word_empty));
        } else {
            SPUtils.updatePassWord(newPassWord);
            UIUtils.SnackBar(getView(R.id.rootView), getString(R.string.setting_alter_pass_word_success));
        }
    }


    public interface ExitInterface {
        void exit();
    }
}
