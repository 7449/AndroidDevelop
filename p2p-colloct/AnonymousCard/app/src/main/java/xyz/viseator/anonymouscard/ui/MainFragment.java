package xyz.viseator.anonymouscard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.activities.CardDetailActivity;
import xyz.viseator.anonymouscard.activities.MainActivity;
import xyz.viseator.anonymouscard.adapter.MainRecyclerViewAdapter;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.UDPDataPackage;
import xyz.viseator.anonymouscard.data.UserInfo;
import xyz.viseator.anonymouscard.network.ComUtil;

import static android.app.Activity.RESULT_OK;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class MainFragment extends Fragment implements MainRecyclerViewAdapter.OnItemClickListener {
    private static final String TAG = "wudi MainFragment";

    @BindView(R.id.main_recyclerView)
    public RecyclerView recyclerView;
    String name;
    private ArrayList<UDPDataPackage> udpDataPackages;
    private ComUtil comUtil;
    private String mId;
    private int fragmentId;

    public int getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fregment, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.hasFixedSize();
        MainRecyclerViewAdapter mainRecyclerViewAdapter;
        if (fragmentId == 1) {
            udpDataPackages = ((MainActivity) getActivity()).getUdpDataPackages();
            mainRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), udpDataPackages);
        } else {
            udpDataPackages = new ArrayList<>();
            for (DataPackage dataPackage : MainActivity.dataPackages) {
                UDPDataPackage udpDataPackage = new UDPDataPackage(dataPackage);
                udpDataPackages.add(udpDataPackage);
            }
            mainRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), udpDataPackages);
        }
        mainRecyclerViewAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mainRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(fragmentId==2){
            udpDataPackages = new ArrayList<>();
            for (DataPackage dataPackage : MainActivity.dataPackages) {
                UDPDataPackage udpDataPackage = new UDPDataPackage(dataPackage);
                udpDataPackages.add(udpDataPackage);
            }
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: addDataPackages");
                ((MainActivity) getActivity()).getDataPackages().add(
                        (DataPackage) data.getSerializableExtra("data"));
                Log.d(TAG, "onActivityResult: " + ((MainActivity) getActivity()).getDataPackages().size());
                UserInfo userInfo = ((MainActivity) getActivity()).getUserInfo();
                userInfo.setCandys(userInfo.getCandys() - 1);
            }
        }
    }

    public void sendRequest() {
        Intent intent = new Intent(getContext(), CardDetailActivity.class);
        intent.putExtra("data", ((MainActivity) getActivity()).getDataById(mId));
        startActivityForResult(intent, 11);
    }

    @Override
    public void onItemClickListener(View view, String id) {
        mId = id;
        CustomDialog dialog = new CustomDialog(getContext(), R.style.dialog, MainFragment.this);
        dialog.show();
    }
}
