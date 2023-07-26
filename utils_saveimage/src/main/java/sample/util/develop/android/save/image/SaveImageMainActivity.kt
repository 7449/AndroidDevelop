package sample.util.develop.android.save.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SaveImageMainActivity : AppCompatActivity() {

    private val btnSave by lazy { findViewById<View>(R.id.btnSave) }
    private val imageView by lazy { findViewById<ImageView>(R.id.imageView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.save_image_activity_main)
        btnSave.setOnClickListener { saveImageView(getViewBitmap(imageView)) }
    }

    private inner class SaveObservable(private val drawingCache: Bitmap?) :
        Observable.OnSubscribe<String> {

        override fun call(subscriber: Subscriber<in String>) {
            if (drawingCache == null) {
                subscriber.onError(NullPointerException("imageview的bitmap获取为null,请确认imageview显示图片了"))
            } else {
                try {
                    val imageFile =
                        File(Environment.getExternalStorageDirectory(), "saveImageview.jpg")
                    val outStream = FileOutputStream(imageFile)
                    drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
                    subscriber.onNext(Environment.getExternalStorageDirectory().path)
                    subscriber.onCompleted()
                    outStream.flush()
                    outStream.close()
                } catch (e: IOException) {
                    subscriber.onError(e)
                }
            }
        }
    }

    private inner class SaveSubscriber : Subscriber<String>() {

        override fun onCompleted() {
            Toast.makeText(applicationContext, "保存成功", Toast.LENGTH_SHORT).show()
        }

        override fun onError(e: Throwable) {
            Log.i(javaClass.simpleName, e.toString())
            Toast.makeText(applicationContext, "保存失败——> " + e.toString(), Toast.LENGTH_SHORT)
                .show()
        }

        override fun onNext(s: String) {
            Toast.makeText(applicationContext, "保存路径为：-->  $s", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImageView(drawingCache: Bitmap?) {
        Observable.create(SaveObservable(drawingCache))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(SaveSubscriber())
    }

    /**
     * 某些机型直接获取会为null,在这里处理一下防止国内某些机型返回null
     */
    private fun getViewBitmap(view: View?): Bitmap? {
        if (view == null) {
            return null
        }
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}
