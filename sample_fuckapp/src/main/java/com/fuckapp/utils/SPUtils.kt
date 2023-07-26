package com.fuckapp.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * by y on 2016/10/20.
 */

object SPUtils {
    private lateinit var sharedPreferences: SharedPreferences
    private const val SHAREDPREFERENCES_NAME = "mokeeUninstallApp"

    const val WARN_APP = "WarnApp"

    private fun initSharePreferences(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun init(context: Context) {
        initSharePreferences(context)
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}
