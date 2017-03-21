package com.example.y.fingerdemo;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    private FingerprintManagerCompat fingerprintManager;
    private KeyguardManager keyguardManager;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mFingerBtn = (Button) findViewById(R.id.finger_btn);
        fingerprintManager = FingerprintManagerCompat.from(this);
        keyguardManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);

        mFingerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFinger()) {
                    startFinger();
                }
            }
        });
    }

    public boolean isFinger() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast("没有指纹识别权限");
            return false;
        }
        Log("有指纹权限");
        if (!fingerprintManager.isHardwareDetected()) {
            Toast("没有指纹识别模块");
            return false;
        }
        Log("有指纹模块");
        if (!keyguardManager.isKeyguardSecure()) {
            Toast("没有开启锁屏密码");
            return false;
        }
        Log("已开启锁屏密码");
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            Toast("没有录入指纹");
            return false;
        }
        Log("已录入指纹");
        return true;
    }

    FingerprintManagerCompat.AuthenticationCallback mSelfCancelled = new FingerprintManagerCompat.AuthenticationCallback() {

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //指纹多次验证错误进入这个方法。并且暂时不能调用指纹验证
            Toast(errString + "  onAuthenticationError");
            showAuthenticationScreen();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            Toast(helpString + "   onAuthenticationHelp");
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Toast("指纹识别成功");
        }


        @Override
        public void onAuthenticationFailed() {
            Toast("指纹识别失败");
        }
    };


    public void startFinger() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast("没有指纹识别权限");
            return;
        }
        fingerprintManager.authenticate(null, 0, new CancellationSignal(), mSelfCancelled, null);
    }


    /**
     * 锁屏密码
     */
    private void showAuthenticationScreen() {
        Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别");
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            if (resultCode == RESULT_OK) {
                Toast("识别成功");
            } else {
                Toast("识别失败");
            }
        }
    }

    public void Toast(Object object) {
        Toast.makeText(getApplicationContext(), object + "", Toast.LENGTH_LONG).show();
    }

    public void Log(Object object) {
        android.util.Log.i("finger", object + "");
    }
}
