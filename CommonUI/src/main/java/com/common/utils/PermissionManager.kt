package com.common.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import java.util.Arrays

/**
 * 权限检测
 * Created by yangyan
 * on 2018/3/22.
 */
class PermissionManager private constructor(){
    private var mActivity: Activity? = null
    private val necessaryPermissions: MutableList<String> = ArrayList()
    private val deniedPermissions: MutableList<String> = ArrayList()
    fun setNecessaryPermissions(permissions: Array<String>): PermissionManager {
        necessaryPermissions.clear()
        necessaryPermissions.addAll(listOf(*permissions))
        return this
    }

    /**
     * 检查是否缺少权限
     *
     */
    fun isLackPermission(permission: String?): Boolean {
        return ActivityCompat.checkSelfPermission(
                mActivity!!,
                permission!!
            ) == PackageManager.PERMISSION_DENIED
    }

    val isLackPermission: Boolean
        /**
         * 检查是否缺少权限
         *
         */
        get() {
            for (permission in necessaryPermissions) {
                if (ActivityCompat.checkSelfPermission(
                        mActivity!!,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    return true
                }
            }
            return false
        }

    /**
     * 得到还没有处理的权限
     */
    fun getDeniedPermissions(): Array<String>? {
        val permissions: Array<String>
        for (permission in necessaryPermissions) {
            if (isLackPermission(permission)) {
                deniedPermissions.add(permission)
            }
        }
        return if (deniedPermissions.size == 0) {
            null
        } else {
            permissions = deniedPermissions.toTypedArray()
            permissions
        }
    }

    /**
     * 权限请求
     */
    fun requestPermissions() {
        if (getDeniedPermissions() != null) {
            ActivityCompat.requestPermissions(
                mActivity!!,
                getDeniedPermissions()!!,
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private var isNotRemind = false
    private var isRemind = false

    init {
        necessaryPermissions.clear()
        deniedPermissions.clear()
    }

    val shouldShowRequestPermissionsCode: Int
        /**
         * 是否存在必要权限没有允许
         */
        get() {
            isNotRemind = false
            isRemind = false
            for (i in deniedPermissions.indices) {
                val isTip = ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity!!,
                    deniedPermissions[i]
                )
                if (isLackPermission(deniedPermissions[i])) {
                    if (isTip) { //表明用户没有彻底禁止弹出权限请求
                        isRemind = true
                    } else { //表明用户已经彻底禁止弹出权限请求
                        isNotRemind = true
                    }
                }
            }
            if (isRemind) {
                return EXIST_NECESSARY_PERMISSIONS_PROHIBTED
            } else if (isNotRemind) {
                return EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND
            }
            return 0
        }

    /**
     * 装转到应用设置页面
     */
    fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data =
            Uri.parse(PACKAGE_URL_SCHEME + mActivity!!.packageName)
        mActivity!!.startActivity(intent)
    }

    companion object {
        const val EXIST_NECESSARY_PERMISSIONS_PROHIBTED = 10001 //存在必要权限被禁止
        const val EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND = 1002 //存在必要权限永远不提示禁止
        const val PERMISSION_REQUEST_CODE = 0 // 系统权限管理页面的参数
        private const val PACKAGE_URL_SCHEME = "package:"
        fun with(activity: Activity): PermissionManager {
            val permissionBuilder = PermissionManager()
            permissionBuilder.mActivity = activity
            return permissionBuilder
        }
    }
}