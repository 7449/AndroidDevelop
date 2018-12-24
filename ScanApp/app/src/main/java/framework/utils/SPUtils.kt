package framework.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.TextUtils

import com.lock.main.model.MainBean

object SPUtils {

    private lateinit var sharedPreferences: SharedPreferences
    private const val SHAREDPREFERENCES_NAME = "look"
    const val USER_NAME = "user_name"
    const val PASS_WORD = "pass_word"
    const val HEADER_URL = "header"
    const val REMEMBER_PASSWORD = "remember_password"

    val isLogin: Boolean
        get() {
            val userBean = readeUser()
            return TextUtils.isEmpty(userBean.userName) && TextUtils.isEmpty(userBean.passWord)
        }


    val userName: String
        get() = readeUser().userName

    val passWord: String
        get() = readeUser().passWord

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initSharePreferences(context: Context) {
        sharedPreferences = context.applicationContext.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun init(context: Context) {
        initSharePreferences(context)
    }


    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun setLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }


    fun clear(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun writeUser(userBean: MainBean) {
        val edit = sharedPreferences.edit()
        edit.putString(USER_NAME, userBean.userName).apply()
        edit.putString(PASS_WORD, userBean.passWord)
        edit.apply()
    }

    fun readeUser(): MainBean {
        val userBean = MainBean()
        userBean.userName = sharedPreferences.getString(USER_NAME, "")
        userBean.passWord = sharedPreferences.getString(PASS_WORD, "")
        return userBean
    }

    fun updatePassWord(newPassWord: String) {
        setString(PASS_WORD, newPassWord)
    }
}
