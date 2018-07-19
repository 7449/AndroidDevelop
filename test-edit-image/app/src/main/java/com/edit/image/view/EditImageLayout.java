package com.edit.image.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.edit.image.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author y
 */
public class EditImageLayout extends FrameLayout {


    /**
     * 图片编辑回调
     */
    public interface EditImageListener {
        /**
         * 更新成功
         */
        void updateSuccess(@NonNull Bitmap bitmap);

        /**
         * 更新失败
         */
        void updateError();

        /**
         * 保存成功
         */
        void saveSuccess(@NonNull String path);

        /**
         * 保存失败
         */
        void saveError();
    }

    public static final int MODE_NONE = 0;//默认
    public static final int MODE_TEXT = 1;// 文字模式
    public static final int MODE_PAINT = 2;//绘制模式
    public static final int MODE_BACK = 3;//回退
    public static final int MODE_EMPTY = 4; //清除所有

    private EditImageListener listener;
    private LinkedList<Bitmap> mCacheList;
    private Bitmap bitmap;
    private int mode = MODE_NONE;
    private TouchImageView touchImageView;
    private TextStickerView textStickerView;
    private CustomPaintView paintView;

    private int paintColor = getResources().getColor(R.color.colorAccent); // 画笔颜色
    private int textColor = getResources().getColor(R.color.colorAccent);// 字体颜色
    private boolean textEraser = false; // 橡皮擦
    private int paintWidth = 18; //画笔粗细
    private boolean autoNewLine = false; //是否自动换行
    private String text; // 文字
    private int cacheCount = 10; //缓存最大数
    private String savePath; // 图片保存路径

