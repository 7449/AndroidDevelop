package com.fuckapp.utils

import com.fuckapp.fragment.model.AppModel
import com.fuckapp.main.App
import java.io.DataOutputStream

/**
 * by y on 2016/10/20.
 */

object RootUtils {
    private const val ADB_COMMAND_HIDE = "pm hide "
    const val ADB_COMMAND_UN_HIDE = "pm unhide "
    private var b = true

    fun execShell(appModels: List<AppModel>, rootUtilsInterface: RootUtilsInterface) {
        for (appModel in appModels) {
            if (!b) {
                rootUtilsInterface.execShellError()
                return
            }
            b = execShell(ADB_COMMAND_HIDE + appModel.pkgName)
        }
        if (b) {
            rootUtilsInterface.execShellSuccess()
        } else {
            rootUtilsInterface.execShellError()
        }
    }

    interface RootUtilsInterface {
        fun execShellSuccess()

        fun execShellError()
    }

    fun execShell(adbCommand: String): Boolean {
        var process: Process? = null
        var os: DataOutputStream? = null
        return try {
            process = Runtime.getRuntime().exec("su")
            os = DataOutputStream(process!!.outputStream)
            os.writeBytes(adbCommand + "\n")
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor() == 0
        } catch (e: Exception) {
            false
        } finally {
            os?.close()
            process?.destroy()
        }
    }

    /**
     * hide之后的app是false
     */
    fun isApk(packageName: String): Boolean {
        val packageManager = App.instance.packageManager
        val pinfo = packageManager.getInstalledPackages(0)
        if (pinfo != null) {
            for (packageInfo in pinfo) {
                if (packageInfo.packageName == packageName) {
                    return true
                }
            }
        }
        return false
    }
}
