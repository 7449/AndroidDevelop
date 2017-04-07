package select.image.com.crop;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;

import select.image.com.R;

/**
 * by y on 2017/4/7.
 */

public class CropManager {

    private static final int TYPE_CAMERA = 1111;
    private static final int TYPE_ALBUM = 1112;
    private static final int TYPE_KITKAT_ALBUM = 1113;

    private static final String IMAGE_TYPE = "image/jpeg";
    private static final String IMAGE_TYPE_All = "image/*";

    private CropCallBack cropCallBack = null;
    private Uri imagePath;

    private CropManager() {
    }

    private static final class CropManagerHolder {
        private static final CropManager CROP_MANAGER = new CropManager();
    }

    public static CropManager get() {
        return CropManagerHolder.CROP_MANAGER;
    }

    public CropManager init(@NonNull CropCallBack cropCallBack) {
        this.cropCallBack = cropCallBack;
        return this;
    }

    /**
     * 打开相机
     */
    public void openCamera() {
        if (cropCallBack == null) {
            throw new NullPointerException("CropManager.get().init(CropCallBack)   ?");
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imagePath = Uri.fromFile(getDiskCacheDir(cropCallBack.getCropActivity()));


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
            cropCallBack
                    .getCropActivity()
                    .startActivityForResult(intent, TYPE_CAMERA);

        } else {

            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, imagePath.getPath());
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, IMAGE_TYPE);
            Uri uri = cropCallBack.getCropActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            cropCallBack
                    .getCropActivity()
                    .startActivityForResult(intent, TYPE_CAMERA);

        }
    }

    /**
     * 打开图库
     */
    public void openAlbum() {
        if (cropCallBack == null) {
            throw new NullPointerException("CropManager.get().init(CropCallBack)   ?");
        }


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            cropCallBack
                    .getCropActivity()
                    .startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType(IMAGE_TYPE), TYPE_ALBUM);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(IMAGE_TYPE_All);
            cropCallBack
                    .getCropActivity()
                    .startActivityForResult(intent, TYPE_KITKAT_ALBUM);
        }
    }

    /**
     * 接管 选择回调
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (cropCallBack == null) {
            throw new NullPointerException("CropManager.get().init(CropCallBack)   ?");
        }
        if (resultCode == Activity.RESULT_CANCELED) {

            cropCallBack.onCropCancel();

        } else if (resultCode == UCrop.RESULT_ERROR) {

            if (data != null) {
                Throwable cropError = UCrop.getError(data);
                if (cropError != null) {
                    cropCallBack.onCropError(cropError.getMessage());
                } else {
                    cropCallBack.onCropError("裁剪出现未知错误");
                }
            } else {
                cropCallBack.onCropError("获取相册图片出现错误");
            }

        } else if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {

                case TYPE_CAMERA:

                    notifyImageToCamera(cropCallBack.getCropActivity(), imagePath);
                    UCrop.of(imagePath,
                            Uri.fromFile(getDiskCacheDir(cropCallBack.getCropActivity())))
                            .withAspectRatio(16, 9)
                            .withOptions(getOptions(cropCallBack.getCropActivity()))
                            .start(cropCallBack.getCropActivity());

                    break;
                case TYPE_ALBUM:

                    if (data != null) {
                        UCrop.of(data.getData(), Uri.fromFile(getDiskCacheDir(cropCallBack.getCropActivity())))
                                .withAspectRatio(16, 9)
                                .withOptions(getOptions(cropCallBack.getCropActivity()))
                                .start(cropCallBack.getCropActivity());
                    } else {
                        cropCallBack.onCropError("获取相册图片出现错误");
                    }

                    break;
                case TYPE_KITKAT_ALBUM:

                    if (data != null) {
                        UCrop.of(data.getData(),
                                Uri.fromFile(getDiskCacheDir(cropCallBack.getCropActivity())))
                                .withAspectRatio(16, 9)
                                .withOptions(getOptions(cropCallBack.getCropActivity()))
                                .start(cropCallBack.getCropActivity());
                    } else {
                        cropCallBack.onCropError("获取相册图片出现错误");
                    }

                    break;
                case UCrop.REQUEST_CROP:
                    cropCallBack.onCropSuccess(UCrop.getOutput(data));
                    break;
            }
        }
    }

    private File getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath, imageName());
    }

    /**
     * 简单的设置 UCrop
     */
    private UCrop.Options getOptions(Activity activity) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.colorPrimaryDark));
        return options;
    }

    /**
     * 更新系统图库
     */
    private void notifyImageToCamera(Context context, Uri uri) {
        try {
            File file = new File(uri.getPath());
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

    private String imageName() {
        return System.currentTimeMillis() + ".jpg";
    }
}
