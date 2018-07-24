package xyz.viseator.anonymouscard.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.adapter.ViewPagerAdapter;
import xyz.viseator.anonymouscard.data.ConvertData;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.DataStore;
import xyz.viseator.anonymouscard.data.SaveData;
import xyz.viseator.anonymouscard.data.UDPDataPackage;
import xyz.viseator.anonymouscard.data.UserInfo;
import xyz.viseator.anonymouscard.network.ComUtil;
import xyz.viseator.anonymouscard.network.TcpServer;
import xyz.viseator.anonymouscard.ui.MainFragment;
import xyz.viseator.anonymouscard.ui.MyMessageFragment;

public class MainActivity extends FragmentActivity {
    private static final int SEND_CARD = 1;
    private static final String TAG = "wudi MainActivity";
    private static int cardId;
    private MainFragment mainFragment, mainFragment1;
    private MyMessageFragment mainFragment2;
    private List<Fragment> fragments;
    private ViewPagerAdapter viewPagerAdapter;
    private static UserInfo userInfo;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.main_toolbar_text)
    TextView toolbarTitle;

    public static ArrayList<DataPackage> dataPackages;
    private static ArrayList<UDPDataPackage> udpDataPackages;

    private static ComUtil comUtil;
    private TcpServer tcpServer;
    private static DataStore dataStore;
    public static Context context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ComUtil.BROADCAST_PORT:
                    UDPDataPackage udpDataPackage = (UDPDataPackage) ConvertData.byteToObject((byte[]) msg.obj);
                    if (getDataById(udpDataPackage.getId()) == null) {
                        udpDataPackages.add(udpDataPackage);
                        dataStore.setDataPackages(dataPackages);
                        mainFragment.recyclerView.getAdapter().notifyDataSetChanged();
                        Log.d(TAG, "handleMessage: Receive UDP");
                    }
                    break;
                case TcpServer.RECEIVE_REQUEST:
                    Toast.makeText(MainActivity.this, "贺卡被打开，收到糖果一个", Toast.LENGTH_SHORT).show();
                    userInfo.setCandys(userInfo.getCandys() + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs= this.getSharedPreferences("share",MODE_PRIVATE);
        boolean isFirst=prefs.getBoolean("isFirst",true);
        SharedPreferences.Editor editor=prefs.edit();
        if (isFirst){
            Intent intent=new Intent(MainActivity.this,BeginActivity.class);
            editor.putBoolean("isFirst",false);
            editor.commit();
            startActivity(intent);
        }else{
            ButterKnife.bind(MainActivity.this);
            getDataFromFile();
            dataStore = new DataStore();
            dataStore.setDataPackages(dataPackages);
            init();
            initViews();
            context = MainActivity.this;
        }

    }

    private void initViews() {
        fragments = new ArrayList<>();
        mainFragment = new MainFragment();
        mainFragment.setFragmentId(1);
        mainFragment1 = new MainFragment();
        mainFragment1.setFragmentId(2);
        mainFragment2 = new MyMessageFragment();
        mainFragment2.setUserInfo(userInfo);

        fragments.add(mainFragment);
        fragments.add(mainFragment1);
        fragments.add(mainFragment2);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), MainActivity.this, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        View view1 = getLayoutInflater().inflate(R.layout.tab_view, null);
        ((ImageView) view1.findViewById(R.id.tab_image)).setImageResource(R.drawable.left_icon_selector);
        View view2 = getLayoutInflater().inflate(R.layout.tab_view, null);
        ((ImageView) view2.findViewById(R.id.tab_image)).setImageResource(R.drawable.center_icon_selector);
        View view3 = getLayoutInflater().inflate(R.layout.tab_view, null);
        ((ImageView) view3.findViewById(R.id.tab_image)).setImageResource(R.drawable.right_icon_selector);

        toolbarTitle.setText("一起抢贺卡吧");
        tabLayout.getTabAt(0).setCustomView(view1);
        tabLayout.getTabAt(1).setCustomView(view2);
        tabLayout.getTabAt(2).setCustomView(view3);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        toolbarTitle.setText("一起抢贺卡吧");
                        break;
                    case 1:
                        toolbarTitle.setText("我的贺卡");
                        break;
                    case 2:
                        toolbarTitle.setText("我的女装");
                        break;
                    default:

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick(R.id.float_button)
    public void clickFloatButton() {
        Intent intent = new Intent(this, SendNewCardActivity.class);
        intent.putExtra("cardId", cardId);
        startActivityForResult(intent, SEND_CARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_CARD) {
            if (resultCode == RESULT_OK) {
                DataPackage dataPackage = (DataPackage) data.getSerializableExtra("data");
                if (dataPackage != null) {
                    Log.d(TAG, "onActivityResult: Got Data");
                    for (int i = 0; i < 5; i++)
                        comUtil.broadCast(ConvertData.objectToByte(new UDPDataPackage(dataPackage)));
                    dataPackages.add(dataPackage);
                    udpDataPackages.add(new UDPDataPackage(dataPackage));
                    dataStore.setDataPackages(dataPackages);
                    cardId++;
                    userInfo.setCandys(userInfo.getCandys() - 5);
                }
            }
        }
    }


    public UDPDataPackage getDataById(String id) {
        for (UDPDataPackage udpDataPackage : udpDataPackages) {
            if (Objects.equals(udpDataPackage.getId(), id)) {
                return udpDataPackage;
            }
        }
        return null;
    }


    public ArrayList<UDPDataPackage> getUdpDataPackages() {
        return udpDataPackages;
    }

    public ArrayList<DataPackage> getDataPackages() {
        return dataPackages;
    }

    private void init() {
        tcpServer = new TcpServer(dataStore);
        tcpServer.startServer(handler);
        comUtil = new ComUtil(handler);
        comUtil.startRecieveMsg();

    }

    public UserInfo getUserInfo() {
        return userInfo;
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Write");
        SaveData.writeToFile(this, dataPackages, "dataPackages");
        SaveData.writeToFile(this, udpDataPackages, "udpDataPackages");
        SaveData.writeToFile(this, cardId, "cardId");
        SaveData.writeToFile(this, userInfo, "userInfo");
    }

    private void getDataFromFile() {
        ArrayList<DataPackage> dataPackages1 = (ArrayList<DataPackage>) SaveData.readFromFile(this, "dataPackages");
        if (dataPackages1 == null) {
            Log.d(TAG, "onResume: init");
            dataPackages = new ArrayList<>();
        } else {
            dataPackages = dataPackages1;
            Log.d(TAG, "onResume: dataPackage" + dataPackages.size());
        }


        ArrayList<UDPDataPackage> udpData = (ArrayList<UDPDataPackage>) SaveData.readFromFile(this, "udpDataPackages");
        if (udpData == null) {
            Log.d(TAG, "onResume: init udp");
            udpDataPackages = new ArrayList<>();
        } else {
            udpDataPackages = udpData;
            Log.d(TAG, "onResume: udpDataPackage" + udpDataPackages.size());
        }

        Integer cardNum = (Integer) SaveData.readFromFile(this, "cardId");
        if (cardNum == null) {
            cardId = 0;
            Log.d(TAG, "getDataFromFile: Init Id");
        } else {
            cardId = cardNum;
            Log.d(TAG, "getDataFromFile: id:" + cardId);
        }

        UserInfo userIn = (UserInfo) SaveData.readFromFile(this, "userInfo");
        if (userIn == null) {
            userInfo = new UserInfo();
        } else {
            userInfo = userIn;
        }
    }

    public static void sendDataByUdp(DataPackage dataPackage) {
        comUtil.broadCast(ConvertData.objectToByte(new UDPDataPackage(dataPackage)));
        dataPackages.add(dataPackage);
        udpDataPackages.add(new UDPDataPackage(dataPackage));
        dataStore.setDataPackages(dataPackages);
        cardId++;
        userInfo.setCandys(userInfo.getCandys() - 5);
    }
}
