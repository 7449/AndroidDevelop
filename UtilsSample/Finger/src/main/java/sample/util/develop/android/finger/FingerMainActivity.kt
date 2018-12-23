package sample.util.develop.android.finger


import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.finger_activity_main.*

@TargetApi(Build.VERSION_CODES.M)
class FingerMainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0
    }

    private lateinit var fingerprintManager: FingerprintManagerCompat
    private lateinit var keyguardManager: KeyguardManager

    private var mSelfCancelled: FingerprintManagerCompat.AuthenticationCallback =
        object : FingerprintManagerCompat.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                //指纹多次验证错误进入这个方法。并且暂时不能调用指纹验证
                Toast(errString.toString() + "  onAuthenticationError")
                showAuthenticationScreen()
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                Toast(helpString.toString() + "   onAuthenticationHelp")
            }

            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Toast("指纹识别成功")
            }


            override fun onAuthenticationFailed() {
                Toast("指纹识别失败")
            }
        }

    private val isFinger: Boolean
        get() {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.USE_FINGERPRINT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast("没有指纹识别权限")
                return false
            }
            Log("有指纹权限")
            if (!fingerprintManager.isHardwareDetected) {
                Toast("没有指纹识别模块")
                return false
            }
            Log("有指纹模块")
            if (!keyguardManager.isKeyguardSecure) {
                Toast("没有开启锁屏密码")
                return false
            }
            Log("已开启锁屏密码")
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast("没有录入指纹")
                return false
            }
            Log("已录入指纹")
            return true
        }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finger_activity_main)
        fingerprintManager = FingerprintManagerCompat.from(this)
        keyguardManager = this.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        fingerBtn.setOnClickListener {
            if (isFinger) {
                startFinger()
            }
        }
    }

    private fun startFinger() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.USE_FINGERPRINT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast("没有指纹识别权限")
            return
        }
        fingerprintManager.authenticate(null, 0, CancellationSignal(), mSelfCancelled, null)
    }


    /**
     * 锁屏密码
     */
    private fun showAuthenticationScreen() {
        val intent = keyguardManager.createConfirmDeviceCredentialIntent("finger", "测试指纹识别")
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            if (resultCode == Activity.RESULT_OK) {
                Toast("识别成功")
            } else {
                Toast("识别失败")
            }
        }
    }

    fun Toast(`object`: Any) {
        Toast.makeText(applicationContext, `object`.toString() + "", Toast.LENGTH_LONG).show()
    }

    fun Log(`object`: Any) {
        android.util.Log.i("finger", `object`.toString() + "")
    }


}
