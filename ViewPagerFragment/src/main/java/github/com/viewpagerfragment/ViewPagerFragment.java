package github.com.viewpagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * by y on 2016/10/5.
 */

public class ViewPagerFragment extends Fragment {


    private TextView textView;
    private int index;

    public static ViewPagerFragment startFragment(int position) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (null != arguments) {
            index = arguments.getInt("index");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_viewpager, null);
        textView = (TextView) inflate.findViewById(R.id.tv);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView.setText(String.valueOf(index));
    }
}
