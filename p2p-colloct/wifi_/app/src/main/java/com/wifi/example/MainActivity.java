package com.wifi.example;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wifi.example.control.MasterControlManager;
import com.wifi.example.control.SlaveControlManager;
import com.wifi.example.utils.IpUtil;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvIp;
    private TextView tvMsg;

    private MasterControlManager masterControlManager = new MasterControlManager();
    private SlaveControlManager slaveControlManager = new SlaveControlManager();
    private boolean isMaster = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.master).setOnClickListener(this);
        findViewById(R.id.slave).setOnClickListener(this);
        tvIp = (TextView) findViewById(R.id.tv_ip);
        tvIp.setOnClickListener(this);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        findViewById(R.id.send_msg).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.send_file).setOnClickListener(this);
        refreshIP();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.master:
                masterControlManager.start();
                slaveControlManager.stop();
                isMaster = true;
                break;
            case R.id.slave:
                slaveControlManager.start();
                masterControlManager.stop();
                isMaster = false;
                break;
            case R.id.tv_ip:
                refreshIP();
                break;
            case R.id.send_msg:
                if (isMaster) {
                    masterControlManager.send("接收到来自master的消息:" + System.currentTimeMillis());
                } else {
                    slaveControlManager.send("接收到来自Slave的消息" + System.currentTimeMillis());
                }
                break;
            case R.id.stop:
                masterControlManager.stop();
                slaveControlManager.stop();
                break;
            case R.id.send_file:
//                slaveControlManager.sendFile();
                break;

        }
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        slaveControlManager.setControlManagerCallback(
                new SlaveControlManager.SlaveControlManagerCallback() {
                    @Override
                    public void onFoundMaster(String masterIp, JSONObject masterInfo) {
                        tvMsg.setText(masterIp + " " + masterInfo.toString());
                    }

                    @Override
                    public void onConnectedMaster(String masterIp, Exception ex) {
                        tvMsg.setText(masterIp + " connected");
                    }

                    @Override
                    public void onDisconnectMaster(String masterIp, Exception ex) {
                        tvMsg.setText(masterIp + " disconnected");
                    }

                    @Override
                    public void onReceiveMessage(String message) {
                        tvMsg.setText(message);
                    }
                });

        masterControlManager.setControlManagerCallback(
                new MasterControlManager.MasterControlManagerCallback() {
                    @Override
                    public void onServerError(Exception ex) {
                        tvMsg.setText("server error: " + ex.getLocalizedMessage());
                    }

                    @Override
                    public void onClientConnected(String clientSocket) {
                        tvMsg.setText("client: " + clientSocket);
                    }

                    @Override
                    public void onClientDisconnect(String clientSocket) {
                        tvMsg.setText("disconnected: " + clientSocket);
                    }

                    @Override
                    public void onMessageReceive(String clientSocket, String message) {
                        tvMsg.setText(message);
                    }

                    @Override
                    public void onReceiveFile(String clientSocket, String filePath) {
                        Toast.makeText(MainActivity.this, filePath, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void refreshIP() {
        tvIp.setText(new StringBuffer().append("本机IP: ").append(IpUtil.getHostIP()));
    }


    @Override
    protected void onDestroy() {
        slaveControlManager.stop();
        masterControlManager.stop();
        super.onDestroy();
    }
}
