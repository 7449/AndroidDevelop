package sample.util.develop.android.image.select

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import sample.util.develop.android.image.select.crop.CropCallBack
import sample.util.develop.android.image.select.crop.CropManager

class ImageSelectMainActivity : AppCompatActivity(), CropCallBack,
    EasyPermissions.PermissionCallbacks {

    private val btnAlbum by lazy { findViewById<View>(R.id.btnAlbum) }
    private val btnCamera by lazy { findViewById<View>(R.id.btnCamera) }

    override val cropActivity: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_select_activity_main)
        CropManager.instance.init(this)
        methodRequiresTwoPermission()
    }

    @AfterPermissionGranted(112)
    private fun methodRequiresTwoPermission() {
        val perms = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            init()
        } else {
            EasyPermissions.requestPermissions(this, "请求权限", 112, *perms)
        }
    }

    override fun onCropSuccess(uri: Uri?) {
        Toast.makeText(applicationContext, uri?.path, Toast.LENGTH_SHORT).show()
    }

    override fun onCropCancel() {
        Toast.makeText(applicationContext, "取消", Toast.LENGTH_SHORT).show()
    }

    override fun onCropError(errorMessage: String?) {
        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        init()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CropManager.instance.onActivityResult(requestCode, resultCode, data)
    }

    private fun init() {
        btnAlbum.setOnClickListener { CropManager.instance.openAlbum() }
        btnCamera.setOnClickListener { CropManager.instance.openCamera() }
    }

}