    public EditImageLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public EditImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditImageLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCacheList = new LinkedList<>();
        View inflate = View.inflate(getContext(), R.layout.layout_edit_image, null);
        touchImageView = inflate.findViewById(R.id.edit_touch_image);
        textStickerView = inflate.findViewById(R.id.edit_text_stick);
        paintView = inflate.findViewById(R.id.edit_text_paint);
        initText();
        initPaint();
        addView(inflate);
    }

    private void initText() {
        textStickerView.setTextColor(textColor);
        textStickerView.setAutoNewline(autoNewLine);
        textStickerView.setText(text);
    }

    private void initPaint() {
        paintView.setColor(paintColor);
        paintView.setWidth(paintWidth);
        paintView.setEraser(textEraser);
    }

    /**
     * 注册回调
     */
    public EditImageLayout setEditImageListener(EditImageListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 初始化Bitmap
     */
    public EditImageLayout setBitmap(@NonNull Bitmap bitmap) {
        this.bitmap = bitmap;
        touchImageView.setImageBitmap(bitmap);
        updateBitmap(bitmap);
        return this;
    }

    /**
     * 画笔颜色
     */
    public EditImageLayout setPaintColor(int paintColor) {
        this.paintColor = paintColor;
        paintView.setColor(paintColor);
        return this;
    }

    /**
     * 画笔粗细
     */
    public EditImageLayout setPaintWidth(int paintWidth) {
        this.paintWidth = paintWidth;
        paintView.setWidth(paintWidth);
        return this;
    }

    /**
     * 画笔橡皮擦颜色
     */
    public EditImageLayout setTextEraser(boolean eraser) {
        this.textEraser = eraser;
        paintView.setEraser(eraser);
        return this;
    }

    /**
     * 字体颜色
     */
    public EditImageLayout setTextColor(int textColor) {
        this.textColor = textColor;
        textStickerView.setTextColor(textColor);
        return this;
    }

    /**
     * 是否自动换行
     */
    public EditImageLayout setAutoNewLine(boolean autoNewLine) {
        this.autoNewLine = autoNewLine;
        textStickerView.setAutoNewline(autoNewLine);
        return this;
    }

    /**
     * 文字模式文字
     */
    public EditImageLayout setText(String text) {
        this.text = text;
        textStickerView.setText(text);
        return this;
    }

    /**
     * 图片保存路径
     */
    public EditImageLayout setSavePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    /**
     * 最大缓存个数
     */
    public EditImageLayout setCacheCount(int cacheCount) {
        this.cacheCount = cacheCount;
        return this;
    }


    /**
     * 默认缓存
     */
    public EditImageLayout setCacheList(LinkedList<Bitmap> mCacheList) {
        this.mCacheList = mCacheList;
        if (!mCacheList.isEmpty()) {
            bitmap = mCacheList.get(0);
            updateBitmap(bitmap);
        }
        return this;
    }


    public void start(int mode) {
        if (this.mode == mode) return;
        this.mode = mode;
        switch (mode) {
            case MODE_TEXT:
                defaultText();
                break;
            case MODE_PAINT:
                defaultPaint();
                break;
            case MODE_BACK:
                backView();
                break;
            case MODE_EMPTY:
                emptyView();
                break;
            default:
                defaultView();
                break;
        }
    }

    private void defaultText() {
        paintView.setVisibility(GONE);
        paintView.reset();
        textStickerView.setVisibility(VISIBLE);
    }

    private void defaultPaint() {
        textStickerView.setVisibility(GONE);
        textStickerView.resetView();
        textStickerView.setText(null);
        paintView.setVisibility(VISIBLE);
    }

    private void emptyView() {
        Bitmap emptyBitmap = mCacheList.getFirst();
        mCacheList.clear();
        updateBitmap(emptyBitmap);
    }

    private void backView() {
        if (mCacheList.isEmpty()) {
            return;
        }
        if (mCacheList.size() > 1) {
            mCacheList.removeLast();
            updateBitmap(mCacheList.getLast());
        } else {
            emptyView();
        }
    }

    private void defaultView() {
        textStickerView.setVisibility(GONE);
        paintView.setVisibility(GONE);
        textStickerView.resetView();
        textStickerView.setText(null);
        paintView.reset();
    }

    private void updateBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        if (!mCacheList.contains(bitmap)) {
            mCacheList.add(bitmap);
        }
        touchImageView.setImageBitmap(bitmap);
        if (mCacheList.size() >= cacheCount) {
            mCacheList.remove(mCacheList.get(1));
        }
        start(MODE_NONE);
    }

    public void save() {
        if (mode == MODE_PAINT || mode == MODE_TEXT) {
            update(this::saveBitmap);
            return;
        }
        saveBitmap();
    }


    public void update() {
        update(null);
    }

    @SuppressLint("CheckResult")
    private void saveBitmap() {
        Observable
                .create(this::saveFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        path -> {
                            if (listener != null) {
                                listener.saveSuccess(path);
                            }
                        },
                        throwable -> {
                            if (listener != null) {
                                listener.saveError();
                            }
                        });
    }


    @SuppressLint("CheckResult")
    private void update(Callback callback) {
        Observable.create(
                (ObservableOnSubscribe<Bitmap>) e -> {
                    switch (mode) {
                        case MODE_PAINT:
                            changePaintBitmap(e);
                            break;
                        case MODE_TEXT:
                            changeTextBitmap(e);
                            break;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newBit -> {
                            if (listener != null) {
                                listener.updateSuccess(newBit);
                            }
                            if (callback != null) {
                                callback.success();
                            }
                            updateBitmap(newBit);
                        },
                        throwable -> {
                            if (listener != null) {
                                listener.updateError();
                            }
                        });
    }

    private void saveFile(ObservableEmitter<String> e) throws IOException {
        File imageFile = new File(savePath);
        FileOutputStream outStream = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        e.onNext(savePath);
        outStream.flush();
        outStream.close();
    }

    private void changePaintBitmap(ObservableEmitter<Bitmap> e) {
        Matrix touchMatrix = touchImageView.getImageViewMatrix();
        Bitmap resultBit = Bitmap.createBitmap(bitmap).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(resultBit);
        float[] data = new float[9];
        touchMatrix.getValues(data);
        Matrix3 cal = new Matrix3(data);
        Matrix3 inverseMatrix = cal.inverseMatrix();
        Matrix m = new Matrix();
        m.setValues(inverseMatrix.getValues());
        float[] f = new float[9];
        m.getValues(f);
        int dx = (int) f[Matrix.MTRANS_X];
        int dy = (int) f[Matrix.MTRANS_Y];
        float scale_x = f[Matrix.MSCALE_X];
        float scale_y = f[Matrix.MSCALE_Y];
        canvas.save();
        canvas.translate(dx, dy);
        canvas.scale(scale_x, scale_y);
        if (paintView.getPaintBit() != null) {
            canvas.drawBitmap(paintView.getPaintBit(), 0, 0, null);
        }
        canvas.restore();
        e.onNext(resultBit);
    }

    private void changeTextBitmap(ObservableEmitter<Bitmap> e) {
        Matrix touchMatrix = touchImageView.getImageViewMatrix();
        Bitmap resultBit = Bitmap.createBitmap(bitmap).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(resultBit);
        float[] data = new float[9];
        touchMatrix.getValues(data);
        Matrix3 cal = new Matrix3(data);
        Matrix3 inverseMatrix = cal.inverseMatrix();
        Matrix m = new Matrix();
        m.setValues(inverseMatrix.getValues());
        float[] f = new float[9];
        m.getValues(f);
        int dx = (int) f[Matrix.MTRANS_X];
        int dy = (int) f[Matrix.MTRANS_Y];
        float scale_x = f[Matrix.MSCALE_X];
        float scale_y = f[Matrix.MSCALE_Y];
        canvas.save();
        canvas.translate(dx, dy);
        canvas.scale(scale_x, scale_y);
        textStickerView.drawText(canvas, textStickerView.layout_x, textStickerView.layout_y, textStickerView.mScale, textStickerView.mRotateAngle);
        canvas.restore();
        e.onNext(resultBit);
    }

    private interface Callback {
        void success();
    }
}
