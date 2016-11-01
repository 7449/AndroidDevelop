package com.fuckapp.fragment.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fuckapp.R;
import com.fuckapp.fragment.model.AppModel;
import com.fuckapp.fragment.presenter.AppPresenterImpl;
import com.fuckapp.fragment.view.AppView;
import com.fuckapp.utils.RootUtils;

import java.util.List;

import static com.fuckapp.utils.Constant.HIDE_APP;

/**
 * by y on 2016/10/31
 */

public class AppFragment extends Fragment implements AppView, AppAdapter.OnItemClickListener, RootUtils.RootUtilsInterface {

    private int type = -1;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AppAdapter appAdapter;
    private static final String FRAGMENT_TYPE = "type";
    private AppPresenterImpl presenter;

    public static AppFragment start(int type) {
        AppFragment fragment = new AppFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt(FRAGMENT_TYPE);
        }
        if (type == HIDE_APP) {
            setHasOptionsMenu(false);
        } else {
            setHasOptionsMenu(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_appinfo, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (type == -1) {
            return;
        }
        appAdapter = new AppAdapter(type);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(appAdapter);
        appAdapter.setOnItemClickListener(this);
        presenter = new AppPresenterImpl(this);
        presenter.startApp(type);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                appAdapter.resetCheckbox();
                break;
            case R.id.menu_start:
                if (appAdapter.getTempList() == null) {
                    break;
                }
                RootUtils.execShell(appAdapter.getTempList(), this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void removeAllAdapter() {
        appAdapter.removeAll();
    }

    @Override
    public void setAppInfo(List<AppModel> appInfo) {
        appAdapter.refreshUI(appInfo);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void obtainSuccess() {
        if (getActivity() != null)
            Snackbar.make(getActivity().findViewById(R.id.fragment), getString(R.string.obtain_app_success), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void obtainError() {
        if (getActivity() != null)
            Snackbar.make(getActivity().findViewById(R.id.fragment), getString(R.string.obtain_app_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position, AppModel appInfo) {
        switch (type) {
            case HIDE_APP:
                if (RootUtils.execShell(RootUtils.ADB_COMMAND_UN_HIDE + appInfo.getPkgName())) {
                    Snackbar.make(getActivity().findViewById(R.id.fragment), getString(R.string.exec_shell_unhide_success), Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                appAdapter.refreshCheckBox(position);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
        presenter.nullSubscription();
    }

    @Override
    public void execShellSuccess() {
        if (getActivity() != null) {
            Snackbar.make(getActivity().findViewById(R.id.fragment), getString(R.string.exec_shell_success), Snackbar.LENGTH_SHORT).show();
        }
        appAdapter.clearTempList();
        appAdapter.resetCheckbox();
    }

}
