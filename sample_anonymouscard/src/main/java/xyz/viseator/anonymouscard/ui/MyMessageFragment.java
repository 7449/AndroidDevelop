package xyz.viseator.anonymouscard.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.data.UserInfo;

/**
 * Created by yanhao on 16-12-23.
 */

public class MyMessageFragment extends Fragment {
    @BindView(R.id.sweet)
    public TextView good;
    @BindView(R.id.negative)
    public TextView negative;
    @BindView(R.id.support)
    public TextView support;
    private UserInfo userInfo;

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mymessage_fragment, container, false);
        ButterKnife.bind(this, view);
        support.setText("10");
        negative.setText("0");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        good.setText(String.valueOf(userInfo.getCandys()));
    }
}
